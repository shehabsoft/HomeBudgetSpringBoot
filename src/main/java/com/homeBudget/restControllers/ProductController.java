package com.homeBudget.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeBudget.ResponseMessage;
import com.homeBudget.dao.FilesStorageService;
import com.homeBudget.dao.ProductDAO;
import com.homeBudget.dao.ProductSellerDAO;
import com.homeBudget.exception.ProductConstraintViolationException;
import com.homeBudget.exception.ProductNotFoundException;
import com.homeBudget.model.Product;
import com.homeBudget.model.ProductsSeller;
import com.homeBudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class ProductController {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private ProductSellerDAO productSellerDAO;

	@Autowired
	FilesStorageService storageService;

	@CrossOrigin
	@RequestMapping(value = "/Product/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Product>  getById(@PathVariable("id") Integer id) throws ProductNotFoundException {
		try {
			Product category = productDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Product>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Product>(category,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  ProductNotFoundException(ex.getMessage());

		}
	}
	@CrossOrigin
	@RequestMapping(value = "/Product/", method = RequestMethod.GET)
	public  ResponseEntity<List<ProductsSeller>>  getAll() throws ProductNotFoundException {
		try {
			Iterator<ProductsSeller> productsSellerIterator = productSellerDAO.findAll().iterator();
			List<ProductsSeller>productsSellersList=new ArrayList<>();
			if (productsSellerIterator == null) {

				return new ResponseEntity(HttpStatus.NOT_FOUND) ;
			}
		     while(productsSellerIterator.hasNext())
			 {
				 productsSellersList.add(productsSellerIterator.next());
			 }
			return new ResponseEntity<List<ProductsSeller>>(productsSellersList,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  ProductNotFoundException(ex.getMessage());

		}
	}

	@CrossOrigin
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Product/", method = RequestMethod.POST,headers = "content-type=multipart/form-data")
	public ResponseEntity<Product> create(@RequestParam("product") Product product) throws ProductNotFoundException{

		if (product != null) {

            User user= product.getUser();



			Product location1 = productDao.save(product);
			ProductsSeller productsSeller=new ProductsSeller();
			productsSeller.setUser(user);
			productsSeller.setProduct(location1);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String strDate= formatter.format(new Date());
			productsSeller.setCreationDate(strDate);
			productsSeller.setSale(0);
			productSellerDAO.save(productsSeller);

			return new ResponseEntity<Product>(location1,HttpStatus.OK) ;
		}else
		{
			 throw new ProductNotFoundException("please privide Product In Request Body");

		}


	}
	@CrossOrigin
	@RequestMapping(value = "/Product/upload", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("product") String product) {
		String message = "";
		try {
			Product product1=new ObjectMapper().readValue(product,Product.class);
			product1.setImgData(file.getBytes());
			User user= product1.getUser();
			Product location1 = productDao.save(product1);
			ProductsSeller productsSeller=new ProductsSeller();
			productsSeller.setUser(user);
			productsSeller.setProduct(location1);
			productsSeller.setCreationDate(new Date().toString());
			productsSeller.setSale(0);
			productSellerDAO.save(productsSeller);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/Product/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> update(@PathVariable("id") int id, @RequestBody Product location) {

		Product currentProduct1= productDao.findById(id).get();

		if (currentProduct1 == null) {

			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}


		currentProduct1=location;
	    Product product=productDao.save(currentProduct1);


		///update location
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	@CrossOrigin
@Transactional
	@RequestMapping(value = "/Product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Product> delete(@PathVariable("id") int id)throws ProductNotFoundException,ProductConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Product product = productDao.findById(id).get();
		if (product == null) {
		 throw new ProductNotFoundException("Unable to delete. Product with id \" + id + \" not found");
		}
			try {
				ProductsSeller productsSeller=productSellerDAO.findByProduct(product);
				productSellerDAO.delete(productsSeller);
				productDao.deleteById(id);
				return new ResponseEntity<Product>(HttpStatus.OK);
			}catch (Exception ex)
			{
				throw new ProductConstraintViolationException(ex.getMessage());
			}
	}



	



}
