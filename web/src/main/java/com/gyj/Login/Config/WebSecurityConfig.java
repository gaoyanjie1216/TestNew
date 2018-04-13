package com.gyj.Login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录配置
 * Created by Gao on 2018/4/12.
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    public static final String SESSON_KEY = "username";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    /**
     *流程为：登录页面发起请求-->拦截器拦截匹配的url判断session-->后台验证/设置session-->返回
     这里主要通过自定义拦截器的方式，继承WebMvcConfigurerAdapter和HandlerInterceptorAdapter来实现拦截器对登录请求进行拦截和session的判断，
     我这里都写在WebSecurityConfig.java中, 其中WebMvcConfigurerAdapter是Spring提供的基础类，可以通过重写 addInterceptors 方法添加注册拦截器来
     组成一个拦截链，以及用于添加拦截规则和排除不用的拦截，如下：
     */
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/**");
    }

    /**
     * 其中HandlerInterceptorAdapter是spring mvc提供的适配器，继承此类，可以非常方便的实现自己的拦截器，它有三个方法：
     * preHandle、postHandle、afterCompletion。preHandle在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
     * postHandle在业务处理器处理请求执行完成后，生成视图之前执行。
     * afterCompletion在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。
     * 此项目中只重写了preHandle,对请求进行session判断和跳转到自定义的页面，如下：
     */
    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

            HttpSession session = request.getSession();

            //判断是否已有该用户登录的session
            if (session.getAttribute(SESSON_KEY) != null) {
                return true;
            }

            //跳转到登录页
            response.sendRedirect("/login");

            return false;
        }
    }
}
