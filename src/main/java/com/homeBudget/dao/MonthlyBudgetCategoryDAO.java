package com.homeBudget.dao;

import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.MonthlyBudgetCategory;
import com.homeBudget.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MonthlyBudgetCategoryDAO extends CrudRepository<MonthlyBudgetCategory, Integer>{
  List<MonthlyBudgetCategory> findByCategory(Category category);
  List<MonthlyBudgetCategory> findByMonthlyBudget(MonthlyBudget monthlyBudget);

}
