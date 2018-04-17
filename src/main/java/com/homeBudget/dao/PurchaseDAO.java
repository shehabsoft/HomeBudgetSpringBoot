package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

 
import com.homeBudget.model.Purchase;
 
@Transactional
public interface PurchaseDAO  extends CrudRepository<Purchase, Integer>{

}
