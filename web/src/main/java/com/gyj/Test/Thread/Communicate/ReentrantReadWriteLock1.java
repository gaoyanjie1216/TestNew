package com.gyj.Test.Thread.Communicate;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 集合中有同步类容器和并发类容器，HashTable（HashTable几乎可以等价于HashMap，并且是线程安全的）也是完全排他的，即使是读也只能同步执行，
 * 而ConcurrentHashMap就可以实现同一时刻多个线程之间并发。为了提高效率，ReentrantLock的升级版ReentrantReadWriteLock就可以实现效率的提升。
 * ReentrantReadWriteLock有两个锁：一个是与读相关的锁，称为“共享锁”；另一个是与写相关的锁，称为“排它锁”。也就是多个读锁之间不互斥，读锁与写锁互斥，
 * 写锁与写锁互斥。
 * 在没有线程进行写操作时，进行读操作的多个线程都可以获取到读锁，而写操作的线程只有获取写锁后才能进行写入操作。即：多个线程可以同时进行读操作，
 * 但是同一时刻只允许一个线程进行写操作。
 * <p>
 * ReentrantReadWriteLock锁的特性：
 * （1）读读共享；
 * （2）写写互斥；
 * （3）读写互斥；
 * （4）写读互斥；
 * Created by Gao on 2018/4/18.
 */
public class ReentrantReadWriteLock1 {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private void read() {
        try {
            try {
                lock.readLock().lock();
                System.out.println("获得读锁" + Thread.currentThread().getName()
                        + " 时间:" + System.currentTimeMillis());
                //模拟读操作时间为5秒
                Thread.sleep(5000);
            } finally {
                lock.readLock().unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        try {
            try {
                lock.writeLock().lock();
                System.out.println("获得写锁" + Thread.currentThread().getName()
                        + " 时间:" + System.currentTimeMillis());
                //模拟写操作时间为5秒
                Thread.sleep(5000);
            } finally {
                lock.writeLock().unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读读共享
     */
    private static void readreadShare() {
        ReentrantReadWriteLock1 demo = new ReentrantReadWriteLock1();

        new Thread(demo::read, "ThreadA").start();
        new Thread(demo::read, "ThreadB").start();
    }

    /**
     * 写写互斥
     */
    private static void writewriteMutex() {
        ReentrantReadWriteLock1 demo1 = new ReentrantReadWriteLock1();

        new Thread(demo1::write, "ThreadA").start();
        new Thread(demo1::write, "ThreadB").start();
    }

    /**
     * 读写互斥或写读互斥
     */
    private static void readwriteMutex() {
        ReentrantReadWriteLock1 demo2 = new ReentrantReadWriteLock1();

        new Thread(demo2::read, "ThreadA").start();
        new Thread(demo2::write, "ThreadB").start();
    }


    public static void main(String[] args) {

        /*readreadShare();
        writewriteMutex();*/
        readwriteMutex();
    }
}

/**
 * readreadShare()
 * 获得读锁ThreadA 时间:1524042419585
 * 获得读锁ThreadB 时间:1524042419585
 * 两个线程之间，获取锁的时间几乎同时，说明lock.readLock().lock(); 允许多个线程同时执行lock（）方法后面的代码。
 * <p>
 * writewriteMutex()
 * 获得写锁ThreadA 时间:1524042829830
 * 获得写锁ThreadB 时间:1524042834832
 * 可以看出执行结果大致差了5秒的时间，可以说明多个写线程是互斥的
 * <p>
 * readwriteMutex()
 * 获得读锁ThreadA 时间:1524044135800
 * 获得写锁ThreadB 时间:1524044140800
 * 执行结果大致差了5秒的时间，可以说明读写线程是互斥的
 */