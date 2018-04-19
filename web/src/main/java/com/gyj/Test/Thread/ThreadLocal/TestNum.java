package com.gyj.Test.Thread.ThreadLocal;

/**
 * Created by Gao on 2018/4/19.
 */
public class TestNum {

    // ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    // ②获取下一个序列值
    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        TestNum sn = new TestNum();
        // ③ 3个线程共享sn，各自产生序列号
        TestClient t1 = new TestClient(sn);
        TestClient t2 = new TestClient(sn);
        TestClient t3 = new TestClient(sn);
        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TestClient extends Thread {
        private TestNum sn;

        public TestClient(TestNum sn) {
            this.sn = sn;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                // ④每个线程打出3个序列值
                System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                        + sn.getNextNum() + "]");
            }
        }
    }
}

/**
 通过ThreadLocal为每一个线程提供了单独的副本
 thread[Thread-0] --> sn[1]
 thread[Thread-0] --> sn[2]
 thread[Thread-0] --> sn[3]
 thread[Thread-1] --> sn[1]
 thread[Thread-1] --> sn[2]
 thread[Thread-1] --> sn[3]
 thread[Thread-2] --> sn[1]
 thread[Thread-2] --> sn[2]
 thread[Thread-2] --> sn[3]

 */