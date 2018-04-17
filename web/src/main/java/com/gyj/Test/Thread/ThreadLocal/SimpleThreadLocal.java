package com.gyj.Test.Thread.ThreadLocal;

import java.util.*;

/**
 * ThreadLocal很容易让人望文生义，想当然地认为是一个“本地线程”。其实，ThreadLocal并不是一个Thread，而是Thread的一个局部变量，
 * 也许把它命名为ThreadLocalVariable更容易让人理解一些。
 * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，
 * 而不会影响其它线程所对应的副本。
 * <p>
 * ThreadLocal 的作用是提供线程内的局部变量，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度。
 * Created by Gao on 2018/4/17.
 */

//ThreadLocal公有的方法就四个，分别为：get、set、remove、intiValue
//在ThreadLocal类中有一个static声明的Map，用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值对应线程的变量副本
public class SimpleThreadLocal<T> {

    //Key为线程对象，Value为传入的值对象
    private  Map<Thread, T> map = Collections.synchronizedMap(new HashMap<Thread, T>());

    //设值value Map键值对的value
    public void set(T value) {
        map.put(Thread.currentThread(), value);
    }

    //移除
    public void remove() {
        map.remove(Thread.currentThread());
    }

    //初始化
    public T initialValue() {
        return null;
    }

    //取值
    public T get() {
        Thread currentThread = Thread.currentThread();
        //返回当前线程对应的变量
        T t = map.get(currentThread);
        //如果当前线程在Map中不存在，则将当前线程存储到Map中
        if (t == null && !map.containsKey(currentThread)) {
            t = initialValue();
            map.put(currentThread, t);
        }
        return t;
    }


    public static void main(String[] args) {

        SimpleThreadLocal<List<String>> threadLocal = new SimpleThreadLocal<>();

        new Thread(() -> {
            List<String> params = new ArrayList<>(3);
            params.add("张三");
            params.add("李四");
            params.add("王五");
            threadLocal.set(params);
            System.out.println(Thread.currentThread().getName());
            threadLocal.get().forEach(param -> System.out.println(param));
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                List<String> params = new ArrayList<>(2);
                params.add("Chinese");
                params.add("English");
                threadLocal.set(params);
                System.out.println(Thread.currentThread().getName());
                threadLocal.get().forEach(param -> System.out.println(param));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

/**
 * 上面的代码清单中的这个ThreadLocal实现版本显得比较简单粗，但其目的主要在与呈现JDK中所提供的ThreadLocal类在实现上的思路
 * Thread-0
 * 张三
 * 李四
 * 王五
 * Thread-1
 * Chinese
 * English
 */

/**
 * 总结
 通过源代码可以看到每个线程都可以独立修改属于自己的副本而不会互相影响，从而隔离了线程和线程.避免了线程访问实例变量发生安全问题. 同时我们也能得出下面的结论：
 （1）ThreadLocal只是操作Thread中的ThreadLocalMap对象的集合；
 （2）ThreadLocalMap变量属于线程的内部属性，不同的线程拥有完全不同的ThreadLocalMap变量；
 （3）线程中的ThreadLocalMap变量的值是在ThreadLocal对象进行set或者get操作时创建的；
 （4）使用当前线程的ThreadLocalMap的关键在于使用当前的ThreadLocal的实例作为key来存储value值；
 （5） ThreadLocal模式至少从两个方面完成了数据访问隔离，即纵向隔离(线程与线程之间的ThreadLocalMap不同)和横向隔离(不同的ThreadLocal实例之间的互相隔离)；
 （6）一个线程中的所有的局部变量其实存储在该线程自己的同一个map属性中；
 （7）线程死亡时，线程局部变量会自动回收内存；
 （8）线程局部变量时通过一个 Entry 保存在map中，该Entry 的key是一个 WeakReference包装的ThreadLocal, value为线程局部变量，key 到 value 的映射是通过：
 ThreadLocal.threadLocalHashCode & (INITIAL_CAPACITY - 1) 来完成的；
 （9）当线程拥有的局部变量超过了容量的2/3(没有扩大容量时是10个)，会涉及到ThreadLocalMap中Entry的回收；
 对于多线程资源共享的问题，同步机制采用了“以时间换空间”的方式，而ThreadLocal采用了“以空间换时间”的方式。前者仅提供一份变量，让不同的线程排队访问，
 而后者为每一个线程都提供了一份变量，因此可以同时访问而互不影响。
 */