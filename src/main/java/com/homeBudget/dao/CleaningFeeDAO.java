package com.homeBudget.dao;

import com.homeBudget.model.CleaningFee;
import com.homeBudget.model.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CleaningFeeDAO extends CrudRepository<CleaningFee, Integer>{


}
