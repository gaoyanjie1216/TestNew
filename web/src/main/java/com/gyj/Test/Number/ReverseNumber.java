package com.gyj.Test.Number;

/**
 * 对于一个整数X，定义操作rev(X)为将X按数位翻转过来，并且去除掉前导0。例如:
 * 如果 X = 123，则rev(X) = 321;
 * 如果 X = 100，则rev(X) = 1.
 * 现在给出整数x和y,要求rev(rev(x) + rev(y))为多少？
 * 输入为一行，x、y(1 ≤ x、y ≤ 1000)，以空格隔开
 * 输入123 100 输出223
 * Created by Gao on 2018/3/31.
 */
public class ReverseNumber {

    private static int reverseNumber(int a, int b) {

        int n = Integer.parseInt(reverse(a)) + Integer.parseInt(reverse(b));
        String str = reverse(n);
        //System.out.println(Integer.parseInt(str));

        return Integer.parseInt(str);
    }

    private static String reverse(int n) {

        StringBuffer sb = new StringBuffer(String.valueOf(n));
        String s = sb.reverse().toString();
        String s1 = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                s1 += s.charAt(i);
            }
        }

        return s1;
    }


    private static int reverseNumber1(int a, int b) {

        int result = rev(rev(a) + rev(b));
        //System.out.println(result);

        return result;
    }


    private static int rev(int x) {
        int res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x = x / 10;
        }

        return res;
    }


    public static void main(String[] args) {

        int a = 123, b = 100;
        //System.nanoTime提供相对精确的计时,以纳秒为单位,常在产生随机数函数以及线程池中的一些函数使用.
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            reverseNumber(a, b);
        }
        long endTime = System.nanoTime();
        System.out.println("reverseNumber:" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            reverseNumber1(a, b);
        }
        long endTime1 = System.nanoTime();
        System.out.println("reverseNumber1:" + (endTime1 - startTime1) / 1000);
        reverseNumber1(a, b);
    }
}

/**
 * int a = 123, b = 100;
 * reverseNumber:8453
 * reverseNumber1:494
 */
