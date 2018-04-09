package com.gyj.Test.Number;

import java.util.Stack;

/**
 * 整数逆序输出
 * Created by Gao on 2018/3/26.
 */
public class ReversePrint {

    /**
     * 堆栈逆序输出
     *
     * @param n
     * @return
     */
    private static int reversePrint(int n) {

        if (n < 0) {
            return 0;
        }

        StringBuffer sb = new StringBuffer();
        Stack<Character> stack = new Stack<>();

        for (char c : String.valueOf(n).toCharArray()) {
            stack.push(c);
        }
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return Integer.parseInt(sb.toString());
    }

    /**
     * 从尾到头循环输出
     *
     * @param n
     * @return
     */
    private static int reversePrint1(int n) {

        if (n < 0) {
            return 0;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = String.valueOf(n).length() - 1; i >= 0; i--) {
            sb.append(String.valueOf(n).charAt(i));
        }

        return Integer.parseInt(sb.toString());
    }

    /**
     * 递归计算输出
     *
     * @param n
     * @return
     */
    private static int reversePrint2(int n) {

        StringBuffer sb = new StringBuffer();
        if (n < 10) {
            return n;
        }

        //需要加上Integer.parseInt(), 不然1234500输出了0054321
        return Integer.parseInt(sb.append(n % 10).append(reversePrint2(n / 10)).toString());

    }

    private static String reversePrint2_1(int n) {

        if (n < 10) {
            return "" + n;
        } else {
            return "" + (n % 10) + reversePrint2_1(n / 10);
        }
    }

    private static int reversePrint3(int n) {

        int result = 0;
        if (n < 0) {
            return 0;
        }
        while (n != 0) {
            result = result * 10 + n % 10;
            n /= 10;    // 1/10=0, 1%10=1
        }

        return result;
    }


    public static void main(String[] args) {

        int n = 1234500;

        int a = reversePrint(n);
        System.out.print(a);
        System.out.println();

        int b = reversePrint1(n);
        System.out.print(b);
        System.out.println();

        int c = reversePrint2(n);
        System.out.print(c);
        System.out.println();

        int d = reversePrint3(n);
        System.out.print(d);

    }
}
