package com.homeBudget.dao;

import javax.transaction.Transactional;

import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.User;
import org.springframework.data.repository.CrudRepository;

 
import com.homeBudget.model.Purchase;

import java.util.List;

@Transactional
public interface PurchaseDAO  extends CrudRepository<Purchase, Integer>{

    public List<Purchase> findByMonthlyBudgetAndCategory(MonthlyBudget monthlyBudget, Category category);
    public List<Purchase>findByCategory(Category category);
    List<Purchase> findByMonthlyBudgetAndStatus(MonthlyBudget monthlyBudget,Integer status);
    public List<Purchase> findByMonthlyBudget(MonthlyBudget monthlyBudget);
}
