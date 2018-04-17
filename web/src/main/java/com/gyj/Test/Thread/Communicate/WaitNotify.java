package com.gyj.Test.Thread.Communicate;

import java.util.ArrayList;
import java.util.List;

/**
 * （1）wait()和notify()方法要在同步块或同步方法中调用，即在调用前，线程也必须获得该对象的对象级别锁。
 * （2）wait方法是释放锁，notify方法是不释放锁的；
 * （3）notify每次唤醒wait等待状态的线程都是随机的，且每次只唤醒一个；
 * （4）notifAll每次唤醒wait等待状态的线程使之重新竞争获取对象锁，优先级最高的那个线程会最先执行；
 * （5）当线程处于wait()状态时，调用线程对象的interrupt()方法会出现InterruptedException异常；
 * Created by Gao on 2018/4/17.
 */
public class WaitNotify {

    public static void main(String[] args) {
        try {
            Object lock = new Object();
            ThreadA a = new ThreadA(lock);
            a.start();
            Thread.sleep(50);
            ThreadB b = new ThreadB(lock);
            b.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyList {

    private static List<String> list = new ArrayList();

    public static void add() {
        list.add("我是元素");
    }

    public static int size() {
        return list.size();
    }
}

class ThreadA extends Thread {

    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                if (MyList.size() != 5) {
                    System.out.println("wait begin " + System.currentTimeMillis());
                    lock.wait();
                    System.out.println("wait end  " + System.currentTimeMillis());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadB extends Thread {

    private final Object lock;

    public ThreadB(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    MyList.add();
                    if (MyList.size() == 5) {
                        lock.notify();
                        System.out.println("已发出通知！");
                    }
                    System.out.println("添加了" + (i + 1) + "个元素!");
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 虽然线程B在第五个元素的时候发出通知，而线程A实现线程B执行完之后才获得对象锁，这也可以说明，wait方法是释放锁的而notify方法是不释放锁的
 * wait begin 1523968658902
 添加了1个元素!
 添加了2个元素!
 添加了3个元素!
 添加了4个元素!
 已发出通知！
 添加了5个元素!
 添加了6个元素!
 添加了7个元素!
 添加了8个元素!
 添加了9个元素!
 添加了10个元素!
 wait end  1523968663956
 */