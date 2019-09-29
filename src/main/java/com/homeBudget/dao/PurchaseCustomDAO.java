package com.homeBudget.dao;

import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.Purchase;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PurchaseCustomDAO  {

      List<Purchase> findByMonthlyBudget(MonthlyBudget monthlyBudget);

      List<Purchase> findByMonthlyBudgetAndCategoryId(MonthlyBudget monthlyBudget,Integer categoryId);
}
