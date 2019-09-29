package com.homeBudget.dao;

import javax.transaction.Transactional;

import com.homeBudget.model.User;
import org.springframework.data.repository.CrudRepository;

 
import com.homeBudget.model.MonthlyBudget;
 
@Transactional
public interface MonthlyBudgetDAO  extends CrudRepository<MonthlyBudget, Integer> {
    MonthlyBudget getMonthlyBudgetByUserAndStatus(User user, Integer status);

}
