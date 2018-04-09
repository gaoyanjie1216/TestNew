package com.gyj.Test.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最小的k个数
 * Created by Gao on 2018/4/3.
 */
public class LeastKCountNumber {

    /**
     * 思路一排序法, 对n个数排序,然后迭代前k个数即可,时间复杂度以快排为准是O(nlogn)
     * 通过排序会改变原来数组的结构
     */
    private static void leastKCountNumber(int[] a, int k) {

        if (a == null || a.length == 0 || k <= 0) {
            return;
        }

        Arrays.sort(a);
        System.out.print("最小的" + k + "个数是：");
        for (int i = 0; i < k; i++) {
            System.out.print(a[i] + " ");
        }
    }


    /**
     * 局部替换法（不会改变原来数组的顺序结构）
     * 假设前k个数就是整个数组中最小的,找出最大的数和k+1比较,如果比k+1大就和K=1互换位置,
     * 然后再将k数组中的最大数找出,在进行比较,直到数组末尾.时间复杂度O(nk)
     * <p>
     * 把整个数组分为k和n-k 2部分,找出最小的K个数的过程其实就是把最大的数放到n-k部分的过程,每次比较都把最大的数交换到n-k的部分里面。
     * 1.把最先遍历到的k个数赋值到大小为k的数组2中
     * 2.在数组2中找出最大元素max,时间复杂度是o(k)
     * 3.在数组1中遍历剩下的n-k个数,和max比较,如果小于max则交换位置,重复2的过程 o(k)+(n-k)o(k)=n*o(k)
     */
    private static void leastKCountNumber1(int[] a, int k) {

        int[] b = new int[k];
        for (int i = 0; i < k; i++) {
            b[i] = a[i];
        }
        for (int i = k; i < a.length; i++) {
            if (getMax(b) > a[i]) {
                b[0] = a[i];
            }
        }

        System.out.print("最小的" + k + "个数是：");
        for (int i = 0; i < k; i++) {
            System.out.print(b[i] + " ");
        }
    }

    //每次都把最大值放到第一个位置
    private static int getMax(int[] b) {

        for (int i = 0; i < b.length; i++) {
            if (b[0] < b[i]) {
                int temp = b[0];
                b[0] = b[i];
                b[i] = temp;
            }
        }

        return b[0];
    }


    /**
     * 思路三: 对思路二中找最大数的优化,用前K个数建立最大堆,每次用堆顶元素和n-k中各个元素比较,如果堆顶元素较大,则互换位置,
     * 然后调整堆,使之重新成为最大堆。时间复杂度 O（n*logk）
     */
    public static void headSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        buildMaxHeap(array);
    }

    /**
     * 创建堆
     */
    public static void buildMaxHeap(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        //从最后的一个非叶子节点向上开始排,避免迭代没有意义的叶子节点
        int half = (array.length - 1) / 2;
        for (int i = half; i >= 0; i--) {
            maxHeap(array, array.length, i);
        }
    }

    /**
     * 调整堆(沉降法)
     * logn
     */
    public static void maxHeap(int[] array, int heapSize, int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;

        int largest = index;
        //判断有没有左节点,如若有则比较替换largest
        if (left < heapSize && array[left] > array[largest]) {
            largest = left;
        }
        //判断有没有右节点,如若有则largest和右节点比较,注意largest有可能是left也有可能是index
        if (right < heapSize && array[right] > array[largest]) {
            largest = right;
        }

        if (index != largest) {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            //被替换的largest节点所在的堆,需要重新调整,使小值/大值一直下沉
            maxHeap(array, heapSize, largest);
        }
    }

    /**
     * 利用树形的特点保存前面比较的结果,可以减少比较次数s
     */
    public static void leastKCountNumber2(int[] ins, int k) {
        int[] ks = new int[k];
        // 最先遍历的k个数放入数组中 o(k)
        for (int i = 0; i < k; i++) {
            ks[i] = ins[i];
        }
        //构建前k个数的最大堆
        headSort(ks);
        //n-k个数和前面和k中最大数比较
        for (int i = k; i < ins.length; i++) {
            //如果堆顶大于n-k中数,则交换位置
            if (ks[0] > ins[i]) {
                ks[0] = ins[i];
                //调整堆,堆顶被替换了,加入被替换的值非常小,会一直下沉到叶子节点.
                maxHeap(ks, ks.length, 0);
            }

        }

        System.out.print("最小的" + k + "个数是：");
        for (int i = 0; i < k; i++) {
            System.out.print(ks[i] + " ");
        }
    }


    /**
     * 同方法三最大堆，一种数据结构，最大堆，用最大堆保存这k个数，每次只和堆顶比，如果比堆顶小，删除堆顶，新数入堆。
     */
    private static ArrayList<Integer> leastKCountNumber2_1(int[] a, int k) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        int length = a.length;
        if (k > length || k == 0) {
            return result;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for (int i = 0; i < length; i++) {
            if (maxHeap.size() != k) {
                maxHeap.offer(a[i]);
            } else if (maxHeap.peek() > a[i]) {
                Integer temp = maxHeap.poll();
                temp = null;
                maxHeap.offer(a[i]);
            }
        }
        for (Integer integer : maxHeap) {
            result.add(integer);
        }

        System.out.print("最小的" + k + "个数是：");
        for (Integer i : result) {
            System.out.print(i + " ");
        }

        return result;
    }

    public static void main(String[] args) {

        int[] a = {3, 9, 6, 8, -10, 7, -11, 19, 30, 12, 23};
        int[] b = {3, 9, 6, 8, -10, 7, -11, 19, 30, 12, 23};
        int[] c = {3, 9, 6, 8, -10, 7, -11, 19, 30, 12, 23};
        int[] d = {3, 9, 6, 8, -10, 7, -11, 19, 30, 12, 23};

        long startTime = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            leastKCountNumber(a, 5);
        }
        long endTime = System.nanoTime();
        System.out.println("leastKCountNumber:" + (endTime - startTime) / 1000);


        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            leastKCountNumber1(b, 5);
        }
        long endTime1 = System.nanoTime();
        System.out.println("leastKCountNumber1:" + (endTime1 - startTime1) / 1000);


        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            leastKCountNumber2(c, 5);
        }
        long endTime2 = System.nanoTime();
        System.out.println("leastKCountNumber2:" + (endTime2 - startTime2) / 1000);

        long startTime2_1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            leastKCountNumber2_1(d, 5);
        }
        long endTime2_1 = System.nanoTime();
        System.out.println("leastKCountNumber2_1:" + (endTime2_1 - startTime2_1) / 1000);
    }
}

/**
   运行一次的执行结果
   最小的5个数是：-11 -10 3 6 7 leastKCountNumber:390
   最小的5个数是：7 -11 3 6 -10 leastKCountNumber1:56
   最小的5个数是：7 3 6 -11 -10 leastKCountNumber2:58
   最小的5个数是：7 3 6 -10 -11 leastKCountNumber2_1:1188
 *
 * 四种方法时间对比，循环1000次结果
 * leastKCountNumber:756
   leastKCountNumber1:1196
   leastKCountNumber2:1070
   leastKCountNumber2_1:10623
 */