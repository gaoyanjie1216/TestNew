package com.gyj.Login.Service.Impl;

import com.gyj.Login.Dao.LoginDao;
import com.gyj.Login.Entity.User;
import com.gyj.Login.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gao on 2018/4/12.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public boolean verifyLogin(User user) {
        List<User> userList = loginDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        return userList.size() > 0;
    }
}
