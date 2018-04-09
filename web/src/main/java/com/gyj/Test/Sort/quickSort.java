package com.gyj.Test.Sort;

/**
 * 快速排序基本思想是：通过一趟排序将待排序记录分割成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，
 * 然后再依次对前后两部分的记录进行快速排序，递归该过程，以达到整个序列有序的目的。
 * 快速排序的时间复杂度为O(NlogN).
 *
 * Created by Gao on 2018/3/21.
 */
public class quickSort {

    /**
     * 法一
     *
     * @param a
     * @param low
     * @param high
     */
    private static void sort(int[] a, int low, int high) {
        int i = low, j = high;
        int index = a[i];      //a[low]即a[i]就是第一个坑, 这个地方换成 int index = a[low]结果是一样的

        if (low >= high) {    //不能少，少了会出现堆栈溢出的错误
            return;
        }

        while (i < j) {
            while (i < j && a[j] >= index) {   //从右向左来找小于index的数来填a[i], 只要右边的比index大j就向左移动, 直到找到比index小的a[j]
                j--;
            }
            if (i < j) {
                a[i++] = a[j];  //等价于a[i]=a[j]; i++; 将a[j]填到a[i]中，a[j]就形成了一个新的坑
            }

            while (i < j && a[i] < index) {   //从左向右来找大于等于index的数来填a[j], 从左边找到比index大的数, 然后交换位置
                i++;
            }
            if (i < j) {
                a[j--] = a[i];   //将a[i]填到a[j]中，a[i]就形成了一个新坑
            }
        }

        a[i] = index;     //退出时，i等于j，将index填入这个坑
        sort(a, low, i - 1);
        sort(a, i + 1, high);
    }


    private static void quickSort(int[] a) {
        sort(a, 0, a.length - 1);
    }


    /**
     * 法二
     *
     * @param a
     * @param low
     * @param high
     * @return
     */
    public static int partition(int[] a, int low, int high) {
        //固定的切分方式
        int key = a[low];
        while (low < high) {
            while (a[high] >= key && high > low) {   //从后半部分向前扫描
                high--;
            }

            a[low] = a[high];
            while (a[low] <= key && high > low) {    //从前半部分向后扫描
                low++;
            }
            a[high] = a[low];
        }
        a[high] = key;

        return high;
    }

    public static void sort1(int[] array, int low, int high) {

        if (low >= high) {
            return;
        }

        int index = partition(array, low, high);
        sort1(array, low, index - 1);
        sort1(array, index + 1, high);
    }


    public static void main(String[] args) {
        int a[] = {6, 3, 7, 4, 1};
        int b[] = {1, 3, 4, 5, 8, 6, 32, 9, 4, 5, 10, 5};
        sort(a, 0, a.length - 1);
        sort1(b, 0, b.length - 1);

        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println(" ");
        for (int i : b) {
            System.out.print(i + " ");
        }
    }
}
