package com.gyj.Test.Other;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by Gao on 2018/3/15.
 */
public class FactorialTest {

    /**
     * 非递归实现
     * @param n
     * @return
     */
    private static int factorial(int n) {

        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    private static int factorial1(int n) {

        int result = 1, i = 1;
        while (i <= n) {
            result *= i;
            i++;
        }

        return result;
    }

    private static int factorial2(int n) {

        int result = 1;
        while (n > 0) {
            result *= n;
            n--;
        }

        return result;
    }

    private static int factorial3(int n) {

        int result = 1, i = 1;
        do {
            result *= i;
            i++;
        } while (i <= n);

        return result;
    }

    private static BigDecimal factorial4(int n) {

        BigDecimal result = new BigDecimal(1);
        for (int i = 2; i <= n; i++) {
            BigDecimal b = new BigDecimal(i);
            result = result.multiply(b);
        }

        return result;
    }

    /**
     * 递归实现
     * @param n
     * @return
     */
    private static int factorial5(int n) {

        int result = 1;
        if (n == 0 || n == 1) {
            return result;
        } else {
            return n * factorial5(n - 1);
        }
    }

    private static BigDecimal factorial6(BigDecimal n) {

        BigDecimal result = new BigDecimal(1);
        if (n.intValue() == 0 || n.intValue() == 1) {
            return result;
        } else {
            return n.multiply(factorial6(n.subtract(result)));
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BigDecimal a = sc.nextBigDecimal();
        System.out.println(a + "!=" + factorial(a.intValue()));
        System.out.println(a + "!=" + factorial1(a.intValue()));
        System.out.println(a + "!=" + factorial2(a.intValue()));
        System.out.println(a + "!=" + factorial3(a.intValue()));
        System.out.println(a + "!=" + factorial4(a.intValue()));
        System.out.println(a + "!=" + factorial5(a.intValue()));
        System.out.println(a + "!=" + factorial6(a));
    }

}
