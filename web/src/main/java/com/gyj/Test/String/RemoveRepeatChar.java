package com.gyj.Test.String;

import java.util.TreeSet;

/**
 * 去除字符串中重复的字符
 * Created by Gao on 2018/3/25.
 */
public class RemoveRepeatChar {

    /**
     * 普通遍历
     *
     * @param str
     * @return
     */
    private static String removeRepeatChar(String str) {

        if (str.length() == 0)
            return "";

        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (newStr.indexOf(str.charAt(i)) == -1) {
                newStr += str.charAt(i);
            }
        }

        return newStr;
    }


    private static String removeRepeatChar1(String str) {

        if (str.length() == 0)
            return "";

        //把字符串数组放入TreeSet中，根据set元素不重复的特性去掉重复元素。根据treeSet的有序性排序
        TreeSet<String> newStr = new TreeSet<String>();
        //System.out.print(str.toCharArray()); //ayzbcdrfgs
        for (char s : str.toCharArray()) {
            newStr.add(String.valueOf(s));
        }
        //return newStr.toString();  输出 [a, b, c, d, f, g, r, s, y, z]字符数组

        //将字符数组转换成字符串
        str = "";
        for (String s1 : newStr) {
            str += s1;
        }

        return str;
    }

    /**
     * 如果把空字符串 ("") 用作 separator，那么 stringObject 中的每个字符之间都会被分割。
     * "hello".split("")	//可返回 ["h", "e", "l", "l", "o"]
     * "How are you doing today?" 则"hello".split(" ")  返回How,are,you,doing,today?
     * String input = "你好acd123"; 过滤掉输入的字符input.replaceAll("[^a-zA-Z]", "");
     *
     * @param str
     * @return
     */
    private static String removeRepeatChar2(String str) {

        if (str.length() == 0)
            return "";

        TreeSet<String> newStr = new TreeSet<String>();
        //把String变成单一字符数组
        String[] chars = str.split("");  //chars 表示为{"a", "a", "y", "z" ...}

        for (String s : chars) {
            newStr.add(s);
        }

        //System.out.print(newStr);  // [a, b, c, d, f, g, r, s, y, z]
        //return newStr.toString();  // [a, b, c, d, f, g, r, s, y, z]字符数组

        //将字符数组转换成字符串
        str = "";
        for (String s1 : newStr) {
            str += s1;
        }

        return str;   //输出abcdfgrsyz
    }

    /**
     * 数组来哈希，时间复杂度为O(n)
     * 初始化一个int数组hash,数组的角标和数组的值正好构成一对<key,value>;
     * 遍历字符串，将每个字符放入数组中对应的位置，出现过得字符，其数组值为1
     * 遍历数组，输出数组中数组值为1所对应的字符
     *
     * @param str
     * @return
     */
    private static String removeRepeatChar3(String str) {

        if (str.length() == 0)
            return "";

        int hash[] = new int[256];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (hash[c] == 0) {
                hash[c] = 1;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != 0) {     //或hash[i] == 1
                char c = (char) i;
                //System.out.print(c);
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "aabbdcc";
        String resultStr = removeRepeatChar(str);
        System.out.print(resultStr);
        System.out.println();

        String resultStr1 = removeRepeatChar1(str);
        System.out.print(resultStr1);
        System.out.println();

        String resultStr2 = removeRepeatChar2(str);
        System.out.print(resultStr2);
        System.out.println();

        String resultStr3 = removeRepeatChar3(str);
        System.out.print(resultStr3);
    }

}
