package com.homeBudget.restControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.homeBudget.dao.CategoryDAO;
import com.homeBudget.dao.MonthlyBudgetDAO;
import com.homeBudget.dao.UserDAO;
import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.User;

@RestController
public class CategoryController {

	@Autowired
	private CategoryDAO categotyDao;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;

	/*@RequestMapping(value = "/Category/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getById(@PathVariable("id") int id) {
		try {
			Category category = categotyDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Category>(category, HttpStatus.OK);

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}*/
	
	@RequestMapping(value = "/Category/get/{id}", method = RequestMethod.GET)
	public  Category  getById(@PathVariable("id") int id) {
		try {
			Category category = categotyDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				//return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
				return null;
			}
			return  category ;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	} 

	@RequestMapping(value = "/Category/add", method = RequestMethod.POST)
	public ResponseEntity<Category> create(@RequestBody Category category) {
		System.out.println("Creating User " + category.getArabicDescription());

		Category category1 = categotyDao.save(category);
		return new ResponseEntity<Category>(category1, HttpStatus.OK);

	}

	@RequestMapping(value = "/Category/update/{id}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/Category/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> delete(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting User with id " + id);

		Category user = categotyDao.findById(id).get();
		if (user == null) {
			System.out.println("Unable to delete. Category with id " + id + " not found");
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}

		categotyDao.deleteById(id);
		return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/Category/get", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = (List<Category>) categotyDao.findAll();
		if (categories.isEmpty()) {
			return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);// You
																				// many
																				// decide
																				// to
																				// return
																				// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	@RequestMapping(value = "/Category/getAllExpensesCategoriesByUserId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllExpensesCategoriesByUserId(@PathVariable("userId")int userId)
	{
		List<Category> categories=null;
		User user=userDAO.findById(userId).get();
		if (user == null) {
			System.out.println("Unable to get All Expenses Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		 categories = (List<Category>) categotyDao.findByUserAndCategoryTypeId(user, 1);//1 for expenses Categories 
		
		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/Category/getAllBudgetCategories/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllBudgetCategoriesByUserId(@PathVariable("userId")int userId)
	{
		List<Category> categories=null;
		User user=userDAO.findById(userId).get();
		if (user == null) {
			System.out.println("Unable to get All Income Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		 categories = (List<Category>) categotyDao.findByUserAndCategoryTypeId(user, 2);//1 for expenses Categories 
		
		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/Category/getBudgetCategories/{userId}/{monthlyBudgetId}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getBudgetCategoriesByUserId(@PathVariable("userId")int userId,@PathVariable("monthlyBudgetId")int monthlyBudgetId)
	{
		List<Category> categories=null;
		
		
		boolean found=userDAO.findById(userId).isPresent();
		Optional<User> user=null;
		if(found)
		{
		 user=	userDAO.findById(userId);
		}
		if (user == null) {
			System.out.println("Unable to get All Income Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		MonthlyBudget monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId).get();
		if (monthlyBudget == null) {
			System.out.println("Unable to get monthlyBudget ByU serId monthlyBudget with id " + monthlyBudgetId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		 categories = (List<Category>) categotyDao.findByMonthlyBudgetsAndUserAndCategoryTypeId(monthlyBudget, user.get(), 2);//1 for expenses Categories 
		
		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	@RequestMapping(value = "/Category/getExpensesCategories/{userId}/{monthlyBudgetId}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getExpensesCategoriesByUserId(@PathVariable("userId")int userId,@PathVariable("monthlyBudgetId")int monthlyBudgetId)
	{
		List<Category> categories=null;
		User user=userDAO.findById(userId).get();
		if (user == null) {
			System.out.println("Unable to get All Income Categories ByU serId Category with id " + userId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		MonthlyBudget monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId).get();
		if (monthlyBudget == null) {
			System.out.println("Unable to get monthlyBudget ByU serId monthlyBudget with id " + monthlyBudgetId + " not found");
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		 categories = (List<Category>) categotyDao.findByMonthlyBudgetsAndUserAndCategoryTypeId(monthlyBudget, user, 1);//1 for expenses Categories 
		
		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}


}
