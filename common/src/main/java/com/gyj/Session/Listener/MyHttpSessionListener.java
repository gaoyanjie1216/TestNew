package com.gyj.Session.Listener;

/**
 * Created by Gao on 2017/12/27.
 */

import com.gyj.Session.Entity.User;
import com.gyj.Session.Utils.SessionUtil;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    private int userNumber = 0;

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        userNumber++;
        arg0.getSession().getServletContext().setAttribute("userNumber", userNumber);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        userNumber--;
        arg0.getSession().getServletContext().setAttribute("userNumber", userNumber);

        ArrayList<User> userList = null;//在线用户List
        userList = (ArrayList<User>) arg0.getSession().getServletContext().getAttribute("userList");
        if (SessionUtil.getUserBySessionId(userList, arg0.getSession().getId()) != null) {
            userList.remove(SessionUtil.getUserBySessionId(userList, arg0.getSession().getId()));
        }
    }

}
