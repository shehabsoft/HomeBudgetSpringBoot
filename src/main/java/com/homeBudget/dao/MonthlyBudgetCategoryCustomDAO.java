package com.homeBudget.dao;

import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.MonthlyBudgetCategory;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MonthlyBudgetCategoryCustomDAO  {
  List<MonthlyBudgetCategory> findByMonthlyBudget(MonthlyBudget monthlyBudgt,Integer categoryTypeId);

}
