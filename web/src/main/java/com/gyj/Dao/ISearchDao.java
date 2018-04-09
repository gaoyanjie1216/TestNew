package com.gyj.Dao;

import com.gyj.entity.Search;
import com.gyj.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by Gao on 2017/12/15.
 */
public interface ISearchDao extends PagingAndSortingRepository<Search, Integer>, JpaSpecificationExecutor<Search>,
        JpaRepository<Search, Integer>, Serializable {

    //hql查询，Search是class,有分页的时候要用hql查询,写sql native为true的时候不行？
    @Query("SELECT DISTINCT s.conditions FROM  Search s WHERE s.loginName = ?1")
    Page<String> findConditionsByLoginName(String loginName, Pageable pageable);

    Page<UserInfo> findByLoginNameContaining(String loginName, Pageable pageable);
}
