package com.gyj.Test.String;

import java.util.Stack;

/**
 * 简单字符串的逆序输出
 * Created by Gao on 2018/3/26.
 */
public class ReverseString {

    /**
     * 直接逆序输出
     *
     * @param str
     * @return
     */
    private static String reverseString(String str) {

        StringBuffer sb = new StringBuffer();
        for (int i = str.length() - 1; i >= 0; i--) {
            //System.out.print(str.charAt(i));
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }

    /**
     * 调用StringBuffer的reverse()方法
     *
     * @param str
     * @return
     */
    private static String reverseString1(String str) {

        StringBuffer sb = new StringBuffer(str);
        return sb.reverse().toString();
    }

    /**
     * 压栈出栈的方法实现字符串的倒序
     * @param str
     * @return
     */
    private static String reverseString2(String str) {

        Stack<Character> stack = new Stack<Character>();
        StringBuffer sb = new StringBuffer();

        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        String str = "abc de";
        String str1 = "abc de";
        String str2 = "abc de";  //适用于数字的倒序输出

        String s = reverseString(str);
        System.out.println(s);

        String s1 = reverseString1(str1);
        System.out.println(s1);

        String s2 = reverseString2(str2);
        System.out.println(s2);
    }
}
