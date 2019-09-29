package com.homeBudget.restControllers;

import com.homeBudget.dao.*;
import com.homeBudget.exception.CategoryConstraintViolationException;
import com.homeBudget.exception.CategoryNotFoundException;
import com.homeBudget.exception.MonthlyBudgetNotFoundException;
import com.homeBudget.exception.UserNotFoundException;
import com.homeBudget.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class MonthlyBudetController {

	@Autowired
	private CategoryDAO categotyDao;

	@Autowired
	private CategoryHistoryDAO categoryHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;


	@Autowired
	private MonthlyBudgetCategoryDAO monthlyBudgetCategoryDAO;

	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;

	@Autowired
	private MonthlyBudgetCategoryCustomDAO monthlyBudgetCategoryCustomDAO;

	@RequestMapping(value = "/MonthlyBudget/{id}", method = RequestMethod.GET)
	public  ResponseEntity<MonthlyBudget>  getById(@PathVariable("id") Integer id) throws MonthlyBudgetNotFoundException {
		try {
			MonthlyBudget category = monthlyBudgetDAO.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<MonthlyBudget>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<MonthlyBudget>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  MonthlyBudgetNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/MonthlyBudget/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody MonthlyBudget  monthlyBudget) throws MonthlyBudgetNotFoundException{

		if (monthlyBudget != null) {
			System.out.println("Creating monthlyBudget " + monthlyBudget.getEndDate());

			MonthlyBudget monthlyBudget1 = monthlyBudgetDAO.save(monthlyBudget);
//			CategoryHistory categoryHistory=new CategoryHistory();
//			categoryHistory.setActualValue(category1.getActualValue());
//			categoryHistory.setCategoryStatus(category1.getCategoryStatus());
//			categoryHistory.setCategory(category1);
//			categoryHistory.setCategoryTypeId(category1.getCategoryTypeId());
//			categoryHistory.setLimitValue(category1.getLimitValue());
//			categoryHistory.setPlanedValue(category1.getPlanedValue());
//			categoryHistory.setUserId(category1.getUser().getId());
//			categoryHistory.setActualValue(category1.getActualValue());
//			categoryHistory.setCreationDate(new Date());
//			categoryHistoryDAO.save(categoryHistory);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(monthlyBudget1.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else
		{
			 throw new MonthlyBudgetNotFoundException("please privide Category In Request Body");

		}


	}

	@RequestMapping(value = "/MonthlyBudget/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MonthlyBudget> update(@PathVariable("id") int id, @RequestBody MonthlyBudget  monthlyBudget) {

		MonthlyBudget monthlyBudget1 = monthlyBudgetDAO.findById(id).get();

		if (monthlyBudget1 == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<MonthlyBudget>(HttpStatus.NOT_FOUND);
		}
		monthlyBudget1=monthlyBudget;
//		currentCategoty.setArabicDescription(category.getArabicDescription());
//		currentCategoty.setEnglishDescription(category.getEnglishDescription());
//		currentCategoty.setPlanedValue(category.getPlanedValue());
//		currentCategoty.setActualValue(category.getActualValue());
//		currentCategoty.setLimitValue(category.getLimitValue());
		monthlyBudgetDAO.save(monthlyBudget1);
		return new ResponseEntity<MonthlyBudget>(monthlyBudget1, HttpStatus.OK);
	}

	@RequestMapping(value = "/MonthlyBudget/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<MonthlyBudget> delete(@PathVariable("id") int id)throws MonthlyBudgetNotFoundException,CategoryConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		MonthlyBudget monthlyBudget = monthlyBudgetDAO.findById(id).get();
		if (monthlyBudget == null) {
		 throw new MonthlyBudgetNotFoundException("Unable to delete. Category with id \" + id + \" not found");
		}
			try {
				monthlyBudgetDAO.deleteById(id);
				return new ResponseEntity<MonthlyBudget>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new CategoryConstraintViolationException(ex.getMessage());
			}
	}


	@RequestMapping(value = "/MonthlyBudget/User/{userId}/", method = RequestMethod.GET)
	public ResponseEntity<MonthlyBudget> getMonthlyBudgetByUserId(@PathVariable("userId")int userId)throws CategoryNotFoundException
	{
		MonthlyBudget monthlyBudget=null;
		User user=userDAO.findById(userId).get();
		if (user == null) {
			System.out.println("Unable to get All Expenses Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<MonthlyBudget>(HttpStatus.NOT_FOUND);
		}
		monthlyBudget = (MonthlyBudget) monthlyBudgetDAO.getMonthlyBudgetByUserAndStatus(user,2);//1 for expenses Categories
		if(monthlyBudget==null)
		{
			throw new CategoryNotFoundException("There is not Categories with type :"+monthlyBudget+" For User ID :"+userId);
		}else {
			return new ResponseEntity<MonthlyBudget>(monthlyBudget, HttpStatus.OK);
		}
	}




}
