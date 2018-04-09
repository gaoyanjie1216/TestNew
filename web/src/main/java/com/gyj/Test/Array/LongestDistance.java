package com.gyj.Test.Array;

/**
 * 有一个长为n的数组A，求满足0≤a≤b<n的A[b]-A[a]的最大值。
 * 给定数组A及它的大小n，请返回最大差值。
 * 测试样例：[10,5],2  返回：0
 * Created by Gao on 2018/4/5.
 */
public class LongestDistance {

    /**
     * 暴力解法，时间复杂度o(n^2)
     */
    private static int longestDistance(int[] a) {

        //错误代码，对循环理解不深刻，写完代码自己在脑袋里过一遍
        /*int max = 0;
        int k=0;
        for (int i=0; i<a.length; i++) {
            for (int j=k; j<a.length-k; j++) {
                if (a[i+k] - a[i] >= max) {
                    max = a[i+k]-a[i];
                }
            }
        }*/

        int max = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (a[i] - a[j] >= max) {
                    max = a[i] - a[j];
                }
            }
        }
        //System.out.print(max);

        return max;
    }


    /**
     * 每次更新前几个数的最小值，时间复杂度o(n)
     */
    private static int longestDistance1(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int min = a[0];
        int max = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] - min > max) {
                max = a[i] - min;
            }

            //每次更新a[i]之前的最小值
            if (a[i] < min) {
                min = a[i];
            }
        }
        //System.out.print(max);

        return max;
    }

    public static void main(String[] args) {

        int[] a = {10, 5, -1, -4, 10, 20};  //1000次的运行时间对比longestDistance:1540，longestDistance1:440

        long startTime = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            longestDistance(a);
        }
        long endTime = System.nanoTime();
        System.out.println("longestDistance:" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            longestDistance1(a);
        }
        long endTime1 = System.nanoTime();
        System.out.println("longestDistance1:" + (endTime1 - startTime1) / 1000);
    }
}
