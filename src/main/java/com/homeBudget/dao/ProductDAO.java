package com.homeBudget.dao;

import com.homeBudget.model.Currency;
import com.homeBudget.model.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProductDAO extends CrudRepository<Product, Integer>{

}
