package com.gyj.Test.Thread.ThreadLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * 类ThreadLocal主要解决的就是每个线程绑定自己的值，可以将ThreadLocal类比喻成全局存放数据的盒子，盒子中可以存储每个线程的私有变量
 * Created by Gao on 2018/4/17.
 */
public class ThreadLocalDemo {

    public static ThreadLocal<List<String>> threadLocal = new ThreadLocal();

    public void setThreadLocal(List<String> values) {
        threadLocal.set(values);
    }

    public void getThreadLocal() {
        System.out.println(Thread.currentThread().getName());
        threadLocal.get().forEach(name -> System.out.println(name));
    }

    public static void main(String[] args) {

        final ThreadLocalDemo threadLocal = new ThreadLocalDemo();
        new Thread(() -> {
            List<String> params = new ArrayList<>(3);
            params.add("aaa");
            params.add("bbb");
            params.add("ccc");
            threadLocal.setThreadLocal(params);
            threadLocal.getThreadLocal();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                List<String> list = new ArrayList<>(3);
                list.add("ddd");
                list.add("eee");
                threadLocal.setThreadLocal(list);
                threadLocal.getThreadLocal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

/**
 * Thread-0
 * aaa
 * bbb
 * ccc
 * Thread-1
 * ddd
 * eee
 *
 * 虽然多个线程对同一个变量进行访问，但是由于threadLocal变量由ThreadLocal修饰，则不同的线程访问的就是该线程设置的值，这里也就体现出来ThreadLocal的作用。
 * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本
 *
 * ThreadLocal是线程局部变量，是一种多线程间并发访问变量的解决方案。和synchronized等加锁的方式不同，ThreadLocal完全不提供锁，而使用以空间换时间的方式，
 * 为每个线程提供变量的独立副本，以保证线程的安全。
 */