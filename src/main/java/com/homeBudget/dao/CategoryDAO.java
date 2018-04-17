package com.homeBudget.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.Category;
import com.homeBudget.model.CategoryHistory;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.User;
@Transactional
public interface CategoryDAO  extends CrudRepository<Category, Integer>{
   public List<Category> findByMonthlyBudgetsAndUserAndCategoryTypeId(MonthlyBudget monthlyBudget,User user,int categoryTypeId);
   public List<Category> findByUserAndCategoryTypeId(User user,int categoryTypeId);
   
  
}
