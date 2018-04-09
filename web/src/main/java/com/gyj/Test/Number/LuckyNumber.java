package com.gyj.Test.Number;

/**
 * 现在对于一个数字x，小明同学定义出了两个函数f(x)和g(x)。 f(x)表示把x这个数用十进制写出后各个数位上的数字之和。
 * 如f(123)=1+2+3=6。 g(x)表示把x这个数用二进制写出后各个数位上的数字之和。如123的二进制表示为1111011，那么，
 * g(123)=1+1+1+1+0+1+1=6。 小明同学发现对于一些正整数x满足f(x)=g(x)，他把这种数称为幸运数，现在他想知道，
 * 小于等于n的幸运数有多少个？
 * 每组数据输入一个数n(n<=100000), 每组数据输出一行，小于等于n的幸运数个数。
 * Created by Gao on 2018/3/31.
 */
public class LuckyNumber {

    private static int luckyNumber(int n) {

        int[] b = new int[n];
        int count = 0, k = 0, j = 0;
        int[] a = new int[n];
        for (int i = 1; i <= n; i++) {
            if (sum(i, 2) == sum(i, 10)) {
                count++;
                a[k++] = i;
                b[j++] = sum(i, 2);
            }
        }

        System.out.println("幸运数是：");
        for (int i : a) {
            if (i != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("幸运数各位数上的和依次是：");
        for (int i : b) {
            if (i != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("个数是：" + count);

        return count;
    }

    private static int sum(int n, int format) {

        int sum = 0;
        while (n > 0) {
            sum += n % format;
            n = n / format;
        }

        return sum;
    }

    public static void main(String[] args) {

        int a = 21;
        luckyNumber(a);  // count=3
    }
}
