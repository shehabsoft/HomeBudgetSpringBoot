package com.homeBudget.impl;

import com.homeBudget.dao.MonthlyBudgetCategoryCustomDAO;
import com.homeBudget.model.Category;
import com.homeBudget.model.MonthlyBudget;
import com.homeBudget.model.MonthlyBudgetCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shehab.tarek on 9/25/2019.
 */
@Repository
public class MonthlyBudgetCategoryCustomDAOImpl implements MonthlyBudgetCategoryCustomDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<MonthlyBudgetCategory> findByMonthlyBudget(MonthlyBudget monthlyBudgt, Integer categoryTypeId) {

        Query query = entityManager.createNativeQuery("SELECT em.categories_ID,em.actual_value  FROM  monthly_budget_category as em,Category c  " +
                "WHERE em.categories_ID=c.id and  em.MonthlyBudget_ID = ? and c.category_type_id= ? ");
        query.setParameter(1, monthlyBudgt.getId());
        query.setParameter(2,categoryTypeId);
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        List<MonthlyBudgetCategory> monthlyBudgetCategories=new ArrayList<>();
        while (itr.hasNext()) {
            Category category=new Category();
            MonthlyBudgetCategory monthlyBudgetCategory=new MonthlyBudgetCategory();
            Object[] objects = (Object[]) itr.next();

            category =entityManager.find(Category.class,objects[0]);
            if(objects[1]!=null) {
                category.setActualValue(Double.valueOf(objects[1].toString()));
            }
            monthlyBudgetCategory.setCategory(category);
            monthlyBudgetCategories.add(monthlyBudgetCategory);


        }

        return monthlyBudgetCategories;
    }
}
