package com.homeBudget.impl;

import com.homeBudget.dao.MonthlyBudgetCategoryCustomDAO;
import com.homeBudget.dao.PurchaseCustomDAO;
import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.MonthlyBudgetCategory;
import com.homeBudget.model.Purchase;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shehab.tarek on 9/25/2019.
 */
@Repository
public class PurchaseCustomDAOImpl implements PurchaseCustomDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Purchase> findByMonthlyBudget(MonthlyBudget monthlyBudgt) {

        Query query = entityManager.createNativeQuery("SELECT ID FROM   purchase where monthlyBudget_ID = ? ");
        query.setParameter(1, monthlyBudgt.getId());

        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        List<Purchase> purchases = new ArrayList<>();
        while (itr.hasNext()) {
            Purchase purchase = new Purchase();
           Integer id = (Integer) itr.next();

            purchase = entityManager.find(Purchase.class, id);


            purchases.add(purchase);

        }

        return purchases;
    }
    public List<Purchase> findByMonthlyBudgetAndCategoryId(MonthlyBudget monthlyBudget,Integer categoryId)
    {
        Query query = entityManager.createNativeQuery("SELECT ID FROM   purchase where monthlyBudget_ID = ? and category_id =?");
        query.setParameter(1, monthlyBudget.getId());
        query.setParameter(2, categoryId);

        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        List<Purchase> purchases = new ArrayList<>();
        while (itr.hasNext()) {
            Purchase purchase = new Purchase();
            Integer id = (Integer) itr.next();

            purchase = entityManager.find(Purchase.class, id);


            purchases.add(purchase);

        }

        return purchases;
    }
}
