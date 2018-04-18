package com.gyj.Test.Thread.Communicate;


import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的另一个称呼就是“重入锁”，Reentrant的英文释义为：重入。
 * 何为重入锁，前几篇在学习synchronized的时候，也谈到了重入锁，“一个对象一把锁，多个对象多把锁”，可重入锁的概念就是：自己可以获取自己的内部锁。
 * ReentrantLock实现了Lock中的接口
 * 在实现线程安全的控制中，通常用ReentrantLock，使用该Lock对象可以显式的加锁、释放锁
 * Created by Gao on 2018/4/18.
 */
public class Lock {

    /**
     * lock1()
     */
    private static void lock1() {

        // 定义锁对象
        final ReentrantLock lock = new ReentrantLock();

        //lambda写法
        new Thread(() -> runMethod1(lock), "thread1").start();
        new Thread(() -> runMethod1(lock), "thread2").start();

        //常规写法
        new Thread(new Runnable() {
            @Override
            public void run() {
                runMethod1(lock);
            }
        }, "thread3").start();
    }

    private static void runMethod1(ReentrantLock lock) {
        lock.lock();
        for (int i = 1; i <= 3; i++) {
            System.out.println("ThreadName:" + Thread.currentThread().getName() + (" i=" + i));
        }
        System.out.println();
        lock.unlock();
    }


    /**
     * lock2()
     */
    private static void runMethod2(ReentrantLock lock, long sleepTime) {
        lock.lock();
        try {
            Thread.sleep(sleepTime);
            System.out.println("ThreadName:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void lock2() {
        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> runMethod2(lock, 0), "thread1").start();
        new Thread(() -> runMethod2(lock, 3000), "thread2").start();
        new Thread(() -> runMethod2(lock, 1000), "thread3").start();
    }


    public static void main(String[] args) {
        //lock1();
        lock2();
    }
}

/**
 * lock1()的执行结果
 * ThreadName:thread2 i=1
 * ThreadName:thread2 i=2
 * ThreadName:thread2 i=3
 * <p>
 * ThreadName:thread3 i=1
 * ThreadName:thread3 i=2
 * ThreadName:thread3 i=3
 * <p>
 * ThreadName:thread1 i=1
 * ThreadName:thread1 i=2
 * ThreadName:thread1 i=3
 * <p>
 * 再次执行可能的打印顺序不一致
 * ThreadName:thread1 i=1
 * ThreadName:thread1 i=2
 * ThreadName:thread1 i=3
 * <p>
 * ThreadName:thread2 i=1
 * ThreadName:thread2 i=2
 * ThreadName:thread2 i=3
 * <p>
 * ThreadName:thread3 i=1
 * ThreadName:thread3 i=2
 * ThreadName:thread3 i=3
 * 当前线程打印完毕之后释放锁，其他线程才可以获取锁然后进行打印。线程打印的数据是分组打印的，这是因为当前线程已经持有锁，
 * 在当前线程打印完之后才会释放锁，但线程之间打印的顺序是随机的
 * <p>
 * run2()的执行结果
 * ThreadName:thread1
 * ThreadName:thread2
 * ThreadName:thread3
 * 在sleep指定的时间内，当调用了lock.lock()方法线程就持有了”对象监视器”，其他线程只能等待锁被释放后再次争抢，效果和使用synchronized关键字是一样的
 * <p>
 * run2()的执行结果
 * ThreadName:thread1
 * ThreadName:thread2
 * ThreadName:thread3
 * 在sleep指定的时间内，当调用了lock.lock()方法线程就持有了”对象监视器”，其他线程只能等待锁被释放后再次争抢，效果和使用synchronized关键字是一样的
 */

/**
 run2()的执行结果
 ThreadName:thread1
 ThreadName:thread2
 ThreadName:thread3
 在sleep指定的时间内，当调用了lock.lock()方法线程就持有了”对象监视器”，其他线程只能等待锁被释放后再次争抢，效果和使用synchronized关键字是一样的
 */