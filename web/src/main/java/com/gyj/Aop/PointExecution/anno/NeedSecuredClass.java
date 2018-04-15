package com.gyj.Aop.PointExecution.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Inherited
public @interface NeedSecuredClass {
}
