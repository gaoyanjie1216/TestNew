package com.gyj.Test.Thread.synchrnonized;

/**
 * Synchronized锁重入
 * （1）关键字Synchronized拥有锁重入的功能，也就是在使用Synchronized的时候，当一个线程得到一个对象的锁后，在该锁里执行代码的时候可以再次请求
 * 该对象的锁时可以再次得到该对象的锁。
 * （2）当线程请求一个由其它线程持有的对象锁时，该线程会阻塞，而当线程请求由自己持有的对象锁时，如果该锁是重入锁,请求就会成功，否则阻塞。
 * （3）一个简单的例子就是：在一个Synchronized修饰的方法或代码块的内部调用本类的其他Synchronized修饰的方法或代码块时，是永远可以得到锁的
 * Created by Gao on 2018/4/17.
 */
public class SyncDubbo {

    public synchronized void method1() {
        System.out.println("method1-----");
        method2();
    }

    public synchronized void method2() {
        System.out.println("method2-----");
        method3();
    }

    public synchronized void method3() {
        System.out.println("method3-----");
    }

    public static void main(String[] args) {
        final SyncDubbo syncDubbo = new SyncDubbo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncDubbo.method1();
            }
        }).start();
    }
}


/**
 * “一个对象一把锁，多个对象多把锁”，可重入锁的概念就是：自己可以获取自己的内部锁。
 * method1-----
 * method2-----
 * method3-----
 * 假如有1个线程T获得了对象A的锁，那么该线程T如果在未释放前再次请求该对象的锁时，如果没有可重入锁的机制，是不会获取到锁的，这样的话就会出现死锁的情况。
 * 就如代码A体现的那样，线程T在执行到method1（）内部的时候，由于该线程已经获取了该对象syncDubbo 的对象锁，当执行到调用method2（） 的时候，
 * 会再次请求该对象的对象锁，如果没有可重入锁机制的话，由于该线程T还未释放在刚进入method1（） 时获取的对象锁，当执行到调用method2（） 的时候，就会出现死锁。
 */