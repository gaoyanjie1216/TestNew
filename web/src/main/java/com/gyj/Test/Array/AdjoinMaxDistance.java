package com.gyj.Test.Array;

import java.util.Arrays;

/**
 * 请设计一个复杂度为O(n)的算法，计算一个未排序数组中排序后相邻元素的最大差值。
 * 给定一个整数数组A和数组的大小n，请返回最大差值。保证数组元素个数大于等于2小于等于500。
 * 测试样例：[9,3,1,10],4   返回：6
 * Created by Gao on 2018/4/5.
 */
public class AdjoinMaxDistance {

    /**
     * 相邻两元素的最大差值，时间复杂度最好是O(n*logn)，不符合题目要求,并且改变的数组的结构
     */
    private static int adjoinMaxDistance(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        Arrays.sort(a);
        int max = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i + 1] - a[i] >= max) {
                max = a[i + 1] - a[i];
            } else if (a[i] - a[i + 1] > max) {
                max = a[i] - a[i + 1];
            }
        }
        System.out.print(max);

        return max;
    }

    /**
     * 时间复杂度为O(n)的算法，但不好理解
     * 最适合的方法是桶排序：
     1.找出最大值和最小值。
     2.生成一个最大值-最小值的区间 比如最大值9，最小值3，那就需要7个桶
     3.往里面填
     4.查找空桶，最多的即为最大差值。
     */
    private static int adjoinMaxDistance1(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int max = a[0];
        int min = a[0];
        for (int i = 0; i < a.length; i++) {
            if (max < a[i])
                max = a[i];
            if (min > a[i])
                min = a[i];
        }
        int[] arr = new int[max - min + 1]; // 生成桶
        for (int i = 0; i < a.length; i++) {
            arr[a[i] - min]++; // 填桶
        }
        int count = 0;
        int maxDis = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) { // 桶为空
                count++;   //记录连续空桶数
            } else {
                if (maxDis < count)
                    maxDis = count;
                count = 0;
            }
        }
        //System.out.print(maxDis + 1);

        return maxDis + 1;
    }

    public static void main(String[] args) {

        int[] a = new int[]{9, -4, 1, 30, 10};

        int max = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            max = adjoinMaxDistance(a);
        }
        long endTime = System.nanoTime();
        System.out.println("adjoinMaxDistance:相邻差最大值：" + max + "  运行时间：" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            adjoinMaxDistance1(a);
        }
        long endTime1 = System.nanoTime();
        System.out.println("adjoinMaxDistance1:相邻差最大值：" + max + "  运行时间："+ (endTime1 - startTime1) / 1000);
    }
}

/**
 *  int[] a = new int[]{9, -4, 1, 30, 10};
 *  运行1000次时间
 * adjoinMaxDistance:相邻差最大值：20  运行时间：1219
   adjoinMaxDistance1:相邻差最大值：20  运行时间：2278
   运行一次时间
   adjoinMaxDistance:相邻差最大值：20  运行时间：268
   adjoinMaxDistance1:相邻差最大值：20  运行时间：7
 */