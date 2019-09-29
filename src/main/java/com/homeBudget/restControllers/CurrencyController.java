package com.homeBudget.restControllers;

import com.homeBudget.dao.CurrencyDAO;
import com.homeBudget.exception.CurrencyConstraintViolationException;
import com.homeBudget.exception.CurrencyNotFoundException;
import com.homeBudget.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CurrencyController {

	@Autowired
	private CurrencyDAO currencyDao;


	@RequestMapping(value = "/Currency/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Currency>  getById(@PathVariable("id") Integer id) throws CurrencyNotFoundException {
		try {
			Currency category = currencyDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Currency>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  CurrencyNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Currency/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Currency currency) throws CurrencyNotFoundException{

		if (currency != null) {
			System.out.println("Creating Currency " + currency.getName());

			Currency currency1 = currencyDao.save(currency);

			URI locationU = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(currency1.getId()).toUri();
			return ResponseEntity.created(locationU).build();
		}else
		{
			 throw new CurrencyNotFoundException("please privide Currency In Request Body");

		}


	}

	@RequestMapping(value = "/Currency/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Currency> update(@PathVariable("id") int id, @RequestBody Currency currency) {

		Currency currentCurrency1= currencyDao.findById(id).get();

		if (currentCurrency1 == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Currency>(HttpStatus.NOT_FOUND);
		}

		currentCurrency1.setName(currency.getName());
		currencyDao.save(currentCurrency1);

		///update location
		return new ResponseEntity<Currency>(currentCurrency1, HttpStatus.OK);
	}

	@RequestMapping(value = "/Currency/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Currency> delete(@PathVariable("id") int id)throws CurrencyNotFoundException,CurrencyConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Currency user = currencyDao.findById(id).get();
		if (user == null) {
		 throw new CurrencyNotFoundException("Unable to delete. Currency with id \" + id + \" not found");
		}
			try {
				currencyDao.deleteById(id);
				return new ResponseEntity<Currency>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new CurrencyConstraintViolationException(ex.getMessage());
			}
	}



	



}
