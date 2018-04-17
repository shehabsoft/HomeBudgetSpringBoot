package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

 
 
import com.homeBudget.model.PurchaseHistory;
 
@Transactional
public interface PurchaseHistoryDAO  extends CrudRepository<PurchaseHistory, Integer>{

}
