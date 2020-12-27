package com.homeBudget.impl;

import com.homeBudget.dao.ProductDAO;
import com.homeBudget.dao.ProductSellerDAO;
import com.homeBudget.model.ProductsSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





@Repository
public abstract class ProductSellerDAOImpl implements ProductSellerDAO {


    @Autowired
    private ProductDAO productDao;
    @Autowired
    private ProductSellerDAO productSellerDao;

    public List<ProductsSeller> getByProductStatus(Integer status) {
        List<ProductsSeller> productsSellers=new ArrayList<>();
        Iterator<ProductsSeller> iterator = productSellerDao.findAll().iterator();
        while (iterator.hasNext()) {
            ProductsSeller productsSeller = iterator.next();
            if (productsSeller.getProduct().getStatus().equals(status)) {
                productsSellers.add(productsSeller);
            }
        }
        return productsSellers;


    }
}
