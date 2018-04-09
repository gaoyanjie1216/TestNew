package com.gyj.Test.Other;

/**
 * Fibonacci数列是这样定义的：
 * F[0] = 0, F[1] = 1, for each i ≥ 2: F[i] = F[i-1] + F[i-2]
 * 因此，Fibonacci数列就形如：0, 1, 1, 2, 3, 5, 8, 13, ...，在Fibonacci数列中的数我们称为Fibonacci数。
 * 给你一个N，你想让其变为一个Fibonacci数，每一步你可以把当前数字X变为X-1或者X+1，现在给你一个数N求最少需要多少步可以变为Fibonacci数。
 * 输入为一个正整数N(1 ≤ N ≤ 1,000,000)
 * 输出一个最小的步数变为Fibonacci数"
 * Created by Gao on 2018/4/2.
 */
public class FactorialNumber {

    private static int factorialNumber(int n) {

        if (n == 0 || n == 1) {
            return 0;
        }

        int a = 0, b = 1;
        while (b <= n) {      //for (int i=2; b<=n; i++)不过没有啥意义
            int c = a + b;
            a = b;
            b = c;
        }

        System.out.print(b - n > n - a ? n - a : b - n);

        return b - n > n - a ? n - a : b - n;
    }


    public static void main(String[] args) {

        int n = 5;
        factorialNumber(n);
    }
}
