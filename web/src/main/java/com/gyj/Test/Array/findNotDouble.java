package com.gyj.Test.Array;

/**
 * 问题：一个整型数组里除了一个数字之外，其他数字都出现了两次。找出这个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 分析：如果没有对时间复杂度的要求，可以先对整个数组进行排序，然后从第一个数字开始遍历，比较相邻的两个数，从而找出这个只出现一次的数字，这种方法的时间复杂度最快是O(nlogn)。
 考虑用异或运算，任何一个数字异或它自己都等于0，如果从头到尾依次异或数组中的每一个数字，那些出现两次的数字全部在异或中抵消掉，最终的结果刚好是这个只出现1次的数字。
 * Created by Gao on 2018/3/12.
 */
public class findNotDouble {

    public static int findNotDouble(int[] a) {
        int result = a[0];
        for (int i = 1; i < a.length; i++) {
            result ^= a[i];
        }
        return result;
    }

   /* public static List<Integer> FindNumsAppearOnce(int[] array) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (!list.contains(array[i]))
                list.add(array[i]);
            else
                list.remove(new Integer(array[i]));
        }
        int num1[] = {0}, num2[] = {0};
        if (list.size() > 1) {
            num1[0] = list.get(0);
            num2[0] = list.get(1);
        }

        return list;
    }*/

    public static void main(String[] args) {
        int a[] = {1, 2, 3, 4, 2, 3, 5, 4, 1, 5, 6, 7, 7}; //满足交换结合律，可以假设先让相同的数字异或
        int num = findNotDouble(a);
        System.out.println(num);

        //int num1[] = {}, num2[] = {};
        //List<Integer> integers = FindNumsAppearOnce(a);

    }
}
