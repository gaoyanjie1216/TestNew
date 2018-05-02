package com.gyj.Test.String;

import org.apache.commons.collections.ListUtils;

import java.util.*;

/**
 * 出现次数最多的一个字符
 * Created by Gao on 2018/3/28.
 */
public class AppearMostChar {

    /**
     * 每次取出字符串的第一个字符，将字符串中与第一个字符相同的字符全部删除掉，然后通过计算删除前后字符串的长度
     * 来确定该字符在字符串中出现的次数，最终比较出出现最多次的字符。
     * 如果有两个或者两个以上的字符出现次数一致，则出现的是最先出现的那个字符
     */
    private static Character appearMostChar(String str) {

        if (str == null || str.length() == 0) {
            return '\0';
        }

        int maxLength = 0;
        String maxStr = "";
        while (str.length() > 0) {
            String first = str.substring(0, 1);
            int length = str.length();
            str = str.replaceAll(first, "");
            if (maxLength <= length - str.length()) {   // <= 出现次数最多的最后一个字符
                maxLength = length - str.length();
                maxStr = first;
            }
        }
        System.out.println("appearMostChar 函数出现次数最多的字符是：" + maxStr.charAt(0) + "  出现的次数是：" + maxLength);

        return maxStr.charAt(0);
    }

    //出现次数最多的一个字符
    private static Character appearMostChar1(String str) {

        if (str == null || str.length() == 0) {
            return '\0';
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.toCharArray().length; i++) {
            if (map.containsKey(str.charAt(i))) {
                map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
            } else {
                map.put(str.charAt(i), 1);
            }
        }

        int maxNum = 0;
        char c = str.charAt(0);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxNum) {  // >= maxMun输出是最后一个出现次数最多的字符，如有多个出现次数一样的字符
                maxNum = entry.getValue();
                c = entry.getKey();
            }
        }
        System.out.println("appearMostChar1函数出现次数最多的字符是：" + c + "  出现的次数是：" + maxNum);

        return c;
    }

    private static List appearMostChar2(String str) {

        if (str == null || str.length() == 0) {
            return ListUtils.EMPTY_LIST;
            //return null;  上面的List<Character>
        }

        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        int maxNum = 0;
        List<Character> list = new ArrayList<>();
       /* for (Map.Entry<Character, Object> entry : map.entrySet()) {
            if ((Integer) entry.getValue() > maxNum) {
                maxNum = (Integer) entry.getValue();
            }
        }*/
        maxNum = Collections.max(map.values()); //Map<Character, Integer>  map.values() 类型不能有Object

        //Map中是一个key有且只有一个value.但是一个value可以对应多个key值.一般都是通过key, 然后map.get(key)获得到value.
        //反向想通过value获得key的值, 可能有多个key，考虑到放进list中
        for (char key : map.keySet()) {
            if (map.get(key).equals(maxNum)) {
                list.add(key);
            }
        }

        System.out.print("appearMostChar2函数出现次数最多的字符是：" + "");
        for (char c2 : list) {
            System.out.print(c2 + " ");
        }
        System.out.print("  出现的次数是：" + maxNum);
        //list.forEach(System.out::print);

        return list;
    }


    public static void main(String[] args) {

        String str = "aabbccddefg";
        appearMostChar(str);
        appearMostChar1(str);
        appearMostChar2(str);
    }
}

/**
 appearMostChar 函数出现次数最多的字符是：d  出现的次数是：2
 appearMostChar1函数出现次数最多的字符是：a  出现的次数是：2
 appearMostChar2函数出现次数最多的字符是：a b c d   出现的次数是：2
 */