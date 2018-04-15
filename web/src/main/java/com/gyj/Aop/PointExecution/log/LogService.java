package com.gyj.Aop.PointExecution.log;

import com.gyj.Aop.PointExecution.anno.AdminOnly;
import com.gyj.Aop.PointExecution.bean.Product;
import org.springframework.stereotype.Component;

/**
 * Created Gao on 2018.4.15
 */
@Component
public class LogService implements Loggable{
    @Override
    @AdminOnly
    public void log() {
        System.out.println("log from LogService");
    }

    public void annoArg(Product product){
        System.out.println("execute log service annoArg");
    }
}
