package com.homeBudget.restControllers;

import com.homeBudget.dao.*;
import com.homeBudget.exception.OrderConstraintViolationException;
import com.homeBudget.exception.OrderNotFoundException;
import com.homeBudget.jobs.ContentIdGenerator;
import com.homeBudget.model.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
@RestController
public class OrderController {

	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private OrdersProductDAO orderProductDao;
	@Autowired
	private CleaningFeeDAO cleaningFeeDAO;
    @Autowired
	private ProductSellerDAO productsSellerDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private NotificationTemplateDAO notificationTemplateDAO;

	@Autowired
	private NotificationsDAO notificationsDAO;

	@Autowired
	private JavaMailSender javaMailSender;

	String mailBody=" ";
	void sendEmailWithAttachment(Order order) throws MessagingException, IOException {

	 	MimeMessage msg = javaMailSender.createMimeMessage();

		// true = multipart message

		NotificationTemplate notificationTemplate=notificationTemplateDAO.findByCode(1);
		 MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		 helper.setTo(order.getUser().getEmail());
		helper.setCc("shehabsoft94@gmail.com");

		 helper.setSubject("Order Confirmation");
		NotificationTemplateSection notificationTemplateSection=notificationTemplate.getNotificationTemplateSection();
		StringBuilder mailBuilder=new StringBuilder();
	    String header=notificationTemplateSection.getHeaderSection();
		header=header.replace("[OREDER_NUMBER]",order.getId()+"");
		header=header.replace("[OREDER_DATE]",order.getCreationDate()+"");
		header=header.replace("[ORDER_TOTAL_AMOUNT]",order.getTotal()+"");
		header=header.replace("[ORDER_SHIPING_ADDRESS]",order.getAddress1()+"");

		header=header.replace("[ORDER_SHIPING_ADDRESS2]",order.getAddress2()+"");
		header=header.replace("[ORDER_SHIPING_PHONE]",order.getPhoneNumber()+"");


		String footerSection=notificationTemplateSection.getFooterSection();
		footerSection=footerSection.replace("[CLEANING_SERVICE]",order.getOrdersProducts().get(0).getCleaningFee().getFeesNameEn()+"");
		footerSection=footerSection.replace("[CLEANING_SERVICE_FEE]",order.getOrdersProducts().get(0).getCleaningFee().getFeeAmount()+"");
		footerSection=footerSection.replace("[CLEANING_SERVICE_ITEM_COUNT]",order.getOrdersProducts().size()+"");
		footerSection=footerSection.replace("[TOTAL_ITEMS]",order.getOrdersProducts().size()+"");
		footerSection=footerSection.replace("[ORDER_TOTAL_FEES]",order.getTotal()+"");
		footerSection=footerSection.replace("[DISCOUNT]", "0");
		footerSection=footerSection.replace("[ORDER_TOTAL_FEES]", order.getTotal()+"");
		StringBuilder alltables=new StringBuilder();
		for(int i=0;i<order.getOrdersProducts().size();i++)
		{
			String tableSection=notificationTemplateSection.getTableSection();
			tableSection=tableSection.replace("[ITEM_ID]",order.getOrdersProducts().get(i).getProduct().getId()+"");
			tableSection=tableSection.replace("[ITEM_NAME_EN]",order.getOrdersProducts().get(i).getProduct().getNameAr());
			tableSection=tableSection.replace("[ITEM_AMOUNT]",order.getOrdersProducts().get(i).getQuantity()+"");
			tableSection=tableSection.replace("[ITEM_FEE]",order.getOrdersProducts().get(i).getProduct().getPrice()+"");
			System.out.println("Image Data :"+order.getOrdersProducts().get(i).getProduct().getImgData().clone());
			String encodedImage = Base64.encode(order.getOrdersProducts().get(i).getProduct().getImgData());

		    File imge=	convertImage(order.getOrdersProducts().get(i).getProduct().getImgData());
			String contentId = ContentIdGenerator.getContentId();
			tableSection=tableSection.replace("[ITEM_IMG_DATA]",encodedImage);
			alltables.append(tableSection);
		}

		mailBuilder.append(header).append(alltables).append(footerSection);
		helper.setText(mailBuilder.toString(), true);
		System.out.println(mailBuilder.toString());
		Notifications notifications=new Notifications();
		notifications.setMail_to(order.getUser().getEmail());
		notifications.setStatus(1);
		notifications.setNotification_content(mailBuilder.toString());
		notificationsDAO.save(notifications);
		// default = text/plain
		//helper.setText("Check attachment for image!");

		// true = text/html
		//helper.setText(mailBuilder.toString(), true);

		//FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

		//Resource resource = new ClassPathResource("android.png");
		//InputStream input = resource.getInputStream();

		//ResourceUtils.getFile("classpath:android.png");

		//helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

		//javaMailSender.send(msg);

	}

	public File convertImage(byte [] bytes )
	{
		File imageFile=null;
		try {

			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");

			//ImageIO is a class containing static methods for locating ImageReaders
			//and ImageWriters, and performing simple encoding and decoding.

			ImageReader reader = (ImageReader) readers.next();
			Object source = bis;
			ImageInputStream iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();

			Image image = reader.read(0, param);
			//got an image file

			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
			//bufferedImage is the RenderedImage to be written

			Graphics2D g2 = bufferedImage.createGraphics();
			g2.drawImage(image, null, null);
			ClassPathResource classPathResource = new ClassPathResource("android.png");
			 imageFile = new File(classPathResource.getURI());
			ImageIO.write(bufferedImage, "png", imageFile);

			System.out.println(imageFile.getPath());

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return imageFile;
	}
	@CrossOrigin
	@RequestMapping(value = "/Order/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Order>  getById(@PathVariable("id") Integer id) throws OrderNotFoundException {
		try {
			Order category = orderDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Order>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Order>(category,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  OrderNotFoundException(ex.getMessage());

		}
	}
	@CrossOrigin
	@RequestMapping(value = "/Order/User/{userId}", method = RequestMethod.GET)
	public  ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable("userId") Integer userId) throws OrderNotFoundException {
		try {
			User user=userDAO.findById(userId).get();
			List<Order> orderList = orderDao.findBySellerUserAndStatus(user,new Integer(2));

			if (orderList == null) {

				return new ResponseEntity<List<Order>>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<List<Order>>(orderList,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  OrderNotFoundException(ex.getMessage());

		}
	}
	@CrossOrigin
	@RequestMapping(value = "/Order/", method = RequestMethod.GET)
	public  ResponseEntity<List<Order>>  getAll() throws OrderNotFoundException {
		try {
				List<Order> category = orderDao.findByStatus(2);

			if (category == null) {

				return new ResponseEntity(HttpStatus.NOT_FOUND) ;
			}

			return new ResponseEntity<List<Order>>(category,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  OrderNotFoundException(ex.getMessage());

		}
	}

	@CrossOrigin
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Order/", method = RequestMethod.POST)
	public ResponseEntity<Order> create(@RequestBody Order order) throws OrderNotFoundException{

		if (order != null&&order.getOrdersProducts()!=null&&order.getUser()!=null) {
			order.setTotal(order.getTotal()+30);
			order.setCreationDate(new Date());
			order.setPhoneNumber("02"+order.getUser().getMobileNumber());
			order.setUpdateDate(new Date());

            List<OrdersProduct>ordersProducts=order.getOrdersProducts();
			ProductsSeller productsSeller=productsSellerDAO.findByProduct(ordersProducts.get(0).getProduct());
			User sellerUser= productsSeller.getUser();
			order.setSellerUser(sellerUser);
			Order order1 = orderDao.save(order);
			 for(int i=0;i<ordersProducts.size();i++) {
				 OrdersProduct  ordersProduct=ordersProducts.get(i);
				 ordersProduct.setCreationDate(new Date());
				 ordersProduct.setOrder(order1);


				 if( ordersProduct.getCleaningFeeId()!=null) {
					 try {
						 CleaningFee cleaningFee = cleaningFeeDAO.findById(ordersProduct.getCleaningFeeId()).get();
						 ordersProduct.setCleaningFee(cleaningFee);
					 }catch (Exception e)
					 {
						// e.printStackTrace();
					 }
				 }

				 orderProductDao.save(ordersProducts.get(i));


			 }
			try {
			//sendEmailWithAttachment(order1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
//			URI locationU = ServletUriComponentsBuilder.fromCurrentRequest().path(
//					"/{id}").buildAndExpand(location1.getId()).toUri();
		//	return ResponseEntity.created(locationU).build();

			return new ResponseEntity<Order>(order1,HttpStatus.OK) ;
		}else
		{
			 throw new OrderNotFoundException("please privide Order In Request Body");

		}


	}

	@CrossOrigin
	@RequestMapping(value = "/Order/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Order> update(@PathVariable("id") int id, @RequestBody Order location) {

		Order currentOrder1= orderDao.findById(id).get();

		if (currentOrder1 == null) {

			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}


		currentOrder1=location;
		currentOrder1.setUpdateDate(new Date());
	    Order product=orderDao.save(currentOrder1);
		try {
		//	sendEmailWithAttachment(currentOrder1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		///update location
		return new ResponseEntity<Order>(product, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/Order/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> delete(@PathVariable("id") int id)throws OrderNotFoundException,OrderConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Order user = orderDao.findById(id).get();
		if (user == null) {
		 throw new OrderNotFoundException("Unable to delete. Order with id \" + id + \" not found");
		}
			try {
				orderDao.deleteById(id);
				return new ResponseEntity<Order>(HttpStatus.OK);
			}catch (Exception ex)
			{
				throw new OrderConstraintViolationException(ex.getMessage());
			}
	}



	



}
