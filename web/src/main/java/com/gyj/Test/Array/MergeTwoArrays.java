package com.gyj.Test.Array;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * 两个整数数组合并成一个从小到大无重复的数组
 * Created by Gao on 2018/4/2.
 */
public class MergeTwoArrays {

    private static int[] mergeTwoArrays(int[] a, int[] b) {

        if (a.length == 0 && b.length == 0) {
            return null;
        }

        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < a.length; i++) {
            set.add(a[i]);
        }
        for (int j : b) {
            set.add(j);
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            sb.append(it.next());      //一定要注意是it.next()
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());

        String[] split = sb.toString().split(" ");

        int[] c = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            c[i] = Integer.parseInt(split[i]);
        }

        for (int i : c) {
            System.out.print(i + " ");
        }

        return c;
    }


    private static int[] mergeTwoArrays1(int[] a, int[] b) {

        if (a.length == 0 && b.length == 0) {
            return null;
        }

        TreeSet<Integer> set = new TreeSet<>();
        for (int i : a) {
            set.add(i);
        }
        for (int j : b) {
            set.add(j);
        }

        /*for (int k : set) {
            System.out.print(k + " ");
        }*/

        int length = set.size();
        int[] c = new int[length];
        int m = 0;
        for (int k : set) {
            c[m++] = k;
        }

        return c;
    }


    public static void main(String[] args) {

        int[] a = new int[]{1, 3, 4, 2, 2};
        int[] b = new int[]{2, 3, 6, 7, 9, 7, 10};
        mergeTwoArrays(a, b);
        System.out.println();

        int[] c = mergeTwoArrays1(a, b);
        for (int i : c) {
            System.out.print(i + " ");
        }
    }
}
