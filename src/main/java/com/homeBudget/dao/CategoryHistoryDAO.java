package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.Category;
import com.homeBudget.model.CategoryHistory;
@Transactional
public interface CategoryHistoryDAO  extends CrudRepository<CategoryHistory, Integer>{

}
