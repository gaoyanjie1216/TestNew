package com.gyj.Test.Other;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * 实现n!实现使用BigDecimal类, 因为用int最多正确算到12!, 用long最多正确算到20!，实现方法都比较简单，
 * 但是转换为BigDecimal实现后可能看起来有点莫名其妙
 * Created by Gao on 2018/3/15.
 */
public class Factorial {

    /**
     * 非递归实现n!
     *
     * @param n
     * @return
     */
    private static int factorial(int n) {

        int result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i;
        }

        return result;
    }

    private static BigDecimal factorial1(int n) {

        BigDecimal result = new BigDecimal(1);
        for (int i = 2; i <= n; i++) {
            //将i转换为BigDecimal类型
            BigDecimal b = new BigDecimal(i);
            //不用result*a，因为BigDecimal类型没有定义*操作
            result = result.multiply(b);
        }

        return result;
    }

    private static int factorial2(int n) {

        int result = 1, i = 1;
        while (i <= n) {
            result *= i;
            i++;
        }

        return result;
    }

    private static int factorial5(int n) {

        //while语句中的表达式一般是关系表达或逻辑表达式，只要表达式的值为真(非0)即可继续循环
        int result = 1;
        while (n > 0) {
            //result *= n;
            //n--;
            result *= n--;
        }

        return result;
    }

    private static int factorial6(int n) {

        //先执行循环中的语句,然后再判断表达式是否为真, 如果为真则继续循环；如果为假, 则终止循环。因此, do-while循环至少要执行一次循环语句
        int result = 1, i = 1;
        do {
            result *= i;
            i++;
        } while (i <= n);

        return result;
    }

    /**
     * 递归实现n!
     *
     * @param n
     * @return
     */
    private static int factorial4(int n) {

        int result = 1;
        if (n == 0 || n == 1) {
            return result;
        } else {
            return n * factorial4(n - 1);
        }
    }

    private static BigDecimal factorial3(BigDecimal n) {

        BigDecimal result = new BigDecimal(1);
        if (n.equals(result) || n.intValue() == 0) {
            return result;
        } else {
            //n*f(n-1)
            return n.multiply(factorial3(n.subtract(result)));
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BigDecimal a = sc.nextBigDecimal();

        System.out.println(a + "!=" + factorial(a.intValue()));
        System.out.println(a + "!=" + factorial1(a.intValue()));
        System.out.println(a + "!=" + factorial2(a.intValue()));
        System.out.println(a + "!=" + factorial4(a.intValue()));
        System.out.println(a + "!=" + factorial5(a.intValue()));
        System.out.println(a + "!=" + factorial6(a.intValue()));
        System.out.println(a + "!=" + factorial3(a));
    }

}
