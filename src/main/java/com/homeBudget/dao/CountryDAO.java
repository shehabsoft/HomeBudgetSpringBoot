package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.Country;
 
 
@Transactional
public interface CountryDAO  extends CrudRepository<Country, Integer>{

}
