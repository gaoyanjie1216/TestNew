package com.gyj.common.Dao;

import com.gyj.common.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gao on 2017/11/30.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
