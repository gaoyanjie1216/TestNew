package com.gyj.Test.Other;

/**
 * 题目：写一个函数，输入n，求斐波那契数列的第n项。
 * 　　　　　　　　　　　　　　     0,                n=1
 * 7 斐波那契数列定义如下： f(n)  =   1,                n=2
 * 8                               f(n-1)+f(n-2),    n>2
 * Created by Gao on 2018/3/26.
 */
public class Fibonacci {


    /**
     * 递归
     *
     * @param n
     * @return
     */
    private static int fibonacci(int n) {

        if (n == 1 || n == 2) {
            return 1;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * 循环非递归
     *
     * @param n
     */
    private static int fibonacci1(int n) {

        if (n == 1 || n == 2) {
            return 1;
        }

        int a = 1, b = 1, c = 2;
        for (int i = 2; i < n; i++) {

            c = a + b;
            a = b;
            b = c;
        }

        return c;
    }


    public static void main(String[] args) {

        // 1 1 2 3 5 8 13 21
        int a = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            a = fibonacci(20);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("递归fibonacci输出值是：" + a + "   运行时间：" + duration / 100000);
        System.out.println();

        int b = 0;
        long startTime1 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            b = fibonacci1(20);
        }
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.println("非递归fibonacci1输出值是：" + b + "   运行时间：" + duration1 / 100000);
        System.out.println();
    }
}

/**
 * 以n=8为例
 * 递归fibonacci输出值是：21      运行时间：93
 * 非递归fibonacci1输出值是：21   运行时间：11
 *
 * n=20
 * 递归fibonacci输出值是：6765   运行时间：21408
   非递归fibonacci1输出值是：6765   运行时间：15
 */
