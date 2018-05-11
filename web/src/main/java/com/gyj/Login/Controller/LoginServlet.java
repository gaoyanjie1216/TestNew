package com.gyj.Login.Controller;

import com.gyj.Login.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Gao on 2018/4/13.
 */
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");

        if (UserDao.checkUser(userName, passWord)) {
            //3.如果正确登录后重定向到主页
            request.getSession().setAttribute("user", userName);//设置session标记user
            response.sendRedirect(request.getContextPath() + "/loginout/index.jsp");
        } else {
            //4.如果错误提示
            response.getWriter().write("用户名密码不正确!");
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
