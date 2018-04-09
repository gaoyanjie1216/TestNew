package com.gyj.Dao;

/**
 * Created by Gao on 2017/12/20.
 */

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface SuperRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
        QueryDslPredicateExecutor<T> {

    Integer deleteByIdIn(Collection<ID> ids);

    List<T> findAll(Predicate predicate);

    List<T> findAll();

    List<T> findAll(Iterable<ID> ids);
}

