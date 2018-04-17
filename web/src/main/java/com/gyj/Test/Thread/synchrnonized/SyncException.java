package com.gyj.Test.Thread.synchrnonized;

/**
 * SyncException 出现异常时，锁自动释放
 * 就是说，当一个线程执行的代码出现异常的时候，其所持有的锁会自动释放
 * Created by Gao on 2018/4/17.
 */
public class SyncException {

    private int i = 0;

    public synchronized void operation() {
        while (true) {
            i++;
            System.out.println(Thread.currentThread().getName() + " i= " + i);
            if (i == 10) {
                Integer.parseInt("a");
            }
        }
    }

    public static void main(String[] args) {
        final SyncException se = new SyncException();
        new Thread(new Runnable() {
            @Override
            public void run() {
                se.operation();
            }
        }, "t1").start();
    }
}

/**
 * t1 i= 1
 t1 i= 2
 t1 i= 3
 t1 i= 4
 t1 i= 5
 t1 i= 6
 t1 i= 7
 t1 i= 8
 t1 i= 9
 t1 i= 10
 * Exception in thread "t1" java.lang.NumberFormatException: For input string: "a"
 */