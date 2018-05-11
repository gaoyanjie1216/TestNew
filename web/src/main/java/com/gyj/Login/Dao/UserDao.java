package com.gyj.Login.Dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao on 2018/4/13.
 */
public class UserDao {

    //工具类的构造方法私有化
    private UserDao() {

    }

    //因为验证的信息是用户名、密码，可以用key、value 的形式，想到用map语法
    private static Map<String, String> map = new HashMap<>();

    //类加载，静态代码块执行
    static {
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
    }

    public static boolean checkUser(String username, String password) {

        return map.containsKey(username) && map.get(username).equals(password);
    }

}
