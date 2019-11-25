package com.homeBudget.restControllers;

import java.net.URI;
import java.util.*;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.homeBudget.dao.*;
import com.homeBudget.exception.CategoryConstraintViolationException;
import com.homeBudget.exception.CategoryNotFoundException;
import com.homeBudget.exception.MonthlyBudgetNotFoundException;
import com.homeBudget.exception.UserNotFoundException;
import com.homeBudget.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CategoryController {


	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CategoryDAO categotyDao;

	@Autowired
	private CategoryHistoryDAO categoryHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;

	@Autowired
	private MonthlyBudgetCategoryDAO monthlyBudgetCategoryDAO;

	@Autowired
	private MonthlyBudgetCategoryCustomDAO monthlyBudgetCategoryCustomDAO;


	@RequestMapping(value = "/Category/Message", method = RequestMethod.GET)
	public String getMessage(@RequestHeader(name="Accept-Language",required = false) Locale local ) throws CategoryNotFoundException {
		try {
    		return messageSource.getMessage("good1.morning.message",null,local);

		} catch (Exception ex) {
			throw new  CategoryNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/Category/MessageLocal", method = RequestMethod.GET)
	public String getMessage1( ) throws CategoryNotFoundException {
		try {
			return messageSource.getMessage("good1.morning.message",null, LocaleContextHolder.getLocale());

		} catch (Exception ex) {
			throw new  CategoryNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/Category/filtering/{id}", method = RequestMethod.GET)
	public MappingJacksonValue dynamicFiltering(@PathVariable("id") Integer id ) throws CategoryNotFoundException {
		try {
			Category category = categotyDao.findById(id).get();
			SimpleBeanPropertyFilter simpleBeanPropertyFilter= SimpleBeanPropertyFilter.FilterExceptFilter.filterOutAllExcept("actualValue");
			FilterProvider filterProvider=new SimpleFilterProvider().addFilter("Category",simpleBeanPropertyFilter);
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(category);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;

		} catch (Exception ex) {
			throw new  CategoryNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/Category/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Category>  getById(@PathVariable("id") Integer id) throws CategoryNotFoundException {
		try {
			Category category = categotyDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Category>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Category>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  CategoryNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Category/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Category category) throws CategoryNotFoundException{

		if (category != null) {
			System.out.println("Creating User " + category.getArabicDescription());

			Category category1 = categotyDao.save(category);
			CategoryHistory categoryHistory=new CategoryHistory();
			categoryHistory.setActualValue(category1.getActualValue());
			categoryHistory.setCategoryStatus(category1.getCategoryStatus());
			categoryHistory.setCategory(category1);
			categoryHistory.setCategoryTypeId(category1.getCategoryTypeId());
			categoryHistory.setLimitValue(category1.getLimitValue());
			categoryHistory.setPlanedValue(category1.getPlanedValue());
			categoryHistory.setUserId(category1.getUser().getId());
			categoryHistory.setActualValue(category1.getActualValue());
			categoryHistory.setCreationDate(new Date());
			categoryHistoryDAO.save(categoryHistory);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(category1.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else
		{
			 throw new CategoryNotFoundException("please privide Category In Request Body");

		}


	}

	@RequestMapping(value = "/Category/{id}", method = RequestMethod.PUT)
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
		categotyDao.save(currentCategoty);
		return new ResponseEntity<Category>(currentCategoty, HttpStatus.OK);
	}

	@RequestMapping(value = "/Category/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> delete(@PathVariable("id") int id)throws CategoryNotFoundException,CategoryConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Category user = categotyDao.findById(id).get();
		if (user == null) {
		 throw new CategoryNotFoundException("Unable to delete. Category with id \" + id + \" not found");
		}
			try {
				categotyDao.deleteById(id);
				return new ResponseEntity<Category>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new CategoryConstraintViolationException(ex.getMessage());
			}
	}


	@RequestMapping(value = "/Category/User/{userId}/{categoryType}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategoriesByUserIdAndCategoryType(@PathVariable("userId")int userId,@PathVariable("categoryType")int categoryType)throws CategoryNotFoundException
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


	
	@RequestMapping(value = "/Category/MonthlyBudget/{monthlyBudgetId}/User/{userId}/{categoryType}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategoriesByMonthlyBudgetUserIdCategoryId(@PathVariable("userId")Integer userId,@PathVariable("monthlyBudgetId")Integer monthlyBudgetId,@PathVariable("categoryType")Integer categoryType)throws Exception
	{
		List<Category> categories=null;
		
		
		boolean found=userDAO.findById(userId).isPresent();
		Optional<User> user=null;
		if(found)
		{
		 user=	userDAO.findById(userId);
		}
		if (user == null) {
			 throw new UserNotFoundException("User is Not Found with ID :"+userId);
		}
		MonthlyBudget monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId).get();
		if (monthlyBudget == null) {
			throw new MonthlyBudgetNotFoundException("User is Not Found with ID :"+userId);
		}

		List<MonthlyBudgetCategory>  monthlyBudgetCategory = (List<MonthlyBudgetCategory>) monthlyBudgetCategoryCustomDAO.findByMonthlyBudget(monthlyBudget,categoryType);//1 for expenses Categories
		if(monthlyBudgetCategory.size()==0)
		{
			throw new CategoryNotFoundException("There is No Categories for Monthly Budget "+monthlyBudgetId +" and User ID :"+userId);
		}
		categories=new ArrayList<>();
		 for(int i=0;i<monthlyBudgetCategory.size();i++)
		 {
			 categories.add(monthlyBudgetCategory.get(i).getCategory());
		 }
		 return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}



}
