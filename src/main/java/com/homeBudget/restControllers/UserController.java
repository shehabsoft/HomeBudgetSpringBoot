package com.homeBudget.restControllers;

import com.homeBudget.dao.UserDAO;
import com.homeBudget.exception.UserNotFoundException;
import com.homeBudget.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
     private OrderController orderController;
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
				user1.setLastLoginDate(new Date());
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
	@RequestMapping(value = "/User/forgetPassword/", method = RequestMethod.POST)
	public  ResponseEntity<User>  forgetPassword(@RequestBody String email) throws UserNotFoundException {
		try {
			User user1 = userDao.findByEmail(email);
			if (user1 == null) {

				return new ResponseEntity<User>(HttpStatus.NOT_FOUND) ;
			}else if(user1!=null ) {




				orderController.forgetPassword(user1);
			}

			return new ResponseEntity<User>(user1, HttpStatus.OK);
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


	@RequestMapping(value = "/User/", method = RequestMethod.POST)
	public ResponseEntity<User> create(@RequestBody User user) throws UserNotFoundException {


		User user1=null;
			if (user != null) {
				try {
					String token = UUID.randomUUID().toString();
					if(user.getAddress()==null)
					{
						throw new UserNotFoundException("User Address Can not be Empty");
					}
					if(user.getMobileNumber()==null)
					{
						throw new UserNotFoundException("Mobile Number Can not be Empty");
					}
					if(user.getEmail()==null)
					{
						throw new UserNotFoundException("Email Can not be Empty");
					}
					if(user.getPassword()==null)
					{
						throw new UserNotFoundException("Password Can not be Empty");
					}
					if(user.getName()==null)
					{
						throw new UserNotFoundException("Name Can not be Empty");
					}
					System.out.println("token is :" + token);
					user.setToken(token);
					  user1 = userDao.save(user);
				}
				catch(Exception ex)
					{
						throw new UserNotFoundException(ex.getMessage());
					}
				return new ResponseEntity<User>(user1, HttpStatus.OK);
			} else {
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
