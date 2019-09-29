package com.homeBudget.restControllers;

import com.homeBudget.dao.*;
import com.homeBudget.exception.CategoryNotFoundException;
import com.homeBudget.exception.MonthlyBudgetNotFoundException;
import com.homeBudget.exception.LocationConstraintViolationException;
import com.homeBudget.exception.LocationNotFoundException;
import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.Location;
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
public class LocationController {

	@Autowired
	private LocationDAO locationDao;


	@RequestMapping(value = "/Location/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Location>  getById(@PathVariable("id") Integer id) throws LocationNotFoundException {
		try {
			Location category = locationDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Location>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Location>(category,HttpStatus.FOUND) ;

		} catch (Exception ex) {
			throw new  LocationNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Location/", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Location location) throws LocationNotFoundException{

		if (location != null) {
			System.out.println("Creating Location " + location.getArabicName());

			Location location1 = locationDao.save(location);

			URI locationU = ServletUriComponentsBuilder.fromCurrentRequest().path(
					"/{id}").buildAndExpand(location1.getId()).toUri();
			return ResponseEntity.created(locationU).build();
		}else
		{
			 throw new LocationNotFoundException("please privide Location In Request Body");

		}


	}

	@RequestMapping(value = "/Location/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Location> update(@PathVariable("id") int id, @RequestBody Location location) {

		Location currentLocation1= locationDao.findById(id).get();

		if (currentLocation1 == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
		}

		currentLocation1.setArabicName(location.getArabicName());
		currentLocation1.setEnglishName(location.getEnglishName());

		locationDao.save(currentLocation1);

		///update location
		return new ResponseEntity<Location>(currentLocation1, HttpStatus.OK);
	}

	@RequestMapping(value = "/Location/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Location> delete(@PathVariable("id") int id)throws LocationNotFoundException,LocationConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Location user = locationDao.findById(id).get();
		if (user == null) {
		 throw new LocationNotFoundException("Unable to delete. Location with id \" + id + \" not found");
		}
			try {
				locationDao.deleteById(id);
				return new ResponseEntity<Location>(HttpStatus.FOUND);
			}catch (Exception ex)
			{
				throw new LocationConstraintViolationException(ex.getMessage());
			}
	}



	



}
