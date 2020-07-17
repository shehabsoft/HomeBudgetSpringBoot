package com.homeBudget.dao;

import com.homeBudget.model.NotificationTemplate;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface NotificationTemplateDAO extends CrudRepository<NotificationTemplate, Integer>{

    public NotificationTemplate findByCode(Integer code);


}
