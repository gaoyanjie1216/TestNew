package com.gyj.Test.Number;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入n个整数，输出出现次数大于等于数组长度一半的数。
 * <p>
 * Created by Gao on 2018/4/2.
 */
public class MoreThanHalfCounts {

    /**
     * 方法一：
     * 数组排序，然后中间值肯定是要查找的值。 排序最小的时间复杂度（快速排序）O(NlogN)，加上遍历。
     */
    private static int moreThanHalfCounts(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        Arrays.sort(a);   //这种是默认的排序，按照字典序(ASCII)的顺序进行排序
        int mid = (a.length - 1) / 2;
        int result = a[mid];
        boolean isMoreThanHalf = checkMoreThanHalf(a, result);

        if (!isMoreThanHalf) {
            result = 0;
        }
        return result;

    }
    //记录某个数字出现一半
    private static boolean checkMoreThanHalf(int[] a, int result) {

        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == result) {
                count++;
            }
        }

        return (count >= a.length / 2);
    }


    //法二
    private static int moreThanHalfCounts1(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i : a) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= a.length / 2) {
                return entry.getKey();
            }
        }

        //没有找到返回-1
        return -1;
    }


    /**
     * 法三：
     * 要求的数组必须有数字超过一半才行，否则此算法不符合要求
     * 数组中有一个数字出现的次数超过数组长度的一半，也就是说它出现的次数比其他所有数字出现的次数的和还要多。
     * 　　因此我们可以考虑用两个变量：一个保存一个数字，一个保存次数。开始时，保存数组中第一个元素，次数设置为1；
     * 　　遍历数组：
     * 　　如果下一个数字和之前保存的数字相同，则次数递增1；如果下一个数字和之前保存的数字不同，则次数递减1；
     * 　　如果次数为零，我们需要保存下一个数字，并把次数设为1。
     * 　　由于我们要找的数字出现的次数比其他所有数字出现的次数之和还要多，那么要找的数字肯定是最后一次把次数设为1时对应的数字。
     * 　　但是最后还是需要检查一下该数字的出现次数是否超过了数组长度的一半，因为可能数组中并不包含这样的数字。
     * 这个方法的时间复杂度是O(N)，空间复杂度是O(1)。
     */
    private static int moreThanHalfCounts2(int[] a) {

        if (a == null || a.length == 0) {
            return 0;
        }

        int result = a[0];
        int num = 1;
        for (int i = 0; i < a.length; i++) {
            if (num == 0) {
                result = a[i];
                num++;
            } else {
                if (result == a[i]) {
                    num++;
                } else {
                    num--;
                }
            }
        }
        //System.out.print(result);

        return result;
    }

    public static void main(String[] args) {

        int[] a = {3, 9, 3, 2, 5, 6, 7, 3, 2, 3, 3, 3};
        int[] b = {0};
        int[] c = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9};  //对于数组c没有满足要求moreThanHalfCounts1输出9
        int[] d = {3, 9, 3, 2, 5, 6, 7, 3, 2, 3, 3, 3};
        int[] e = {3, 9, 3, 2, 5, 6, 7, 3, 2, 3, 3, 3};

        int n = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            n = moreThanHalfCounts(a);
        }
        long endTime = System.nanoTime();
        System.out.println("moreThanHalfCounts:次数超过一半的数字是:" + n + "  运行时间是：" + (endTime - startTime) / 1000);


        int n1 = 0;
        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            n1 = moreThanHalfCounts1(d);
        }
        long endTime1 = System.nanoTime();
        System.out.println("moreThanHalfCounts1次数超过一半的数字是:" + n1 + "  运行时间是：" + (endTime1 - startTime1) / 1000);


        int n2 = 0;
        long startTime2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            n2 = moreThanHalfCounts2(e);
        }
        long endTime2 = System.nanoTime();
        System.out.println("moreThanHalfCounts2次数超过一半的数字是:" + n2 + "  运行时间是：" + (endTime2 - startTime2) / 1000);
    }
}

/**
 * 以数组a = {3, 9, 3, 2, 5, 6, 7, 3, 2, 3, 3, 3}为例
 * moreThanHalfCounts:次数超过一半的数字是:3  运行时间是：938
   moreThanHalfCounts1次数超过一半的数字是:3  运行时间是：5037
   moreThanHalfCounts2次数超过一半的数字是:3  运行时间是：415
 */
