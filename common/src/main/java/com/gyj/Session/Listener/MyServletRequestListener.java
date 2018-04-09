package com.gyj.Session.Listener;

/**
 * Created by Gao on 2017/12/27.
 */

import com.gyj.Session.Entity.User;
import com.gyj.Session.Utils.SessionUtil;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    private ArrayList<User> userList;//在线用户List

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent arg0) {

        userList = (ArrayList<User>) arg0.getServletContext().getAttribute("userList");

        if (userList == null)
            userList = new ArrayList<User>();

        HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
        String sessionIdString = request.getSession().getId();

        if (SessionUtil.getUserBySessionId(userList, sessionIdString) == null) {
            User user = new User();
            user.setSessionId(sessionIdString);
            user.setFirstTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            user.setIp(request.getRemoteAddr());
            userList.add(user);
        }
        arg0.getServletContext().setAttribute("userList", userList);
    }

}
