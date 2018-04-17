package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.ApprovedPurchases;
 
@Transactional
public interface ApprovedPurchasesDAO  extends CrudRepository<ApprovedPurchases, Integer>{

}
