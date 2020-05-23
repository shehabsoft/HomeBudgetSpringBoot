package com.homeBudget.dao;

import com.homeBudget.model.Product;
import com.homeBudget.model.ProductsSeller;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProductSellerDAO extends CrudRepository<ProductsSeller, Integer>{

    public ProductsSeller findByProduct(Product product);

}
