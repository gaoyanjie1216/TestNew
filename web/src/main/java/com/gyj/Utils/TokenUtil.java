package com.gyj.Utils;

import com.google.common.cache.Cache;
import com.gyj.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gao on 2017/12/14.
 */
public class TokenUtil {

    public static final String TOKEN_KEY = "token";

    // guava cache
    public static Cache<String, UserInfo> loginUsers;

    /**
     * 获取当前请求 token
     * @return
     */
    public static String getToken() {
        String token = null;
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        if (StringUtils.isBlank(token)) {
            token = request.getHeader(TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            token = (String) request.getAttribute(TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            Object obj = request.getSession().getAttribute(TOKEN_KEY);
            if (obj != null) {
                token = obj.toString();
            }
        }

        if (StringUtils.isBlank(token)) {
            token = "";
        }

        return token;
    }
}
