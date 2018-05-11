package com.gyj.common.Dao;

/**
 * Created by Gao on 2017/11/24.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

//@NoRepositoryBean是为了避免SpringDataJPA自动实例化才添加的
@NoRepositoryBean
public interface BaseJPA<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {

}

