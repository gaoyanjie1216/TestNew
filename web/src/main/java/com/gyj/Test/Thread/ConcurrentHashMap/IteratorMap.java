package com.gyj.Test.Thread.ConcurrentHashMap;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 遍历map的四种方式
 * Created by Gao on 2018/4/19.
 */
public class IteratorMap {

    public static Map<String, String> map = new ConcurrentHashMap<String, String>();

    /**
     * 方式一：在for-each循环中使用entries来遍历
     */
    private static void Iterator1() {
        //System.out.println("方式一：在for-each循环中使用entries来遍历");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }


    /**
     * 方法二：在for-each循环中遍历keys或values,这种方式适用于需要值或者键的情况,方法二比方法一快了10%
     */
    private static void Iterator2() {
        //System.out.println("方法二：在for-each循环中遍历keys或values,这种方式适用于需要值或者键的情况");
        for (String key : map.keySet()) {
            //System.out.println("key = " + key);
        }
        for (String value : map.values()) {
            //System.out.println("value = " + value);
        }
    }

    /**
     * 方法三：使用Iterator遍历,使用并发集合不会报异常,性能类似于方法二
     */
    //使用泛型
    private static void Iterator3() {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        //System.out.println("使用Iterator遍历,并且使用泛型:");
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            //注意这里操作了集合,下面的的遍历不会再打印0
            if ("0".equals(entry.getKey())) {
                map.remove(entry.getKey());
            }
        }
    }

    //不使用泛型
    private static void Iterator3_1() {
        Iterator entrys = map.entrySet().iterator();
        //System.out.println("使用Iterator遍历,并且不使用泛型");
        while (entrys.hasNext()) {
            Map.Entry entry = (Map.Entry) entrys.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            //System.out.println("Key = " + key + ", Value = " + value);
        }
    }


    /**
     * 方式四：通过键找值遍历,该方法效率相当低,不建议使用
     */
    private static void Iterator4() {
        //System.out.println("方式四：通过键找值遍历");
        for (String key : map.keySet()) {
            String value = map.get(key);
            //System.out.println("Key = " + key + ", Value = " + value);
        }
    }

    /**
     * 初始化Map
     */
    private static void init() {

        for (Integer i = 0; i < 5; i++) {
            map.put(i.toString(), i.toString());
        }
    }

    public static void main(String[] args) {

        init();

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Iterator1();
        }
        long endTime1 = System.nanoTime();
        System.out.println("Iterator1:" + (endTime1 - startTime1) / 1000);


        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Iterator2();
        }
        long endTime2 = System.nanoTime();
        System.out.println("Iterator2:" + (endTime2 - startTime2) / 1000);


        long startTime3 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Iterator3();
        }
        long endTime3 = System.nanoTime();
        System.out.println("Iterator3:" + (endTime3 - startTime3) / 1000);


        long startTime3_1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Iterator3_1();
        }
        long endTime3_1 = System.nanoTime();
        System.out.println("Iterator3_1:" + (endTime3_1 - startTime3_1) / 1000);


        long startTime4 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            Iterator4();
        }
        long endTime4 = System.nanoTime();
        System.out.println("Iterator4:" + (endTime4 - startTime4) / 1000);

    }
}

/**
 Iterator1:2661
 Iterator2:1529
 Iterator3:922
 Iterator3_1:533
 Iterator4:863
 */
