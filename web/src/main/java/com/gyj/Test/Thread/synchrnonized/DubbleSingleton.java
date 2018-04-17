package com.gyj.Test.Thread.synchrnonized;

/**
 * 单例模式-双重校验锁：
 * Created by Gao on 2018/4/17.
 */
public class DubbleSingleton {

    //private static DubbleSingleton instance;
    private static volatile DubbleSingleton instance;

    public static DubbleSingleton getInstance() {
        //判断实例是否已经被其他线程创建了，如果没有则创建
        if (instance == null) {
            try {
                //模拟初始化对象的准备时间...
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //类上加锁，表示当前对象不可以在其他线程的时候创建
            synchronized (DubbleSingleton.class) {
                //如果不加这一层判断的话，这样的话每一个线程会得到一个实例,而不是所有的线程的到的是一个实例
                if (instance == null) {  //从第一次判断是否为null到加锁之间的时间内判断实例是否已经被创建
                    instance = new DubbleSingleton();
                }
            }
        }

        return instance;
    }
}


/**
 * (双重校验锁的方式相对于线程安全的懒汉模式来说，从表面上是将锁的粒度缩小为方法内部的同步代码块，而不是线程安全的懒汉模式同步整个方法！
 * 是锁优化中：减小锁粒度的一种表现形式),需要注意的是，上述的代码是错误的写法，这是因为：指令重排优化，可能会导致初始化单例对象和将该对象地址赋值给
 * instance字段的顺序与上面Java代码中书写的顺序不同。
 * 例如：线程A在创建单例对象时，在构造方法被调用之前，就为 该对象分配了内存空间并将对象设置为默认值。此时线程A就可以将分配的内存地址赋值给instance字段了，
 * 然而该对象可能还没有完成初始化操作。线程B来调用newInstance()方法，得到的 就是未初始化完全的单例对象，这就会导致系统出现异常行为。
 * <p>
 * 为了解决上述的问题，可以使用volatile关键字进行修饰instance字段。volatile关键字在这里的含义就是禁止指令的重排序优化（另一个作用是提供内存可见性），
 * 从而保证instance字段被初始化时，单例对象已经被完全初始化。
 *
 *
 * private static volatile DubbleSingleton instance;
 *
 * 指令重排序是JVM为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，尽可能地提高并行度（比如：将多条指定并行执行或者是调整指令的执行顺序）。
 * 编译器、处理器也遵循这样一个目标。注意是单线程。可显而知，多线程的情况下指令重排序就会给程序员带来问题。最重要的一个问题就是程序执行的顺序可能会被调整，
 * 另一个问题是对修改的属性无法及时的通知其他线程，已达到所有线程操作该属性的可见性。
 */