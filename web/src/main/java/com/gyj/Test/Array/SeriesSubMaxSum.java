package com.gyj.Test.Array;

import static java.lang.Math.max;

/**
 * 一个数组有 N 个元素，求连续子数组的最大和。 例如：[-1,2,1]，和最大的连续子数组为[2,1]，其和为 3
 * Created by Gao on 2018/4/4.
 */
public class SeriesSubMaxSum {


    private static int seriesSubMaxSum(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int end = 0, count = 0, max = 0, sum = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 0) {
                count++;
                if (count > max) {
                    max = count;
                    end = i;
                }
            } else {
                count = 0;
            }
        }

        for (int i = end - max + 1; i < end + 1; i++) {
            sum += a[i];
        }
        //System.out.println(sum);

        return sum;
    }


    /**
     * 时间复杂度为 o(n)
     */
    private static int seriesSubMaxSum1(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int sum = a[0];
        int cur = a[0];
        for (int i = 1; i < a.length; i++) {
            if (cur < 0) {
                cur = 0;
            }
            cur += a[i];
            if (cur >= sum) {
                sum = cur;
            }
        }
        //System.out.println(sum);

        return sum;
    }


    /**
     * 动态规划思想
     * 状态方程 max(dp[i]) = getMax(max(dp[i-1]) + arr[i] , arr[i]) 。
     * 上面式子的意义是：我们从头开始遍历数组，遍历到数组元素arr[i]时，连续的最大的和可能为 max(dp[i-1])+arr[i] ，
     * 也可能为arr[i], 做比较即可得出哪个更大，取最大值。时间复杂度为 o(n)
     */
    private static int seriesSubMaxSum2(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int sum = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            sum = getMax(sum + a[i], a[i]);
            if (sum >= max) {
                max = sum;
            }
        }
        //System.out.println(max);

        return max;
    }

    private static int getMax(int a, int b) {
        return a >= b ? a : b;
    }


    private static int seriesSubMaxSum2_1(int[] a) {

        int max = a[0];
        int sum = a[0];
        for (int i = 1; i < a.length; i++) {
            sum = max(sum + a[i], a[i]);
            max = max(sum, max);
        }
        //System.out.println(max);

        return max;
    }


    public static void main(String[] args) {

        int[] a = new int[]{-1, 2, 1, -2, -6, 8, 4, 5, -10, 5, -3};

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            seriesSubMaxSum(a);
        }
        long endTime = System.nanoTime();
        System.out.println("seriesSubMaxSum:" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            seriesSubMaxSum1(a);
        }
        long endTime1 = System.nanoTime();
        System.out.println("seriesSubMaxSum1:" + (endTime1 - startTime1) / 1000);

        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            seriesSubMaxSum2(a);
        }
        long endTime2 = System.nanoTime();
        System.out.println("seriesSubMaxSum2:" + (endTime2 - startTime2) / 1000);

        long startTime2_1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            seriesSubMaxSum2_1(a);
        }
        long endTime2_1 = System.nanoTime();
        System.out.println("seriesSubMaxSum2_1:" + (endTime2_1 - startTime2_1) / 1000);
    }
}

/**
 * int[] a = new int[]{-1, 2, 1, -2, -6, 8, 4, 5, -10, 5, -3};分别运行一次的时间
 * 17  seriesSubMaxSum:178
 * 17  seriesSubMaxSum1:26
 * 17  seriesSubMaxSum2:43
 * 17  seriesSubMaxSum2_1:42
 *
 * 运行1000次的平均时间
 * seriesSubMaxSum:355
   seriesSubMaxSum1:310
   seriesSubMaxSum2:351
   seriesSubMaxSum2_1:407
 */