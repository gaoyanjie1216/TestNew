package com.gyj.Session.Utils;

import com.gyj.Session.Entity.User;

import java.util.ArrayList;

/**
 * Created by Gao on 2017/12/27.
 */
public class SessionUtil {
    public static Object getUserBySessionId(ArrayList<User> userList, String sessionId) {

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getSessionId().equals(sessionId)) {
                return user;
            }
        }

        return null;
    }

}
