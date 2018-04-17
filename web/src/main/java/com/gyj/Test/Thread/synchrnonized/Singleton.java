package com.gyj.Test.Thread.synchrnonized;

/**
 * 使用上述的方式可以实现多线程的情况下获取到正确的实例对象，但是每次访问newInstance（）方法都会进行加锁和解锁操作，也就是说该锁可能会成为系统的瓶颈
 * 普通的加锁的单例模式：
 * Created by Gao on 2018/4/17.
 */
public class Singleton {

    //懒汉模式
    private static Singleton instance = null;

    //饿汉模式
    //private static Singleton instance = new Singleton();

    private Singleton() {

    }

    public static synchronized Singleton newInstance() {
        if (null == instance) {
            instance = new Singleton();
        }

        return instance;
    }
}
