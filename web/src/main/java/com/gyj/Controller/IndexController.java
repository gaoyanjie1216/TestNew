package com.gyj.Controller;

/**
 * Created by Gao on 2017/11/22.
 */

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IndexController {

    /**
     * 初始化登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login_view", method = RequestMethod.GET)
    public String login_view() {
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
