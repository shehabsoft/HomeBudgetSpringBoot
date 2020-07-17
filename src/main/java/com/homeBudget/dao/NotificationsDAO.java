package com.homeBudget.dao;

import com.homeBudget.model.Notifications;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotificationsDAO extends CrudRepository<Notifications, Integer>{

    public List<Notifications> findByStatus(Integer code);


}
