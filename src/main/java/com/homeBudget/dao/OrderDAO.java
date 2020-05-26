package com.homeBudget.dao;

import com.homeBudget.model.Order;
import com.homeBudget.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderDAO extends CrudRepository<Order, Integer>{

    List<Order>findByStatus(Integer status);
    List<Order>findByUser(User user);
    List<Order>findBySellerUserAndStatus(User user,Integer status);
}
