package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.Location;
@Transactional
public interface LocationDAO  extends CrudRepository<Location, Integer>{

}
