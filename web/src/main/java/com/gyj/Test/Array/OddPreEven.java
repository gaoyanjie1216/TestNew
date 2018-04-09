package com.gyj.Test.Array;

/**
 * 输入一个整数数组，调整数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。要求时间复杂度为O(n)。
 * Created by Gao on 2018/3/27.
 */
public class OddPreEven {

    private static String oddPreEven(int[] a) {

        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 != 0) {
                sb1.append(a[i]).append(",");
            } else {
                sb2.append(a[i]).append(",");
            }
        }

        return sb1.append(sb2).toString();
    }

    private static int[] oddPreEven1(int[] n) {

        int[] temp = new int[n.length];
        int k = 0;
        for (int i : n) {
            if (i % 2 == 1) {
                temp[k++] = i;
            }
        }
        for (int t : n) {
            if (t % 2 == 0) {
                temp[k++] = t;
            }
        }

        return temp;
    }

    public static void main(String[] args) {

        int[] n = {1, 4, 5, 8, 7, 9, 11, 6};

        String s = oddPreEven(n);
        char[] chars = s.toCharArray();
        System.out.print(chars);
        System.out.println();

        int[] a = oddPreEven1(n);
        for (int i : a) {
            System.out.print(i + " ");
        }

    }
}
