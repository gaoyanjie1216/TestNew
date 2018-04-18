package com.gyj.Test.Thread.Communicate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以使用关键字synchronized与wait()方法和notify()方式结合实现线程间通信，也就是等待/通知模式。在ReentrantLock中，是借助Condition对象进行实现的
 * 在使用关键字synchronized与wait()方法和notify()方式结合实现线程间通信的时候，notify/notifyAll的通知等待的线程时是随机的，显然使用Condition相对灵活很多,
 * 可以实现”选择性通知”。
 * （1）Object的wait()方法相当于Condition类中的await()方法；
 * （2）Object的notify()方法相当于Condition类中的signal()方法；
 * （3）Object的notifyAll()方法相当于Condition类中的signalAll()方法；
 * Created by Gao on 2018/4/18.
 */
public class LockCondition {

    /**
     * 使用Lock对象和Condition实现等待/通知实例
     */
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private static void oneCondition() throws InterruptedException {

        LockCondition lockCondition = new LockCondition();
        //new Thread(() -> lockCondition.await(), "thread1").start();
        new Thread(lockCondition::await, "thread1").start();
        Thread.sleep(1000);
        new Thread(lockCondition::signal, "thread2").start();
    }

    private void await() {
        try {
            lock.lock();
            System.out.println("等待await！ThreadName：" + Thread.currentThread().getName());
            condition.await();
            System.out.println("等待await结束！ThreadName：" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal() {
        lock.lock();
        System.out.println("发送通知signal! ThreadName:" + Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }


    /**
     * 使用Lock对象和多个Condition实现等待/通知实例
     */
    private ReentrantLock reentrantLock1 = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();


    private void await(Condition condition) {
        try {
            lock.lock();
            System.out.println("开始等待await！ThreadName：" + Thread.currentThread().getName());
            condition.await();
            System.out.println("等待await结束！ThreadName：" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal(Condition condition) {
        lock.lock();
        System.out.println("发送通知signal！ThreadName：" + Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }

   private static void MoreConditions() throws InterruptedException {

       LockCondition demo = new LockCondition();
       new Thread(() -> demo.await(demo.conditionA), "thread1_conditionA").start();
       new Thread(() -> demo.await(demo.conditionB), "thread2_conditionB").start();
       new Thread(() -> demo.signal(demo.conditionA), "thread3_conditionA").start();
       Thread.sleep(1000);
       new Thread(() -> demo.signal(demo.conditionB), "thread4_conditionB").start();
   }

    public static void main(String[] args) throws InterruptedException {
        //oneCondition();

        MoreConditions();
    }
}

/**
 * oneCondition()方法执行结果
 开始等待await！ ThreadName：thread1
 发送通知signal! ThreadName:thread2
 等待await结束！ ThreadName：thread1

 MoreConditions()方法执行结果
 开始等待await！ThreadName：thread1_conditionA
 开始等待await！ThreadName：thread2_conditionB
 发送通知signal！ThreadName：thread3_conditionA
 等待await结束！ThreadName：thread1_conditionA
 发送通知signal！ThreadName：thread4_conditionB
 等待await结束！ThreadName：thread2_conditionB
 实现了分别通知。可以使用Condition进行分组，可以单独的通知某一个分组，还可以使用signalAll()方法实现通知某一个分组的所有等待的线程
 */
