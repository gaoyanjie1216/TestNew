package com.gyj.Test.Thread.Communicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao on 2018/4/17.
 */
public class NoWaitNotify {

    public static class MyList {

        private List list = new ArrayList();

        public void add() {
            list.add("我是元素");
        }

        public int size() {
            return list.size();
        }
    }

    public static class ThreadA extends Thread {

        private MyList list;

        public ThreadA(MyList list) {
            super();
            this.list = list;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    list.add();
                    System.out.println("添加了" + (i + 1) + "个元素");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThreadB extends Thread {

        private MyList list;

        public ThreadB(MyList list) {
            super();
            this.list = list;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (list.size() == 5) {
                        System.out.println("==5了，线程b要退出了！");
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyList myList = new MyList();

        ThreadA a = new ThreadA(myList);
        a.setName("A");
        a.start();

        ThreadB b = new ThreadB(myList);
        b.setName("B");
        b.start();
    }
}
