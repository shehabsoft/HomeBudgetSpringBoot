package com.homeBudget.restControllers;

import com.homeBudget.dao.UserDAO;
import com.homeBudget.exception.UserNotFoundException;
import com.homeBudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping(value = "/User/{id}", method = RequestMethod.GET)
	public  ResponseEntity<User>  getById(@PathVariable("id") Integer id) throws UserNotFoundException {
		try {
//			SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
//			simpleMailMessage.setCc("shehabsoft94@gmail.com");
//			simpleMailMessage.setFrom("shehabsoft94@gmail.com");
//			simpleMailMessage.setText("said Mohammed");
//			simpleMailMessage.setSubject("Smart Services  Mail");
//			javaMailSender.send(simpleMailMessage);
			sendEmailWithAttachment();
			User user = userDao.findById(id).get();
			if (user == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<User>(user,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  UserNotFoundException(ex.getMessage());

		}
	}
	void sendEmailWithAttachment() throws MessagingException, IOException {

		MimeMessage msg = javaMailSender.createMimeMessage();

		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("shehabsoft94@gmail.com");

		helper.setSubject("Testing from Spring Boot");

		// default = text/plain
		//helper.setText("Check attachment for image!");

		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);

		//FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

		//Resource resource = new ClassPathResource("android.png");
		//InputStream input = resource.getInputStream();

		//ResourceUtils.getFile("classpath:android.png");

		helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

		javaMailSender.send(msg);

	}
	@RequestMapping(value = "/UserEmail", method = RequestMethod.POST)
	public  ResponseEntity<User>  getByEmail(@RequestBody User userRequest) throws UserNotFoundException {
		try {

			User user = userDao.findByEmail(userRequest.getEmail());
			if (user == null) {
				System.out.println("Unable to delete. User with id " + userRequest.getEmail() + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<User>(user,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  UserNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/User/signIn/", method = RequestMethod.POST)
	public  ResponseEntity<User>  signIn(@RequestBody User user) throws UserNotFoundException {
		try {
			User user1 = userDao.findByEmail(user.getEmail());
			if (user1 == null) {

				return new ResponseEntity<User>(HttpStatus.NOT_FOUND) ;
			}
			if(user1.getPassword().equals(user.getPassword())) {

				String token = UUID.randomUUID().toString();
				user1.setToken(token);
				user1=userDao.save(user1);
				return new ResponseEntity<User>(user1, HttpStatus.OK);
			}
			else
			{
				throw new  UserNotFoundException("Invalid Credentioals");
			}

		} catch (Exception ex) {
			throw new  UserNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/User/", method = RequestMethod.GET)
	public  ResponseEntity<List<User>>  getAll() throws UserNotFoundException {
		try {
			Iterator<User> user = userDao.findAll().iterator();
			List<User>userList=new ArrayList<>();
			if (user == null) {

				return new ResponseEntity(HttpStatus.NOT_FOUND) ;
			}
		     while(user.hasNext())
			 {
				 userList.add(user.next());
			 }
			return new ResponseEntity<List<User>>(userList,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  UserNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/User/", method = RequestMethod.POST)
	public ResponseEntity<User> create(@RequestBody User user) throws UserNotFoundException {

		if (user != null) {

				String token = UUID.randomUUID().toString();
				System.out.println("token is :" + token);
				user.setToken(token);
				   User user1 = userDao.save(user);

				return   new ResponseEntity<User>(user1,HttpStatus.OK) ;
			}else
			{
				throw new UserNotFoundException("please privide User In Request Body");

			}





	}

	@RequestMapping(value = "/User/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody User user) {

		User currentUser1= userDao.findById(id).get();

		if (currentUser1 == null) {

			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}


		currentUser1=user;
	    User newuser=userDao.save(currentUser1);


		///update user
		return new ResponseEntity<User>(newuser, HttpStatus.OK);
	}

	@RequestMapping(value = "/User/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> delete(@PathVariable("id") int id)throws UserNotFoundException  {
		System.out.println("Fetching & Deleting User with id " + id);

		User user = userDao.findById(id).get();
		if (user == null) {
		 throw new UserNotFoundException("Unable to delete. User with id \" + id + \" not found");
		}
			try {
				userDao.deleteById(id);
				return new ResponseEntity<User>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				 throw new UserNotFoundException(ex.getMessage());
			}
	}



	



}
