package com.gyj.Test.Thread;

/**
 * 模拟买票实现线程同步，不同的线程不会同时访问同一张票
 * Created by Gao on 2018/4/11.
 */
public class SellTickets {

    /**
     * 用synchronized 块实现同步
     */
    public static void main(String[] args) {
        // runable对象的属性代表了一共多少张票, runable对象的run方法实现了买票的动作
        Runnable runnable = new Runnable() {
            private int count = 100;

            @Override
            public void run() {
                while (true) {
                    synchronized (this) { //synchronized 的对象如果
                        Thread currentThread = Thread.currentThread();
                        if (count > 0) {
                            System.out.println(currentThread.getName() + " => " + (101 - count) + "张车票");
                            --count;
                        } else {
                            break;
                        }
                    }
                }
            }
        };

        // 开启100个线程模拟100个售票口
        for (int i = 0; i < 100; i++) {
            new Thread(runnable).start();
        }
    }


    /**
     * 用synchronized同步方法实现同步
     */
    public static void main1(String[] args) {
        // runable对象的属性代表了一共多少张票
        // runable对象的run方法实现了买票的动作
        Runnable runnable = new Runnable() {
            private int count = 100;

            @Override
            public void run() {
                while (true) {
                    if (synchronizedMethod()) break;
                }
            }

            public synchronized boolean synchronizedMethod() {
                Thread currentThread = Thread.currentThread();
                if (count > 0) {
                    System.out.println(currentThread.getName() + " => " + (101 - count) + "张车票");
                    --count;
                    return false;
                }
                return true;
            }
        };

        // 开启100个线程模拟100个售票口
        for (int i = 0; i < 100; i++) {
            new Thread(runnable).start();
        }
    }
}
