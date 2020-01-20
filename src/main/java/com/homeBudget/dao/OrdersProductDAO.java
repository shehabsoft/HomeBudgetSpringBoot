package com.homeBudget.dao;

import com.homeBudget.model.Order;
import com.homeBudget.model.OrdersProduct;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrdersProductDAO extends CrudRepository<OrdersProduct, Integer>{

}
