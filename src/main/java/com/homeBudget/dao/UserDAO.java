package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

 
import com.homeBudget.model.User;
@Transactional
public interface UserDAO  extends CrudRepository<User, Integer>{

    public User findByEmail(String email);


}
