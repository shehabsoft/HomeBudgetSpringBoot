package com.homeBudget.restControllers;

import com.homeBudget.dao.CleaningFeeDAO;

 
import com.homeBudget.exception.CleaningFeeConstraintViolationException;
import com.homeBudget.exception.CleaningFeeNotFoundException;
import com.homeBudget.model.CleaningFee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class CleaningFeeController {

	@Autowired
	private CleaningFeeDAO cleaningFeeDAO;




	@RequestMapping(value = "/CleaningFee/{id}", method = RequestMethod.GET)
	public  ResponseEntity<CleaningFee>  getById(@PathVariable("id") Integer id) throws CleaningFeeNotFoundException {
		try {
			CleaningFee category = cleaningFeeDAO.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<CleaningFee>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<CleaningFee>(category,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  CleaningFeeNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/CleaningFee/", method = RequestMethod.GET)
	public  ResponseEntity<List<CleaningFee>>  getAll() throws CleaningFeeNotFoundException {
		try {
			Iterator<CleaningFee> productsSellerIterator = cleaningFeeDAO.findAll().iterator();
			List<CleaningFee>productsSellersList=new ArrayList<>();
			if (productsSellerIterator == null) {

				return new ResponseEntity(HttpStatus.NOT_FOUND) ;
			}
		     while(productsSellerIterator.hasNext())
			 {
				 CleaningFee cleaningFee=productsSellerIterator.next();
				 if(cleaningFee!=null) {
					 productsSellersList.add(cleaningFee);
				 }else
				 {
				 	break;
				 }
			 }
			return new ResponseEntity<List<CleaningFee>>(productsSellersList,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  CleaningFeeNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/CleaningFee/", method = RequestMethod.POST)
	public ResponseEntity<CleaningFee> create(@RequestBody CleaningFee location) throws CleaningFeeNotFoundException{

		if (location != null) {


			CleaningFee location1 = cleaningFeeDAO.save(location);
			return new ResponseEntity<CleaningFee>(location1,HttpStatus.OK) ;
		}else
		{
			 throw new CleaningFeeNotFoundException("please privide CleaningFee In Request Body");

		}


	}

	@RequestMapping(value = "/CleaningFee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CleaningFee> update(@PathVariable("id") int id, @RequestBody CleaningFee location) {

		CleaningFee currentCleaningFee1= cleaningFeeDAO.findById(id).get();

		if (currentCleaningFee1 == null) {

			return new ResponseEntity<CleaningFee>(HttpStatus.NOT_FOUND);
		}


		currentCleaningFee1=location;
	    CleaningFee product=cleaningFeeDAO.save(currentCleaningFee1);


		///update location
		return new ResponseEntity<CleaningFee>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/CleaningFee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CleaningFee> delete(@PathVariable("id") int id)throws CleaningFeeNotFoundException,CleaningFeeConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		CleaningFee user = cleaningFeeDAO.findById(id).get();
		if (user == null) {
		 throw new CleaningFeeNotFoundException("Unable to delete. CleaningFee with id \" + id + \" not found");
		}
			try {
				cleaningFeeDAO.deleteById(id);
				return new ResponseEntity<CleaningFee>(HttpStatus.OK);
			}catch (Exception ex)
			{
				throw new CleaningFeeConstraintViolationException(ex.getMessage());
			}
	}



	



}
