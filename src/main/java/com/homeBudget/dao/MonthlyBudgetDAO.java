package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

 
import com.homeBudget.model.MonthlyBudget;
 
@Transactional
public interface MonthlyBudgetDAO  extends CrudRepository<MonthlyBudget, Integer>{

}
