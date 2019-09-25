package com.homeBudget.restControllers;

import com.homeBudget.dao.CategoryDAO;
import com.homeBudget.dao.CategoryHistoryDAO;
import com.homeBudget.dao.MonthlyBudgetDAO;
import com.homeBudget.dao.UserDAO;
import com.homeBudget.exception.CategoryNotFoundException;
import com.homeBudget.exception.MonthlyBudgetNotFoundException;
import com.homeBudget.exception.UserNotFoundException;
import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryHistoryController {

	@Autowired
	private CategoryDAO categotyDao;

	@Autowired
	private CategoryHistoryDAO categoryHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;



	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/CategoryHistory/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Category category) throws CategoryNotFoundException{

		if (category != null) {
			System.out.println("Creating User " + category.getArabicDescription());

			Category category1 = categotyDao.save(category);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(category1.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else
		{
			 throw new CategoryNotFoundException("please privide Category In Request Body");

		}


	}

	@RequestMapping(value = "/CategoryHistory/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> update(@PathVariable("id") int id, @RequestBody Category category) {

		Category currentCategoty = categotyDao.findById(id).get();

		if (currentCategoty == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}

		currentCategoty.setArabicDescription(category.getArabicDescription());
		currentCategoty.setEnglishDescription(category.getEnglishDescription());
		currentCategoty.setPlanedValue(category.getPlanedValue());
		currentCategoty.setActualValue(category.getActualValue());
		currentCategoty.setLimitValue(category.getLimitValue());

		return new ResponseEntity<Category>(currentCategoty, HttpStatus.OK);
	}

	@RequestMapping(value = "/CategoryHistory/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> delete(@PathVariable("id") int id)throws CategoryNotFoundException {
		System.out.println("Fetching & Deleting User with id " + id);

		Category user = categotyDao.findById(id).get();
		if (user == null) {
			System.out.println("Unable to delete. Category with id " + id + " not found");
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}
			try {
				categotyDao.deleteById(id);
				return new ResponseEntity<Category>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new CategoryNotFoundException("Can not Delete Category as there is Relation ");
			}
	}


	@RequestMapping(value = "/CategoryHistory/User/{userId}/{categoryType}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategoriesByUserId(@PathVariable("userId")int userId,@PathVariable("categoryType")int categoryType)throws CategoryNotFoundException
	{
		List<Category> categories=null;
		User user=userDAO.findById(userId).get();
		if (user == null) {
			System.out.println("Unable to get All Expenses Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		 categories = (List<Category>) categotyDao.findByUserAndCategoryTypeId(user, categoryType);//1 for expenses Categories
		if(categories==null)
		{
			throw new CategoryNotFoundException("There is not Categories with type :"+categoryType+" For User ID :"+userId);
		}else {
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		}
	}

	
//	@RequestMapping(value = "/CategoryHistory/MonthlyBudget/{monthlyBudgetId}/User/{userId}/{categoryType}", method = RequestMethod.GET)
//	public ResponseEntity<List<Category>> getCategoriesByMonthlyBudgetUserIdCategoryId(@PathVariable("userId")Integer userId,@PathVariable("monthlyBudgetId")Integer monthlyBudgetId,@PathVariable("categoryType")Integer categoryType)throws Exception
//	{
//		List<Category> categories=null;
//
//
//		boolean found=userDAO.findById(userId).isPresent();
//		Optional<User> user=null;
//		if(found)
//		{
//		 user=	userDAO.findById(userId);
//		}
//		if (user == null) {
//			 throw new UserNotFoundException("User is Not Found with ID :"+userId);
//		}
//		Optional<MonthlyBudget> monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId);
//		if (monthlyBudget == null) {
//			throw new MonthlyBudgetNotFoundException("User is Not Found with ID :"+userId);
//		}
//		 categories = (List<Category>) categotyDao.findByMonthlyBudgetsAndUserAndCategoryTypeId(monthlyBudget.get(), user.get(), categoryType);//1 for expenses Categories
//		if(categories==null)
//		{
//			throw new CategoryNotFoundException("There is No Categories for Monthly Budget "+monthlyBudgetId +" and User ID :"+userId);
//		}
//		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
//	}



}
