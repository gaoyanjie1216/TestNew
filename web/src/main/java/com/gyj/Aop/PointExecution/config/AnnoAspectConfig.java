package com.gyj.Aop.PointExecution.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * //匹配方法标注有AdminOnly的注解的方法
 * @Pointcut("@annotation(com.imooc.anno.AdminOnly) && within(com.imooc..*)")
 * //匹配标注有NeedSecured的类底下的方法 //class级别
 * @Pointcut("@within(com.imooc.anno.NeedSecured) && within(com.imooc..*)")
 * //匹配标注有NeedSecured的类及其子类的方法 //runtime级别
 * 在spring context的环境下,二者没有区别
 * @Pointcut("@target(com.imooc.anno.NeedSecured) && within(com.imooc..*)")
 * //匹配传入的参数类标注有Repository注解的方法
 * @Pointcut("@args(com.imooc.anno.NeedSecured) && within(com.imooc..*)")
 */
@Aspect
@Component
public class AnnoAspectConfig {

//    @Pointcut("@args(com.imooc.anno.NeedSecured) && within(com.imooc..*)")
//    public void matchAnno(){}
//
//    @Before("matchAnno()")
//    public void before(){
//        System.out.println("");
//        System.out.println("###before");
//    }

}
