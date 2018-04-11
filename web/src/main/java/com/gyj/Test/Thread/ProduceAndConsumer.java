package com.gyj.Test.Thread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Gao on 2018/4/11.
 * 生产者、消费者问题有很多的实现方法：
 * - 用wait() / notify()方法
 * - 用Lock的多Condition方法
 * - BlockingQueue阻塞队列方法
 */
/**
 * 用阻塞队列快速实现生产者-消费者
 */
public class ProduceAndConsumer {

    static class Produce extends Thread {
        private final BlockingQueue<Integer> list;

        Produce(BlockingQueue<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer take = list.take();
                    System.out.println("消费数据：" + take);
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private final BlockingQueue<Integer> list;

        Consumer(BlockingQueue<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int i = new Random().nextInt(100);
                    list.put(i);
                    System.out.println("生产数据：" + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        final BlockingQueue<Integer> list = new ArrayBlockingQueue<Integer>(10);
        Produce produce = new Produce(list);
        Consumer consumer = new Consumer(list);
        produce.start();
        consumer.start();
    }
}