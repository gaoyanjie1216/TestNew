package com.gyj;

import com.gyj.Aop.PointExecution.bean.Product;
import com.gyj.Aop.PointExecution.log.LogService;
import com.gyj.Aop.PointExecution.service.ProductService;
import com.gyj.Aop.PointExecution.service.SubService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Gao on 2018/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PointExecutionTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private SubService subService;

    @Autowired
    private LogService logService;

    @Test
    public void test() {

        System.out.println("###begin test###");
        productService.findById(1L);
        productService.findByTwoArgs(1L, "hello");
        productService.getName();
        subService.demo();
        try {
            productService.exDemo();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        logService.log();
        productService.log();
        logService.annoArg(new Product());
    }
}
