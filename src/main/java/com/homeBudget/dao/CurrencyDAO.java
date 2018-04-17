package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.Currency;
 
@Transactional
public interface CurrencyDAO  extends CrudRepository<Currency, Integer>{

}
