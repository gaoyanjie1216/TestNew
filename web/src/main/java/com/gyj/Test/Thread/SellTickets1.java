package com.gyj.Test.Thread;

/**
 * 模拟3个窗口卖票，不同的窗口不会卖同一张票
 * Created by Gao on 2018/4/11.
 */
public class SellTickets1 extends Thread {

    //票数 这里 必须定义为static。不然非静态的成员变量,非静态的成员变量数据是在每个对象中都会维护一份数据的。三个线程对象就会有三份。
    private static int tickets = 1;

    public SellTickets1(String threadName) {
        super(threadName);
    }

    public void run() {
        while (true) {
            synchronized ("锁") {
                if (tickets == 101) {
                    System.out.println("票已经卖完啦-_-...");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "卖了第" + tickets + "号票");
                tickets++;
            }
            //System.out.println(Thread.currentThread().getName() + "锁后...");
        }
    }

    public static void main(String[] args) {

        //创建三个线程对象,模拟三个窗口
        SellTickets1 s1 = new SellTickets1("窗口1");
        SellTickets1 s2 = new SellTickets1("窗口2");
        SellTickets1 s3 = new SellTickets1("窗口3");

        //不同的系统有不同的线程优先级的取值范围，但是Java定义了10个级别（1-10）。这样就有可能出现几个线程在一个操作系统里有不同的优先级，
        // 在另外一个操作系统里却有相同的优先级（并因此可能有意想不到的行为),优先级高表示竞争力大一些
        s1.setPriority(10);
        //s2.setPriority(10);

        //开启线程售票
        s1.start();
        s2.start();
        s3.start();

        System.out.println("main方法...");
    }

}
