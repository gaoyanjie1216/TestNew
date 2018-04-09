package com.gyj.Test.Other;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 给你两个集合，要求{A} + {B}。 注：同一个集合中不会有两个相同的元素。
 * 每组输入数据分为三行,第一行有两个数字n,m(0 ≤ n,m ≤ 10000)，分别表示集合A和集合B的元素个数。
 * 后两行分别表示集合A和集合B。每个元素为不超过int范围的整数,每个元素之间有个空格隔开。
 * <p>
 * 输出描述:
 * 针对每组数据输出一行数据，表示合并后的集合，要求从小到大输出，每个元素之间有一个空格隔开,行末无空格。
 * 输入
 * 3 3
 * 1 3 5
 * 2 4 6
 * 输出
 * 1 2 3 4 5 6
 * Created by Gao on 2018/4/2.
 */
public class TwoCollectionsBing {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        TreeSet<Integer> set = new TreeSet<Integer>();
        while (in.hasNext()) {
            int m = in.nextInt();
            int n = in.nextInt();
            for (int i = 1; i <= m + n; i++) {
                set.add(in.nextInt());
            }

            /*Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                System.out.print(it.next());
                if (it.hasNext())
                    System.out.print(" ");
            }*/

           /* StringBuilder sb = new StringBuilder();
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                sb.append(" ");
            }
            //去掉最后的一个空格
            sb.delete(sb.length() - 1, sb.length());
            System.out.println(sb.toString());*/


            for (int i : set) {
                System.out.print(i + " ");
            }

        }
    }
}
