package com.gyj.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Gao on 2017/12/16.
 */
@Aspect
@Component
public class HttpAspect {

    public static final Log log = LogFactory.getLog(HttpAspect.class);

    //@Pointcut("execution(public * com.gyj.common.Controller.GirlController.*(..))")
    public void logger() {

    }

   /* @Before("logger()")
    public void doBefore() {
        log.info("11111111");
    }

    @After("logger()")
    public void doAfter() {
        log.info("22222222");
    }*/

}
