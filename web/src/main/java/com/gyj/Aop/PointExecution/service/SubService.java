package com.gyj.Aop.PointExecution.service;

import org.springframework.stereotype.Component;

/**
 * Created by Gao 2018-4-15.
 */
@Component
public class SubService extends ProductService {

    public void demo() {
        System.out.println("execute sub service method");
    }
}
