package com.homeBudget.restControllers;

import com.homeBudget.dao.*;
import com.homeBudget.exception.*;
import com.homeBudget.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PurchaseController {

	@Autowired
	private PurchaseDAO purchaseDao;
    @Autowired
    private CategoryDAO categoryDao;

	@Autowired
	private PurchaseHistoryDAO purchaseHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;

	@RequestMapping(value = "/Purchase/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Purchase>  getById(@PathVariable("id") Integer id) throws PurchaseNotFoundException {
		try {
			Purchase category = purchaseDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Purchase>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Purchase>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  PurchaseNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Purchase/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Purchase purchase) throws PurchaseNotFoundException{

		if (purchase != null) {
			System.out.println("Creating User " + purchase.getArabicDescription());

			Purchase purchase1 = purchaseDao.save(purchase);
			PurchaseHistory purchaseHistory=new PurchaseHistory();
			purchaseHistory.setPurchase(purchase1) ;
			purchaseHistory.setPrice(purchase1.getPrice());
			purchaseHistory.setDetails(purchase1.getDetails());
		    purchaseHistory.setLocation(purchase1.getLocation());
			purchaseHistory.setCreationDate(new Date());
            purchaseHistoryDAO.save(purchaseHistory);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(purchase1.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else
		{
			 throw new PurchaseNotFoundException("please privide Purchase In Request Body");

		}


	}

	@RequestMapping(value = "/Purchase/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Purchase> update(@PathVariable("id") int id, @RequestBody Purchase purchase) {

		Purchase currentPurchae= purchaseDao.findById(id).get();

		if (currentPurchae == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Purchase>(HttpStatus.NOT_FOUND);
		}

		currentPurchae.setArabicDescription(purchase.getArabicDescription());
		currentPurchae.setEnglishDescription(purchase.getEnglishDescription());
		currentPurchae.setDetails(purchase.getDetails());
		currentPurchae.setCategory(purchase.getCategory());
		currentPurchae.setPrice(currentPurchae.getPrice()+purchase.getPrice());
		currentPurchae.setDetails(purchase.getDetails());
		purchaseDao.save(currentPurchae);

		///update location
		return new ResponseEntity<Purchase>(currentPurchae, HttpStatus.OK);
	}

	@RequestMapping(value = "/Purchase/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Purchase> delete(@PathVariable("id") int id)throws PurchaseNotFoundException,PurchaseConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Purchase user = purchaseDao.findById(id).get();
		if (user == null) {
		 throw new PurchaseNotFoundException("Unable to delete. Purchase with id \" + id + \" not found");
		}
			try {
				purchaseDao.deleteById(id);
				return new ResponseEntity<Purchase>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new PurchaseConstraintViolationException(ex.getMessage());
			}
	}


	@RequestMapping(value = "/Purchase/MonthlyBudget/{monthlyBudgetId}/Category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<List<Purchase>> getAllPurchaseByMonthlyBudgetAndCategory(@PathVariable("monthlyBudgetId") Integer monthlyBudgetId ,@PathVariable("categoryId") Integer categoryId)throws MonthlyBudgetNotFoundException,CategoryNotFoundException,PurchaseNotFoundException
	{
        MonthlyBudget monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId).get();
        if (monthlyBudget == null) {
            throw new MonthlyBudgetNotFoundException("Null Monthly Budget");
        }
        Category category=categoryDao.findById(categoryId).get();
        if(category==null)
        {
            throw new CategoryNotFoundException("Null Monthly Budget");
        }
        List<Purchase> purchases = (List<Purchase>) purchaseDao.findByMonthlyBudgetAndCategory(monthlyBudget,category);//1 for expenses Categories
		if(purchases==null)
		{
			throw new PurchaseNotFoundException("There is not Purchases with type For User ID :"+category.getArabicDescription());
		}else {
			return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
		}
	}
    @RequestMapping(value = "/Purchase/MonthlyBudget/{monthlyBudgetId}/", method = RequestMethod.GET)
    public ResponseEntity<List<Purchase>> getAllPurchaseByMonthlyBudget(@PathVariable("monthlyBudgetId") Integer monthlyBudgetId )throws MonthlyBudgetNotFoundException,CategoryNotFoundException,PurchaseNotFoundException
    {
        MonthlyBudget monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId).get();
        if (monthlyBudget == null) {
            throw new MonthlyBudgetNotFoundException("Null Monthly Budget");
        }

        List<Purchase> purchases = (List<Purchase>) purchaseDao.findByMonthlyBudget(monthlyBudget);//1 for expenses Categories
        if(purchases.size()==0)
        {
            throw new PurchaseNotFoundException("There is not Purchases with type For User I ");
        }else {
            return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
        }
    }
	
//	@RequestMapping(value = "/Purchase/MonthlyBudget/{monthlyBudgetId}/User/{userId}/", method = RequestMethod.GET)
//	public ResponseEntity<List<Purchase>> getPurchasesByMonthlyBudgetUserIdPurchaseId(@PathVariable("userId")Integer userId,@PathVariable("monthlyBudgetId")Integer monthlyBudgetId)throws Exception
//	{
//		List<Purchase> purchases=null;
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
//
//		Optional<MonthlyBudget> monthlyBudget=monthlyBudgetDAO.findById(monthlyBudgetId);
//		if (monthlyBudget == null) {
//			throw new MonthlyBudgetNotFoundException("User is Not Found with ID :"+userId);
//		}
//        purchases = (List<Purchase>) purchaseDao.findByMonthlyBudgetAndUser(monthlyBudget.get(), user.get());//1 for expenses Categories
//		if(purchases==null)
//		{
//			throw new PurchaseNotFoundException("There is No Categories for Monthly Budget "+monthlyBudgetId +" and User ID :"+userId);
//		}
//		 return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
//	}



}
