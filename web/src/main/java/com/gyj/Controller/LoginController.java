package com.gyj.Controller;

/**
 * Created by Gao on 2017/11/22.
 */

import com.gyj.Dao.IUserDao;
import com.gyj.entity.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    @Autowired
    private IUserDao userDao;

    @ApiOperation(value = "用户登录", notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(UserInfo user, HttpServletRequest request) {

        //登录成功
        boolean flag = true;
        String result = "登录成功";
        //根据用户名查询用户是否存在
        UserInfo userEntity = userDao.findOne(new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get("name"), user.getName()));
                return null;
            }
        });

        //用户不存在
        if (userEntity == null) {
            flag = false;
            result = "用户不存在，登录失败";
        }

        //密码错误
        else if (!userEntity.getPassword().equals(user.getPassword())) {
            flag = false;
            result = "用户密码不相符，登录失败";
        }

        //登录成功
        if (flag) {
            //将用户写入session
            request.getSession().setAttribute("_session_user", userEntity);
        }

        return result;
    }
}