package com.homeBudget.dao;

import com.homeBudget.model.Order;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrderDAO extends CrudRepository<Order, Integer>{

}
