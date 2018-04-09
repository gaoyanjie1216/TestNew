package com.gyj.Test.Sort;

import java.util.Arrays;

/**
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，
 * 而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 * 分而治之(divide - conquer);每个递归过程涉及三个步骤
 * 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
 * 第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
 * 第三, 合并: 合并两个排好序的子序列,生成排序结果.
 * 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
 * <p>
 * 　归并排序是稳定排序，它也是一种十分高效的排序，能利用完全二叉树特性的排序一般性能都不会太差。java中Arrays.sort()采用了一种名为TimSort的排序算法，
 * 就是归并排序的优化版本。从上文的图中可看出，每次合并操作的平均时间复杂度为O(n)，而完全二叉树的深度为|log2n|。总的平均时间复杂度为O(nlogn)。而且，
 * 归并排序的最好，最坏，平均时间复杂度均为O(nlogn)。
 * Created by Gao on 2018/3/23.
 */
public class MergeSort {

    private static void merge(int[] a, int mid, int low, int high) {

        int i = low, k = low, j = mid + 1;
        int[] temp = new int[a.length];

        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }

        while (low <= high) {
            a[low] = temp[low++];
        }
    }

    private static void sort(int a[], int low, int high) {

        //这里low不能有等于high的时候, 当划分到low和high均为0的时候，会进入死循环出错，Exception java.lang.StackOverflowError
        if (low < high) {
            int mid = (low + high) / 2;
            sort(a, low, mid);
            sort(a, mid + 1, high);
            merge(a, mid, low, high);
        }

    }

    public static void main(String[] args) {

        int[] a = {1, 7, 3, 8, 2, 11, 56, 9, 6};
        int[] b = {1, 7, 3, 8, 2, 11, 56, 9, 6};

        sort(a, 0, a.length - 1);
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        //java中Arrays.sort()采用了一种名为TimSort的排序算法，就是归并排序的优化版本
        Arrays.sort(b);
        for (int i : b) {
            System.out.print(i + " ");
        }
    }

}
