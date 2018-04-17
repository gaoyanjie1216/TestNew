package com.gyj.Test.Thread.synchrnonized;

/**
 * 将任意对象作为监视器
 * Created by Gao on 2018/4/17.
 */
public class StringLock {

    private String lock = "lock";

    private void method() {
        synchronized (lock) {
            try {
                System.out.println("当前线程： " + Thread.currentThread().getName() + "开始");
                Thread.sleep(100);
                System.out.println("当前线程： " + Thread.currentThread().getName() + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final StringLock stringLock = new StringLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        }, "t2").start();
    }
}

/**
 * 当前线程： t1开始
 * 当前线程： t1结束
 * 当前线程： t2开始
 * 当前线程： t2结束
 */
