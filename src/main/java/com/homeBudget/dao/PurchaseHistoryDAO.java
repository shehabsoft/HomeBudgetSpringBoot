package com.homeBudget.dao;

import javax.transaction.Transactional;

import com.homeBudget.model.Purchase;
import org.springframework.data.repository.CrudRepository;

 
 
import com.homeBudget.model.PurchaseHistory;

import java.util.List;

@Transactional
public interface PurchaseHistoryDAO  extends CrudRepository<PurchaseHistory, Integer>{

    List<PurchaseHistory>findByPurchase(Purchase purchase);

}
