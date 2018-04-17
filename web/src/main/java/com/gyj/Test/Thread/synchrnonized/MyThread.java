package com.gyj.Test.Thread.synchrnonized;

/**
 * Created by Gao on 2018/4/17.
 */
public class MyThread extends Thread {

    private int count = 5;

    /**
     * 不使用synchronized，多个线程同时操作run（）方法，对count进行修改，进而造成错误
     * t2 count 3
     t1 count 3
     t3 count 2
     t5 count 1
     t4 count 0
     */
   /* @Override
    public void run() {
        count--;
        //sout回车快捷键
        System.out.println(this.currentThread().getName() + " count " + count);
    }*/

    /**
     * 使用synchronized关键字，同步方法
     * 当多个线程访问MyThread 的run方法的时候，如果使用了synchronized修饰，那个多线程就会以排队的方式进行处理（这里排队是按照CPU分配的先后顺序而定的）
     * 一个线程想要执行synchronized修饰的方法里的代码，首先是尝试获得锁，如果拿到锁，执行synchronized代码体的内容，如果拿不到锁的话，
     * 这个线程就会不断的尝试获得这把锁，直到拿到为止，而且多个线程同时去竞争这把锁，也就是会出现锁竞争的问题
     * t1 count 4
     t2 count 3
     t3 count 2
     t5 count 1
     t4 count 0
     */
    @Override
    public synchronized void run() {
        count--;
        //sout回车快捷键
        System.out.println(this.currentThread().getName() + " count " + count);
    }

    //输入psvm回车快捷键
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        //myThread.start();  输出Thread-0 count4单线程就执行一次
        Thread thread1 = new Thread(myThread, "t1");
        Thread thread2 = new Thread(myThread, "t2");
        Thread thread3 = new Thread(myThread, "t3");
        Thread thread4 = new Thread(myThread, "t4");
        Thread thread5 = new Thread(myThread, "t5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}


