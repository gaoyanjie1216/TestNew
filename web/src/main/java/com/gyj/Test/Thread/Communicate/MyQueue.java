package com.gyj.Test.Thread.Communicate;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用wait/notify模拟Queue
 * Queue是队列，我们需要实现的是阻塞的放入和得到数据，设计思路如下：
 * （1）初始化队列最大长度为5；
 * （2）需要新加入的时候，判断是否长度为5，如果是5则等待插入(最大了已经就不能再插入了)；
 * （3）需要消费元素的时候，判断是否为0，如果是0则等待消费(已经消费完了，不能再消费了)；
 * Created by Gao on 2018/4/18.
 */
public class MyQueue {

    private final LinkedList<Object> list = new LinkedList<>();

    //计数器
    private final AtomicInteger count = new AtomicInteger();

    //上下限
    private final int maxSize = 5;
    private final int minSize = 0;

    private final Object lock = new Object();

    //put方法
    public void put(Object obj) {
        synchronized (lock) {
            //达到最大无法添加，进入等待
            while (count.get() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.add(obj);
            count.getAndIncrement();  //计数器增加
            System.out.println(" 元素 " + obj + " 被添加 ");
            lock.notify();  //通知另一个阻塞的线程方法
        }
    }

    //get方法
    public Object get() {
        Object temp = null;
        //达到最小，没有元素无法消费，进入等待
        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count.getAndDecrement();
            temp = list.removeFirst();
            System.out.println(" 元素 " + temp + " 被消费 ");
            lock.notify();
        }

        return temp;
    }

    private int size() {
        return count.get();
    }

    private static void initMyQueue(MyQueue myQueue) {
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");
        System.out.println("当前元素个数：" + myQueue.size());
    }


    public static void main(String[] args) {

        final MyQueue myQueue = new MyQueue();
        initMyQueue(myQueue);

        Thread thread1 = new Thread(() -> {
            myQueue.put("h");
            myQueue.put("i");
            myQueue.put("j");
        }, "t1");

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);
                    myQueue.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        thread1.start();
        thread2.start();
    }
}


/**
 元素 a 被添加
 元素 b 被添加
 元素 c 被添加
 元素 d 被添加
 元素 e 被添加
 当前元素个数：5
 元素 a 被消费
 元素 h 被添加
 元素 b 被消费
 元素 i 被添加
 元素 c 被消费
 元素 j 被添加
 */
