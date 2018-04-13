package com.gyj.Login.Controller;

import com.gyj.Login.Entity.User;
import com.gyj.Login.Service.LoginService;
import com.gyj.Login.Config.WebSecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

/**
 * Created by Gao on 2018/4/12.
 */
@Controller
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/")
    public String index(@SessionAttribute(WebSecurityConfig.SESSON_KEY) String account, Model model) {

        return "index";
    }

    //loginVerify是对登录请求到数据库中进行验证用户名和密码，验证通过以后设置session，否则跳转到登录页面,
    //实际项目登录验证会使用登录验证框架：spring security 、shiro等，以及登录过程密码加密传输保存等
    @PostMapping("/loginVerify")
    public String loginVerify(String username, String password, HttpSession session) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        boolean flag = loginService.verifyLogin(user);
        if (flag) {
            session.setAttribute(WebSecurityConfig.SESSON_KEY, username);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSON_KEY);

        //这里还有点问题，注销的时候session里面没有值，"redirect:/login"报错
        //return "redirect:/login";
        return "login";
    }

}
