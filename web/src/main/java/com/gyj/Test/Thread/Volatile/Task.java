package com.gyj.Test.Thread.Volatile;

import java.util.concurrent.locks.ReentrantLock;

/**
 * java内存分为工作内存和主存
 * 工作内存：即java线程的本地内存，是单独给某个线程分配的，存储局部变量等，同时也会复制主存的共享变量作为本地
 * 的副本，目的是为了减少和主存通信的频率，提高效率。
 * 主存：存储类成员变量等
 * <p>
 *
 * 原子性
 * 在Java中，对基本数据类型的变量的读取和赋值操作是原子性操作，即这些操作是不可被中断的，要么执行，要么不执行。
 * 只有简单的读取、赋值（而且必须是将数字赋值给某个变量，变量之间的相互赋值不是原子操作）才是原子操作。
 *
 * 可见性是指的是线程访问变量是否是最新值。当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值
 * 局部变量不存在可见性问题，而共享内存就会有可见性问题，volatile关键字只能保证共享变量的可见性,但不能保证对变量的操作的原子性。
 * 因为本地线程在创建的时候，会从主存中读取一个共享变量的副本，且修改也是修改副本，
 * 且并不是立即刷新到主存中去，那么其他线程并不会马上共享变量的修改。
 * <p>
 * 解决共享变量可见性问题，需要用volatile关键字修饰。
 * 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值。volatile很容易被误用，用来进行原子性操作。
 * Created by Gao on 2018/4/18.
 */
public class Task {

    /**
     * 一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
     1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
     2）禁止进行指令重排序。
     */
    //public static int count = 0;
    public volatile static int count = 0;

    public synchronized static void increate() {

        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    private static void task1() {

        //同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Task.increate();
                }
            }).start();
        }

        //这里每次运行的值都有可能不同,可能为1000
        //Task.count=508, Task.count=862,Task.count=779每次运行的结果可能都不一样
        System.out.println("运行结果:Task.count=" + Task.count);
    }

    /**
     * 分析public volatile static int count = 0; 加上volatile关键字之后还是运行结果:Task.count=260，运行结果还是不一样也不是1000
     *
     * 但是要注意，线程1对变量进行读取操作之后，被阻塞了的话，并没有对count值进行修改。然后虽然volatile能保证线程2对变量count的值读取是从内存中读取的，
     * 但是线程1没有进行修改，所以线程2根本就不会看到修改的值
     *
     * volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性
     * 线程1对变量进行读取操作之后，被阻塞了的话，并没有对inc值进行修改。然后虽然volatile能保证线程2对变量inc的值读取是从内存中读取的，
     * 但是线程1没有进行修改，所以线程2根本就不会看到修改的值
     * 根源就在于自增操作不是原子性操作，而且volatile也无法保证对变量的任何操作都是原子性的。
     *
     */
    public static void main(String[] args) {
        task1();
    }
}



