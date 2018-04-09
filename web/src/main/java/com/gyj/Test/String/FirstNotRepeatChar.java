package com.gyj.Test.String;

import java.util.*;

/**
 * 字符流中第一个不重复的字符
 * 请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个字符"go"时，
 * 第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
 * 如果当前字符流没有存在出现一次的字符，返回#字符。
 * Created by Gao on 2018/4/5.
 */
public class FirstNotRepeatChar {

    /**
     * 时间复杂度是O(n)，空间复杂度增加到O(n)
     * HashMap的boolean containsKey(Object key)方法时间复杂度是O(1)，containsValue的复杂度是O(n)
     * 扩展：
     * List集合的contains()方法用于判断集合中包不包含某个元素,返回值是boolean
     * 遍历HashMap中所有元素所需的时间上，containsKey效率高些，containsValue效率很低，是差几个数量级的
     * containsValue()方法需要重写equals()方法和hashcode()方法，而containsKey()方法不需要
     * containsKey()方法中的参数是String类型的值，String不是基本类型，是对象
     */
    private static char firstNotRepeatChar(String str) {

        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (char c : str.toCharArray()) {
            if (map.get(c) == 1) {
                return c;
            }
        }

        return '#';
    }


    private static char firstNotRepeatChar1(String str) {

        int[] hash = new int[128];
        for (char c : str.toCharArray()) {
            if (hash[c] == 0) {
                hash[c] = 1;
            } else {
                hash[c] += 1;
            }
        }

        //这种方法求得是结果是e，e的ASCII是101，l的是108在e的后面
        /*for (int i = 0; i < hash.length; i++) {
            if (hash[i] == 1) {
                return (char) i;
            }
        }*/

        for (char c : str.toCharArray()) {
            if (hash[c] == 1) {
                return c;
            }
        }

        return '#';
    }


    private static char firstNotRepeatChar2(String str) {

        for (int i = 0; i < str.length(); i++) {
            //以字符串google为例，第一次i=0,s2为oole;i=1,s2为ggle,i=2时又是oole这样会有重复
            //replace会调用replaceAll方法，还会新建new StringBuffer对象，底层代码比较复杂，比较耗时
            String s2 = str.replace(String.valueOf(str.charAt(i)), "");
            if (str.length() - s2.length() == 1) {
                return str.charAt(i);
            }
        }

        return '#';
    }


    public static void main(String[] args) {

        String str = "google";

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            firstNotRepeatChar(str);
        }
        long endTime = System.nanoTime();
        System.out.println("firstNotRepeatChar:" + (endTime - startTime) / 1000);  //9093
        char c = firstNotRepeatChar(str);
        System.out.println(c);


        //性能最好，时间最少，拿空间换时间
        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            firstNotRepeatChar1(str);
        }
        long endTime1 = System.nanoTime();
        System.out.println("firstNotRepeatChar1:" + (endTime1 - startTime1) / 1000);  //1460
        char c1 = firstNotRepeatChar1(str);
        System.out.println(c1);


        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            firstNotRepeatChar2(str);
        }
        long endTime2 = System.nanoTime();
        System.out.println("firstNotRepeatChar2:" + (endTime2 - startTime2) / 1000);  //53291
        char c2 = firstNotRepeatChar2(str);
        System.out.println(c2);
    }
}

    /**
     * containsKey()底层Node<K, V>是一个存放有Key和Value的链表节点
     * table是Node<K, V>数组，这里的Node<K, V>则是某一hash值链表的头节点
     * （不同key的hash值可能重复，将会被存放在后续的节点中）。值得注意的是，数组table的长度是2的倍数。
     * 底层是一个基于hash算法实现的，实际上一个好的hash算法是可以让平均时间复杂度为O(1)
     */
    /*public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;

                //key对应的头节点在数组table中的存放位置，也就是下标是`(n - 1) & hash`这个位运算的结果。
                //n是table的长度（必为2的倍数），则n - 1就是table下标的取值范围，用二进制表示是1111...，
                //共log(n)个1。因此`(n - 1) & hash`实际上是取了hash二进制形式的后n位数，正好能对应数组table的下标。
                //数组通过下标访问`Node<K, V>`的时间复杂度是**O(1)**，而`Node<K, V>`访问字段的时间复杂度也是O(1)，
                //如果头节点后没有节点，时间复杂度就是O(1)。
                //头节点后存在节点时，则按下面的代码遍历这些节点，时间复杂度大于O(1)

            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }*/
