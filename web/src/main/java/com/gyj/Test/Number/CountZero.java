package com.gyj.Test.Number;

import java.math.BigDecimal;

/**
 * 输入一个正整数n,求n!(即阶乘)末尾有多少个0？ 比如: n = 10; n! = 3628800,所以答案为2
 * 输入为一行，n(1 ≤ n ≤ 1000)
 * Created by Gao on 2018/3/30.
 */
public class CountZero {

    private static int countZero(int n) {

        int a = n;
        if (n == 0 || n == 1) {
            return 1;
        }

        int result = 1;
        while (n > 0) {
            result *= n--;
        }

       /* int i = 1;
        while (i <= n) {
            result *= i;
            i++;
        }

        for (int j = 2; j < n; j++) {
            result *= j;
        }*/

        int count = 0;
        for (int k = String.valueOf(result).length() - 1; k >= 0; k--) {
            //一定要注意String.valueOf(result).charAt(i)是一个字符，右边是'0', 不是整数0，整数0也不会报错
            if (String.valueOf(result).charAt(k) == '0') {
                count++;
            }
            if (String.valueOf(result).charAt(k - 1) != '0') {
                break;
            }
        }
        //System.out.println(a + "！的末尾0个数是：" + count);

        return count;
    }

    private static int countZero1(int n) {

        int a = n;
        if (n == 0 || n == 1) {
            return 1;
        }

        BigDecimal result = new BigDecimal(1);
        for (int i = 1; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }


        /**
         * 把一个基本数据类型转为String的时候，优先考虑使用toString()方法。至于为什么，很简单：
         （1）String.valueOf()方法底层调用了Integer.toString()方法，但是会在调用前做空判断；
         （2）Integer.toString()方法就不说了，直接调用了；
         （3）i + “”底层使用了StringBuilder实现，先用append方法拼接，再用toString()方法获取字符串；
         三者对比下来，明显是2最快、1次之、3最慢。
         */
        int count = 0;
        for (int i = result.toString().length() - 1; i >= 0; i--) {
            //一定要注意String.valueOf(result).charAt(i)是一个字符，右边是'0', 不是整数0，整数0也不会报错
            if (result.toString().charAt(i) == '0') {
                count++;
            }
            if (result.toString().charAt(i - 1) != '0') {
                break;
            }
        }
        //System.out.println(a + "！的末尾0个数是：" + count);

        return count;
    }

    /**
     * 2*5=10
     * 0的个数,由有多少个2和5决定.
     * 在连续的自然数中,2的个数远多于5的个数.
     * 所以50!里有多少个5,就有多少个0.
     * 每5个数里就有1个5的因子.每25个数里就有1个25的因子.
     * 其中25=5*5,5已经给每5个数里就有1个5的因子计算过了,所以1个25的因子只多了1个5.
     * 50!,一起就有50/5+50/25=10+2=12个5.
     * 50!的末尾有12个连续的0.
     *
     * @param n
     * @return
     */
    private static int countZero2(int n) {

        int a = n;
        int count = 0;
        for (int i = 1; i <= n; i++) {
            int j = i;
            while (j % 5 == 0) {
                count++;
                j = j / 5;
            }
        }
        //System.out.println(a + "！的末尾0个数是：" + count);

        return count;
    }

    /**
     * 1、分析：阶乘虽然大，但是求0的个数只有2x5，4x5，6x5，8x5才有0，但是5的数目最小，且都
     * 要用到5，即可转化为求N！中5的个数
     * 若N = 5，则5的个数为1个
     * N = 6，1个，
     * N = 10，有5和2x5两个5
     * N = 15，有5和2x5和3x5三个5，
     * N = 20，有5和2x5和3x5和4x5四个5
     * N = 25，有5和2x5和3x5和4x5和5x5五个5
     *
     * @param n
     * @return
     */
    private static int countZero3(int n) {

        int a = n;
        if (n < 5) return 0;
        int count = 0;
        while (n > 0) {
            count += n / 5;
            n /= 5;
        }
        //System.out.println(a + "！的末尾0个数是：" + count);

        return count;
    }

    public static void main(String[] args) {

        int a = 10;
        countZero(a);

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countZero1(1000);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("countZero1:" + duration / 1000);


        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countZero2(1000);
        }
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.println("countZero2:" + duration1 / 1000);


        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countZero3(1000);
        }
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("countZero3:" + duration2 / 1000);

    }
}

/**
 * 12！的末尾0个数是：2
 * 10000！的末尾0个数是：2499
 * 10000！的末尾0个数是：2499

/**
 * 以a = 10!为例
 * 时间效率上差距太大了，实现一个功能算法的效率很重要
 *countZero1:687234
 countZero2:19817
 countZero2:344
 */
