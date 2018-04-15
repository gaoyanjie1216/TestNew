package com.gyj.Aop.PointExecution.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Before("matchAnno()")
 * @After("matchAnno())") //相当于finally
 * @AfterReturning("matchException()")
 * @AfterThrowing("matchException()")
 * @Around("matchException()")
 * @Before(value = "matchLongArg() && args(productId)")
 * public void beforeWithArgs(Long productId)
 * @AfterReturning(value = "matchReturn()",returning = "returnValue")
 * public void getReulst(Object returnValue)
 */
@Aspect
@Component
public class AdviceAspectConfig {

    /******pointcut********/

    @Pointcut("@annotation(com.gyj.Aop.PointExecution.anno.AdminOnly) && within(com.gyj.Aop.PointExecution..*)")
    public void matchAnno(){}

    @Pointcut("execution(* *..find*(Long)) && within(com.gyj.Aop.PointExecution..*) ")
    public void matchLongArg(){}

    @Pointcut("execution(public * com.gyj.Aop.PointExecution.service..*Service.*(..) throws java.lang.IllegalAccessException) && within(com.gyj.Aop.PointExecution..*)")
    public void matchException(){}

    @Pointcut("execution(String com.gyj.Aop.PointExecution..*.*(..)) && within(com.gyj.Aop.PointExecution..*)")
    public void matchReturn(){}


    /******advice********/
    @Before("matchLongArg() && args(productId)")
    public void before(Long productId){
        System.out.println("###before,get args:"+productId);
    }

   /* @Around("matchException()")
    public java.lang.Object after(ProceedingJoinPoint joinPoint){
        System.out.println("###before");
        java.lang.Object result = null;
        try{
            result = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("###after returning");
        }catch (Throwable e){
            System.out.println("###ex");
            //throw
            e.printStackTrace();
        }finally {
            System.out.println("###finally");
        }
        return result;
    }*/

}
