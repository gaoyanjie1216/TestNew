package com.gyj.Login.Dao;

import com.gyj.Login.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Gao on 2018/4/12.
 */
public interface LoginDao extends CrudRepository<User, Integer> {

    public List<User> findByUsernameAndPassword(String username, String password);
}
