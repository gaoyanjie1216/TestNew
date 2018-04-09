package com.gyj.Dao;

import com.gyj.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gao on 2017/11/30.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
