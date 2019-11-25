package com.homeBudget.restControllers;

import com.homeBudget.dao.CountryDAO;
import com.homeBudget.dao.LocationDAO;
import com.homeBudget.exception.CountryConstraintViolationException;
import com.homeBudget.exception.CountryNotFoundException;
import com.homeBudget.exception.LocationConstraintViolationException;
import com.homeBudget.exception.LocationNotFoundException;
import com.homeBudget.model.Country;
import com.homeBudget.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CountriesController {

	@Autowired
	private CountryDAO countryDAO;


	@RequestMapping(value = "/Country/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Country>  getById(@PathVariable("id") Integer id) throws CountryNotFoundException {
		try {
			Country country = countryDAO.findById(id).get();
			if (country == null) {
			 return new ResponseEntity<Country>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Country>(country,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new CountryNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Country/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Country country) throws CountryNotFoundException{

		if (country != null) {
			System.out.println("Creating Location " + country.getArabicName());

			Country country1 = countryDAO.save(country);

			URI locationU = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(country1.getId()).toUri();
			return ResponseEntity.created(locationU).build();
		}else
		{
			 throw new CountryNotFoundException("please privide Country In Request Body");

		}


	}

	@RequestMapping(value = "/Country/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Country> update(@PathVariable("id") int id, @RequestBody Country country) {

		Country country1= countryDAO.findById(id).get();

		if (country1 == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}

		country1.setArabicName(country.getArabicName());
		country1.setEnglishName(country.getEnglishName());

		countryDAO.save(country1);

		///update location
		return new ResponseEntity<Country>(country1, HttpStatus.OK);
	}

	@RequestMapping(value = "/Country/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Country> delete(@PathVariable("id") int id)throws CountryNotFoundException,CountryConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Country country = countryDAO.findById(id).get();
		if (country == null) {
		 throw new CountryNotFoundException("Unable to delete. Location with id \" + id + \" not found");
		}
			try {
				countryDAO.deleteById(id);
				return new ResponseEntity<Country>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new CountryConstraintViolationException(ex.getMessage());
			}
	}



	



}
