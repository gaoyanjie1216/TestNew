package com.gyj.Test.Thread.ConcurrentHashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao on 2018/4/19.
 */
public class Test {

    private static Map<Integer, String> map = new HashMap<>();
    private static Map<Integer, String> map1 = new ConcurrentHashMap<>();

    private static void init() {
        for (Integer i = 0; i < 10; i++) {
            map.put(i, i.toString());
        }
    }

    private static void print() {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private static void init1() {
        for (Integer i = 0; i < 10; i++) {
            map1.put(i, i.toString());
        }
    }

    private static void print1() {
        for (Map.Entry<Integer, String> entry : map1.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * HashMap或者ArrayList边遍历边删除数据会报java.util.ConcurrentModificationException异常
     */
    private static void HashMapRemove() {
        init();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int key = entry.getKey();
            if (key < 10) {
                map.remove(key);
            }
        }

        print();
    }

    /**
     * 要用迭代器删除元素
     */
    private static void IteratorRemove() {
        init();

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            int key = entry.getKey();
            if (key < 5) {
                iterator.remove();
            }
        }

        print();
    }


    /**
     * 对ConcurrentHashMap边遍历边删除或者增加操作不会产生异常(可以不用迭代方式删除元素)，因为其内部已经做了维护，遍历的时候都能获得最新的值。
     * 即便是多个线程一起删除、添加元素也没问题
     */
    private static void concurrentRemove() {
        init1();

        for (Map.Entry<Integer, String> entry : map1.entrySet()) {
            int key = entry.getKey();
            if (key < 6) {
                map1.remove(key);
            }
        }

        print1();
    }


    //一个线程对ConcurrentHashMap增加数据，另外一个线程在遍历时就能获得
    private static void threadConcurrent() {
        init1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                map1.put(100, "100");
                map1.put(101, "101");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, String> entry : map1.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }, "thread2").start();

        print1();
    }


    public static void main(String[] args) {
        //HashMapRemove();
        //IteratorRemove();
        //concurrentRemove();
        threadConcurrent();

    }
}

/**
 threadConcurrent();
 0 0
 1 1
 2 2
 3 3
 4 4
 5 5
 6 6
 7 7
 8 8
 9 9
 0 : 0
 1 : 1
 2 : 2
 3 : 3
 4 : 4
 100 : 100
 5 : 5
 101 : 101
 6 : 6
 7 : 7
 8 : 8
 9 : 9
 */
