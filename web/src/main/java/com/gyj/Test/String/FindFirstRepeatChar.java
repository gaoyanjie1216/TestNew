package com.gyj.Test.String;

import java.util.HashSet;

/**
 * 首个重复字符
 * Created by Gao on 2018/3/25.
 */
public class FindFirstRepeatChar {

    private static char findFirstRepeatChar(String str) {

        HashSet<Character> set = new HashSet<>();
        char c1;
        for (char c : str.toCharArray()) {
            if (!set.add(c)) {
                c1 = c;
                return c1; //或者是break;  如果把return c1去掉，则是最后一个重复的字符
            }
        }

        return '\0';
    }

    private static char findFirstRepeatChar1(String str) {

        HashSet<Character> set = new HashSet<Character>();
        char[] a = str.toCharArray();  //toCharArray()的用法：将字符串对象中的字符转换为一个字符数组
        for (int i = 0; i < a.length; i++) {
            //通过往hashset塞值（hashset不准有重复元素），判断当前一段数据中是否有重复元素，一但有，立刻返回
            if (!set.add(a[i])) {
                return a[i];
            }
        }

        return '\0';
    }

    /**
     * 空间换时间，时间复杂度为O(N)
     * @param str
     * @return
     */
    private static char findFirstRepeatChar2(String str) {

        //初始化一个256位的初始值时0的数组
        int[] hash = new int[256];
        for (char c : str.toCharArray()) {
            if (hash[c] == 0) {
                hash[c] = 1;
            } else {
                return c;
            }
        }

        return '\0';
    }


    public static void main(String[] args) {

        String str = "abdccddd";

       /* char firstRepeatChar = findFirstRepeatChar(str);
        System.out.print(firstRepeatChar);
        System.out.println();

        char firstRepeatChar1 = findFirstRepeatChar1(str);
        System.out.print(firstRepeatChar1);
        System.out.println();

        char firstRepeatChar2 = findFirstRepeatChar2(str);
        System.out.print(firstRepeatChar2);*/


        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            findFirstRepeatChar(str);
        }
        long endTime = System.nanoTime();
        System.out.println("findFirstRepeatChar:" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            findFirstRepeatChar1(str);
        }
        long endTime1 = System.nanoTime();
        System.out.println("findFirstRepeatChar1:" + (endTime1 - startTime1) / 1000);

        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            findFirstRepeatChar2(str);
        }
        long endTime2 = System.nanoTime();
        System.out.println("findFirstRepeatChar2:" + (endTime2 - startTime2) / 1000);
    }
}


/**
 * 三者运行时间对比
 findFirstRepeatChar:1578
 findFirstRepeatChar1:1146
 findFirstRepeatChar2:621
 */