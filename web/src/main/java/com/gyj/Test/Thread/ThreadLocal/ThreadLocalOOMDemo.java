package com.gyj.Test.Thread.ThreadLocal;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal造成OOM内存溢出案例演示与原理分析
 * 理解ThreadLocal内存泄漏的前因后果，那么怎么避免内存泄漏呢？
 答案就是：每次使用完ThreadLocal，都调用它的remove()方法，清除数据
 * Created by Gao on 2018/4/17.
 */
//首先看一下代码，模拟了一个线程数为500的线程池，所有线程共享一个ThreadLocal变量，每一个线程执行的时候插入一个大的List集合：
public class ThreadLocalOOMDemo {

    private static final int THREAD_LOOP_SIZE = 500;
    private static final int MOCK_DIB_DATA_LOOP_SIZE = 10000;

    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);

        for (int i = 0; i < THREAD_LOOP_SIZE; i++) {
            executorService.execute(() -> {
                threadLocal.set(new ThreadLocalOOMDemo().addBigList());

                System.out.println(Thread.currentThread().getName());

                //不加的话可能会出现OOM, 记住是在当前线程内部调用,在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄漏的问题，
                //更严重的是可能导致业务逻辑出现问题。所以，使用ThreadLocal就跟加锁完要解锁一样，用完就清理
                threadLocal.remove();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    private List<User> addBigList() {
        List<User> params = new ArrayList<>(MOCK_DIB_DATA_LOOP_SIZE);
        for (int i = 0; i < MOCK_DIB_DATA_LOOP_SIZE; i++) {
            params.add(new User("xuliugen", "password" + i, "男", i));
        }
        return params;
    }

    class User {
        private String userName;
        private String password;
        private String sex;
        private int age;

        public User(String userName, String password, String sex, int age) {
            this.userName = userName;
            this.password = password;
            this.sex = sex;
            this.age = age;
        }
    }
}

/**
 * 一个Thread中只有一个ThreadLocalMap，一个ThreadLocalMap中可以有多个ThreadLocal对象，其中一个ThreadLocal对象对应一个ThreadLocalMap中一个的Entry
 * （也就是说：一个Thread可以依附有多个ThreadLocal对象）
 * ThreadLocal的实现是这样的：每个Thread 维护一个 ThreadLocalMap 映射表，这个映射表的 key 是 ThreadLocal实例本身，value 是真正需要存储的 Object
 * <p>
 * 在ThreadLocal的get(),set(),remove()的时候都会清除线程ThreadLocalMap里所有key为null的value
 * <p>
 * （1）使用static的ThreadLocal，延长了ThreadLocal的生命周期，可能导致内存泄漏。
 * （2）分配使用了ThreadLocal又不再调用get(),set(),remove()方法，那么就会导致内存泄漏，因为这块内存一直存在。
 */
