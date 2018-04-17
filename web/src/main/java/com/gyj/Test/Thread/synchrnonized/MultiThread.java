package com.gyj.Test.Thread.synchrnonized;

/**
 * Created by Gao on 2018/4/17.
 */
public class MultiThread {

    private static int num = 200;

    private static synchronized void printNum(String threadName, String tag) {
        if (tag.equals("a")) {
            num = num - 100;
            System.out.println(threadName + " tag a,set num over!");
        } else {
            num = num - 200;
            System.out.println(threadName + " tag b,set num over!");
        }
        System.out.println(threadName + " tag " + tag + ", num = " + num);
    }

    public static void main(String[] args) {
        final MultiThread multiThread1 = new MultiThread();
        final MultiThread multiThread2 = new MultiThread();

        new Thread(new Runnable() {
            @Override
            public void run() {
                multiThread1.printNum("thread1", "a");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                multiThread2.printNum("thread2", "b");
            }
        }).start();
    }
}

/**
 * 有两个对象：multiThread1和multiThread2，如果多个对象使用同一把锁的话，
 * 那么上述执行的结果就应该是：thread2 tag b, num = -100，因此，是每一个对象拥有该对象的锁的
 * 对于非静态static修饰的方法或变量，是一个对象一把锁的
 * thread1 tag a,set num over!
 * thread1 tag a, num = 100
 * thread2 tag b,set num over!
 * thread2 tag b, num = 0
 */

/**
 * 在变量和方法上都加上synchronized关键字后的结果，多个线程共享变量和方法
 * 同步的概念就是共享，我们要知道“共享”这两个字，如果不是共享的资源，就没有必要进行同步，也就是没有必要进行加锁；
 * 同步的目的就是为了线程的安全，其实对于线程的安全，需要满足两个最基本的特性：原子性和可见性;
 * thread1 tag a,set num over!
 * thread1 tag a, num = 100
 * thread2 tag b,set num over!
 * thread2 tag b, num = -100
 */