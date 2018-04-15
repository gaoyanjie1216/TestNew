package com.gyj.Aop.AopDemo.dao;

import com.gyj.Aop.AopDemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gao on 2018/4/14.
 */
public interface ProductDao extends JpaRepository<Product, Long> {

    public Product findById(Long id);
}
