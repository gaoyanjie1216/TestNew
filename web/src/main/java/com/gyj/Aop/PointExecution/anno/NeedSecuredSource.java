package com.gyj.Aop.PointExecution.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Inherited
public @interface NeedSecuredSource {
}
