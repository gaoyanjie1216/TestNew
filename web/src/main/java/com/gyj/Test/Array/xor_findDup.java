package com.gyj.Test.Array;

import java.util.Random;

/**
 * 寻找数组中出现的唯一重复的一个数
 *
 * 题目要求：
 假设你有一个用1001个整数组成的数组，这些整数是任意排列的，但是你知道所有的整数都在1到1000(包括1000)之间。
 此外，除一个数字出现两次外，其他所有数字只出现一次。假设你只能对这个数组做一次处理，用一种算法找出重复的那个数字。

 解题思路：
 数组中的1001个数相互异或的结果与（1-1000）相互异或的结果再做异或，得出的值即位所求

 原理：
 假设重复数为A，其余999个数相互异或结果为B。
 那么1-1000异或结果为A^B；
 1001个数异或结果为A^(A^B) 。
 由于异或满足交换律和结合律，且X^X = 0， 0^X = X；
 则有：A^(A^B)  = B ；(A^B)^B=A。A即为所求。

 * Created by Gao on 2018/3/12.
 */
public class xor_findDup {

/*    public static void find(int[] arr, int n) {
        int result = 0;
        for (int i = 0; i < n + 1; i++) {
            result ^= arr[i];    //数组内的数相互异或
        }
        for (int i = 1; i <= n; i++) {
            result ^= i;         //将上面的结果与1~n的数相互异或
        }
        System.out.println("找到重复的数为：" + result);
    }*/

    //上述代码可以简化为下面的函数
    public static int find(int[] a, int n) {
        int result = 0;
        for (int i=0; i< n+1; i++) {
            result ^= a[i];
            result ^= i;
        }
        System.out.println("找到重复的数为：" + result);

        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[10001];
        for (int i = 1; i <= 1000; i++) {
            arr[i] = i;
        }
        int r = new Random().nextInt(1000);
        System.out.println("放入1000以内的一个随机数：" + r);
        arr[0] = r;
        find(arr, 1000);
    }
}
