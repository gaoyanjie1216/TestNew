package com.gyj.Login.Service;

import com.gyj.Login.Entity.User;

/**
 * Created by Gao on 2018/4/12.
 */
public interface LoginService {

    /**
     * 校验用户是否登录
     * @param user
     * @return
     */
    public boolean verifyLogin(User user);
}
