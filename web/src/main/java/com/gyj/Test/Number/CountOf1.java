package com.gyj.Test.Number;

/**
 * Java实现求一个整数的二进制数中1的个数
 * Created by Gao on 2018/3/31.
 */
public class CountOf1 {

    /**
     * 如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，那么原来处在整数最右边的1就会变为0，
     * 原来在1后面的所有的0都会变成1(如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
     * 举个例子：一个二进制数1100，从右边数起第三位是处于最右边的一个1。减去1后，第三位变成0，它后面的两位0变成了1，
     * 而前面的1保持不变，因此得到的结果是1011.我们发现减1的结果是把最右边的一个1开始的所有位都取反了。这个时候如果我们再
     * 把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0。如1100&1011=1000.也就是说，
     * 把一个整数减去1，再和原整数做与运算，会把该整数最右边一个1变成0.那么一个整数的二进制有多少个1，就可以进行多少次这样的操作。
     * <p>
     * while循环中只需要执行n中1的个数+1次。以n=19为例，二进制数为：0001 0011
     * 第一次循环：0001 0011 & 0001 0010 = 0001 0010
     * 第二次循环：0001 0010 & 0001 0001 = 0001 0001
     * 第三次循环：0001 0001 & 0001 0000 = 0001 0000
     * 第四次循环：0001 0000 & 0000 1111 = 0000 0000 ，此时n=0，跳出循环
     *
     * @param n
     * @return
     */
    //使用n=n&(n-1)的方法，效率会更高
    private static int countOf1(int n) {

        int count = 0;
        while (n > 0) {
            n = n & (n - 1);
            count++;
        }
        //System.out.println(count);

        return count;
    }

    /**
     * 位移运算
     *
     * @param n
     * @return
     */
    private static int countOf1_1(int n) {

        int count = 0;
        while (n > 0) {
            count += n & 0X01;
            n >>= 1;
        }
        //System.out.println(count);

        return count;
    }

    /**
     * 取余计算
     *
     * @param n
     * @return
     */
    private static int countOf1_2(int n) {

        int count = 0;
        while (n > 0) {
            if (n % 2 == 1) {
                count++;
            }
            n = n / 2;
        }
        //System.out.println(count);

        return count;
    }

    /**
     * 转换成二进制的数组
     *
     * @param n
     * @return
     */
    private static int countOf1_3(int n) {

        int count = 0;
        String str = Integer.toBinaryString(n);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                count++;
            }
        }
        //System.out.println(count);

        return count;
    }


    public static void main(String[] args) {
        int a = 123;  //a=123, count=6; a=21, count=3

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countOf1(a);
        }
        long endTime = System.nanoTime();
        System.out.println("countOf1:" + (endTime - startTime) / 1000);

        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countOf1_1(a);
        }
        long endTime1 = System.nanoTime();
        System.out.println("countOf1:" + (endTime1 - startTime1) / 1000);

        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countOf1_2(a);
        }
        long endTime2 = System.nanoTime();
        System.out.println("countOf1:" + (endTime2 - startTime2) / 1000);

        long startTime3 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            countOf1_3(a);
        }
        long endTime3 = System.nanoTime();
        System.out.println("countOf1:" + (endTime3 - startTime3) / 1000);
    }
}

/**
 * a=123时运行时间对比
 * countOf1:214
 * countOf1:364
 * countOf1:352
 * countOf1:1544
 */
