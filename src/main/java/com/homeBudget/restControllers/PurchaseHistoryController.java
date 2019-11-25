package com.homeBudget.restControllers;

import com.homeBudget.dao.*;
import com.homeBudget.exception.*;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.Purchase;
import com.homeBudget.model.PurchaseHistory;
import com.homeBudget.model.User;
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

@RestController
public class PurchaseHistoryController {

	@Autowired
	private PurchaseDAO purchaseDao;
	@Autowired
	private PurchaseCustomDAO purchaseCustomDAO;
    @Autowired
    private CategoryDAO categoryDao;

	@Autowired
	private PurchaseHistoryDAO purchaseHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MonthlyBudgetDAO monthlyBudgetDAO;

	@RequestMapping(value = "/PurchaseHistory/", method = RequestMethod.GET)
	public  ResponseEntity <List<PurchaseHistory>>  getAll() throws PurchaseNotFoundException {
		try {
			List<PurchaseHistory> purchases = (List<PurchaseHistory>)purchaseHistoryDAO.findAll();
			if (purchases == null) {
				System.out.println(" Threre is No Purchase history");
				return new ResponseEntity <List<PurchaseHistory>>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<List<PurchaseHistory>>(purchases, HttpStatus.FOUND);

		} catch (Exception ex) {
			throw new  PurchaseNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/PurchaseHistory/{id}", method = RequestMethod.GET)
	public  ResponseEntity<PurchaseHistory>  getById(@PathVariable("id") Integer id) throws PurchaseHistoryNotFoundException {
		try {
			PurchaseHistory category = purchaseHistoryDAO.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<PurchaseHistory>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<PurchaseHistory>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  PurchaseHistoryNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/PurchaseHistory/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody PurchaseHistory purchaseHistory) throws PurchaseHistoryNotFoundException{

		if (purchaseHistory != null) {


			PurchaseHistory purchaseHistory1 = purchaseHistoryDAO.save(purchaseHistory);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(purchaseHistory1.getId()).toUri();
			return ResponseEntity.created(location).build();
		}else
		{
			 throw new PurchaseHistoryNotFoundException("please privide Purchase In Request Body");

		}


	}

	@RequestMapping(value = "/PurchaseHistory/{id}", method = RequestMethod.PUT)
	public ResponseEntity<PurchaseHistory> update(@PathVariable("id") int id, @RequestBody PurchaseHistory purchaseHistory) {

		PurchaseHistory currentPurchaeHistory= purchaseHistoryDAO.findById(id).get();

		if (currentPurchaeHistory == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<PurchaseHistory>(HttpStatus.NOT_FOUND);
		}

		currentPurchaeHistory=purchaseHistory;


		purchaseHistoryDAO.save(currentPurchaeHistory);

		///update location
		return new ResponseEntity<PurchaseHistory>(currentPurchaeHistory, HttpStatus.OK);
	}

	@RequestMapping(value = "/PurchaseHistory/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<PurchaseHistory> delete(@PathVariable("id") int id)throws PurchaseHistoryNotFoundException,PurchaseHistoryConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Purchase user = purchaseDao.findById(id).get();
		if (user == null) {
		 throw new PurchaseHistoryNotFoundException("Unable to delete. Purchase Hisory with id \" + id + \" not found");
		}
			try {
				purchaseDao.deleteById(id);
				return new ResponseEntity<PurchaseHistory>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new PurchaseHistoryConstraintViolationException(ex.getMessage());
			}
	}


	@RequestMapping(value = "/PurchaseHistory/Purchase/{purchaseId}/User/{userId}/", method = RequestMethod.GET)
	public ResponseEntity<List<PurchaseHistory>> getPurchaseHistoryByIdAndUserId(@PathVariable("purchaseId") Integer purchaseId ,@PathVariable("userId") Integer userId)throws PurchaseHistoryNotFoundException,CategoryNotFoundException,PurchaseNotFoundException,UserNotFoundException
	{
		Purchase  purchase=purchaseDao.findById(purchaseId).get();
        if (purchase == null) {
            throw new PurchaseNotFoundException("Null Purchase ");
        }
        User user=userDAO.findById(userId).get();
        if(user==null)
        {
            throw new UserNotFoundException("Null Monthly Budget");
        }

        List<PurchaseHistory> purchases = (List<PurchaseHistory>) purchaseHistoryDAO.findByPurchase(purchase);//1 for expenses Categories
		if(purchases.size()==0)
		{
            return new ResponseEntity<List<PurchaseHistory>>(HttpStatus.NOT_FOUND);

		}else {
			return new ResponseEntity<List<PurchaseHistory>>(purchases, HttpStatus.OK);
		}
	}





}
