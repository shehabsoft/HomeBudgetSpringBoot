package com.homeBudget.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homeBudget.model.InfoTraceLog;
 
@Transactional
public interface InfoTraceLogDAO  extends CrudRepository<InfoTraceLog, Integer>{

}
