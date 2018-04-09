package com.gyj.Test.Array;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Gao on 2018/4/3.
 */
public class Arrays_Sort {

    public static void main(String[] args) {

        /**
         * 默认升序排序
         */
        int[] a = {9, 8, 7, 2, 3, 4, 1, 0, 6, 5};
        Arrays.sort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        /**
         * 针对数组中的部分数据进行排序
         * Arrays.sort(int[] a, int fromIndex, int toIndex), 这种形式是对数组部分排序，
         * 也就是对数组a的下标从fromIndex到toIndex-1的元素排序. 注意：下标为toIndex的元素不参与排序！
         */
        int[] b = {9, 8, 7, 2, 3, 4, 1, 0, 6, 5};
        Arrays.sort(b, 0, 3);  //只是排序了前三个数
        for (int i : b) {
            System.out.print(i + " ");
        }
        System.out.println();

        /**
         * 自定义一个类实现Comparator接口，可以实现逆序操作
         * 注意，要想改变默认的排列顺序，不能使用基本类型（int, double, char), 而要使用它们对应的包装类
         */
        Integer[] c = {9, 8, 7, 2, 3, 4, 1, 0, 6, 5};
        //定义一个自定义类MyComparator的对象
        Comparator<Integer> cmp = new MyComparator();
        Arrays.sort(c, cmp);
        for (int i : c) {
            System.out.print(i + " ");
        }
        System.out.println();


        /**
         * 字符串排序
         */
        String[] str = {"abc", "aaa", "abcd"};
        Arrays.sort(str, String.CASE_INSENSITIVE_ORDER);  //aaa abc abcd 字符串升序

        //String中定义的忽略大小写，完全通过字母的顺序进行排序
        //Arrays.sort(str, Collections.reverseOrder());     //abcd abc aaa 字符串逆序
        for (int i=0; i<str.length; i++) {
            System.out.print(str[i] + " ");
        }
    }
}

//Comparator是一个接口，所以这里我们自己定义的类MyComparator要implements该接口, 而不是extends Comparator
class MyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        //如果o1小于o2，我们就返回正值，如果o1大于o2我们就返回负值，
        //这样颠倒一下，就可以实现反向排序了
        if (o1 < o2) {
            return 1;
        } else if (o1 > o2) {
            return -1;
        } else {
            return 0;
        }
    }
}

/**
 * Arrays.sort()方法底层是DualPivotQuicksort，翻译过来就是双轴快速排序，可以发现如果数组的长度小于QUICKSORT_THRESHOLD的话
 * 就会使用这个双轴快速排序，而这个值是286，
 * 总结一下Arrays.sort()方法，如果数组长度大于等于286且连续性好的话，就用归并排序，如果大于等于286且连续性不好的话就用双轴快速排序。
 * 如果长度小于286且大于等于47的话就用双轴快速排序，如果长度小于47的话就用插入排序
 */