package com.gyj.Login.Controller;

/**
 * Created by Gao on 2018/4/13.
 */
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.杀死session
        //request.getSession()底层的实现，如果发现没有session立即创建
        if (request.getSession(false) != null //如果没有对应的session，返回null，不会创建session
                && request.getSession().getAttribute("user") != null) {
            //  request.getSession(false)!=null && request.getSession().getAttribute("user")!=null  表示有sesson，并且有登录标记
            request.getSession().invalidate();
        }
        //2.重定向到主页
        response.sendRedirect(request.getContextPath() + "/loginout/index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
