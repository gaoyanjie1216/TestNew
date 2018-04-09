package com.gyj.Test.Sort;

import java.util.Arrays;

/**
 * Created by Gao on 2018/3/23.
 * 基本思想：两两比较相邻的记录的关键字，如果反序（前面的大于后面的）则交换，直到没有反序的记录为止。
 * 同样选择排序无论序列是怎样的都是要比较n(n-1)/2次的，这是比较次数，而对于交换次数来说：如果数组有序则不需要交换，如果逆序则要交换n次
   普通冒泡排序时间复杂度最坏和最好都是O(n*n)，优化后的最优是O(n).
 * 设数组的长度为N：
 * （1）比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * （2）这样对数组的第0个数据到N-1个数据进行一次遍历后，最大的一个数据就“沉”到数组第N-1个位置。
 * （3）N=N-1，如果N不为0就重复前面二步，否则排序完成。
 *
 * 时间复杂度
 这个时间复杂度还是很好计算的：外循环和内循环以及判断和交换元素的时间开销；
 最优的情况也就是开始就已经排序好序了，那么就可以不用交换元素了，则时间花销为：[ n(n-1) ] /  2；所以最优的情况时间复杂度为：O( n^2 )；
 最差的情况也就是开始的时候元素是逆序的，那么每一次排序都要交换两个元素，则时间花销为：[ 3n(n-1) ] / 2；（其中比上面最优的情况所花的时间就是在于交换元素的三个步骤）；
 所以最差的情况下时间复杂度为：O( n^2 )；
 综上所述：
 最优的时间复杂度为：O( n^2 ) ；有的说 O(n)，下面会分析这种情况；
 最差的时间复杂度为：O( n^2 )；
 平均的时间复杂度为：O( n^2 )；

 空间复杂度
 空间复杂度就是在交换元素时那个临时变量所占的内存空间；
 最优的空间复杂度就是开始元素顺序已经排好了，则空间复杂度为：0；
 最差的空间复杂度就是开始元素逆序排序了，则空间复杂度为：O(n)；
 平均的空间复杂度为：O(1)；
 *
 */

public class BubbleSort {

    private static void bubbleSort1(int[] a) {
        int count1 = 0, count2 = 0;
        for (int i = 0; i < a.length - 1; i++) {    //for(j=1; j<a.length-i; j++)
            for (int j = a.length - 1; j > i; j--) {
                count1++;
                if (a[j - 1] > a[j]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    count2++;
                }
            }
        }
        System.out.print("比较的次数为:" + count1 + "  移动的次数为：" + count2 + "     ");
    }

    //下面开始考虑优化，如果对于一个本身有序的序列，或则序列后面一大部分都是有序的序列，上面的算法就会浪费很多的时间开销，
    //这里设置一个标志flag，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。

    /**
     * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
     * 这个交换两个变量而无需借助第3个临时变量过程，其实现主要是基于异或运算的如下性质：
     * 1.任意一个变量X与其自身进行异或运算，结果为0，即X^X=0
     * 2.任意一个变量X与0进行异或运算，结果不变，即X^0=X
     * 3.异或运算具有可结合性，即a^b^c=（a^b）^c=a^（b^c）
     * 4.异或运算具有可交换性，即a^b=b^a
     * a = a ^ b;
     * b = a ^ b;
     * a = a ^ b;
     *
     * @param a
     */
    public static void bubbleSort2(int[] a) {
        boolean flag = true;
        int k = a.length;
        int count1 = 0, count2 = 0;
        while (flag) {
            flag = false;
            for (int j = 1; j < k; j++) {
                count1++;
                if (a[j - 1] > a[j]) {
                    a[j - 1] = a[j - 1] ^ a[j];
                    a[j] = a[j - 1] ^ a[j];
                    a[j - 1] = a[j - 1] ^ a[j];
                    count2++;
                    flag = true;
                }
            }
            k--;
        }
        System.out.print("比较的次数为:" + count1 + "  移动的次数为：" + count2 + "     ");
    }

    //记下面这种方法
    private static void bubbleSort2_1(int[] a) {
        int count1 = 0, count2 = 0;
        boolean flag = true;
        for (int i = 0; i < a.length - 1 && flag; i++) {
            flag = false;
            for (int j = a.length - 1; j > i; j--) {
                count1++;
                if (a[j - 1] > a[j]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;

                    count2++;
                    flag = true;
                }
            }
        }
        System.out.print("比较的次数为:" + count1 + "  移动的次数为：" + count2 + "     ");
    }

    //再进一步做优化。比如，现在有一个包含1000个数的数组，仅前面100个无序，后面900个都已排好序且都大于前面100个数字，那么在第一趟遍历后，最后发生交换的位置必定小于100，
    //且这个位置之后的数据必定已经有序了，也就是这个位置以后的数据不需要再排序了，于是记录下这位置，第二次只要从数组头部遍历到这个位置就可以了。如果是对于上面的冒泡排序算法2来说，
    //虽然也只排序100次，但是前面的100次排序每次都要对后面的900个数据进行比较，而对于现在的排序算法3，只需要有一次比较后面的900个数据，之后就会设置尾边界，保证后面的900个数据不再被排序。
    private static void bubbleSort3(int[] a) {
        int count1 = 0, count2 = 0, k;
        int flag = a.length;
        while (flag > 0) {
            k = flag;
            flag = 0;
            for (int j = 1; j < k; j++) {
                count1++;
                if (a[j - 1] > a[j]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;

                    count2++;
                    flag = j;
                }
            }
        }
        System.out.print("比较的次数为:" + count1 + "  移动的次数为：" + count2 + "     ");
    }


    public static void main(String[] args) {

        int[] a = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        int[] b = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        int[] c = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        int[] d = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        int[] e = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        bubbleSort1(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        bubbleSort2(b);
        for (int i : b) {
            System.out.print(i + " ");
        }
        System.out.println();

        bubbleSort2_1(c);
        for (int i : c) {
            System.out.print(i + " ");
        }
        System.out.println();

        bubbleSort3(d);
        for (int i : d) {
            System.out.print(i + " ");
        }
        System.out.println();

        //java中Arrays.sort()采用了一种名为TimSort的排序算法，就是归并排序的优化版本
        Arrays.sort(e);
        for (int i : e) {
            System.out.print(i + " ");
        }
    }
}

/**
 * 比较的次数为:36  移动的次数为：11     1 2 3 6 7 8 9 11 56
   比较的次数为:33  移动的次数为：11     1 2 3 6 7 8 9 11 56
   比较的次数为:26  移动的次数为：11     1 2 3 6 7 8 9 11 56
   比较的次数为:33  移动的次数为：11     1 2 3 6 7 8 9 11 56
   1 2 3 6 7 8 9 11 56
 */
