package com.gyj.Dao;

import com.gyj.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao on 2017/11/22.
 */
@Transactional
public interface IUserDao extends JpaRepository<UserInfo, Integer>, SuperRepository<UserInfo, Integer>, PagingAndSortingRepository<UserInfo, Integer>,
        JpaSpecificationExecutor<UserInfo>, QueryDslPredicateExecutor<UserInfo>, Serializable {

    @Query(value = "select * from user_info where age > ?1", nativeQuery = true)
    public List<UserInfo> nativeQuery(int age);

    //where x.age <= ?1
    List<UserInfo> findByAgeLessThanEqual(int age);

    //where x.age > ?1
    List<UserInfo> findByAgeGreaterThan(int age);

    //where x.age between ?1 and ?2
    List<UserInfo> findByAgeBetween(int ageStart, int ageEnd);

    @Transactional //TransactionRequiredException不加的话会抛出事务异常
    @Modifying     //@Query注解好像只是用来查询的，但是如果配合@Modifying注解一共使用，则可以完成数据的删除、添加、更新操作
    @Query(value = "delete from user_info where name = ?1 and age = ?2", nativeQuery = true)
    public void deleteNativeQueryByNameAndAge(String name, Integer age);

    void deleteByAge(Integer age);

    Page<UserInfo> findAll(Pageable pageable);

    Page<UserInfo> findByNameContaining(String name, Pageable pageable);

    List<UserInfo> findByNameContaining(String name);

    @Query(value = "select * from user_info where Date(create_time)>= ?1 and Date(create_time)<= ?2", nativeQuery = true)
    List<UserInfo> findBycreateTimeBetween(String createTimeStart, String createTimeEnd);

    @Query(value = "select name, address, age, create_time from user_info where Date(create_time)>= ?1 and Date(create_time)<= ?2", nativeQuery = true)
    List<String> findConditionsBycreateTimeBetween(String createTimeStart, String createTimeEnd);

}