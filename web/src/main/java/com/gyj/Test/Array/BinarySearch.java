package com.gyj.Test.Array;

/**
 * Created by Gao on 2018/3/20.
 * 对于一个有序数组，我们通常采用二分查找的方式来定位某一元素，请编写二分查找的算法，在数组中查找指定元素。
 * 算法思想：又叫折半查找，要求待查找的序列有序。每次取中间位置的值与待查关键字比较，如果中间位置的值比待查关键字大，
 * 则在前半部分循环这个查找的过程，如果中间位置的值比待查关键字小，则在后半部分循环这个查找的过程。直到查找到了为止，否则序列中没有待查的关键字。
 *
 * 优点是比较次数少，查找速度快，平均性能好；
 其缺点是要求待查表为有序表，且插入删除困难。
 因此，折半查找方法适用于不经常变动而查找频繁的有序列表。

 * 时间复杂度为 O(logN)
 * 采用的是分治策略
 最坏的情况下两种方式时间复杂度一样：O(log2 N)
 最好情况下为O（1）

 空间复杂度
 算法的空间复杂度并不是计算实际占用的空间，而是计算整个算法的辅助空间单元的个数
 非递归方式：
 由于辅助空间是常数级别的所以：
 空间复杂度是O(1);
 递归方式：
 递归的次数和深度都是log2 N,每次所需要的辅助空间都是常数级别的：
 空间复杂度：O(log2N )
 */
public class BinarySearch {

    /**
     * 非递归实现
     *
     * @param a
     * @param key
     * @return
     */
    private static int getPosition(int[] a, int key) {

        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (key < a[mid]) {
                high = mid - 1;
            } else if (key > a[mid]) {
                low = mid + 1;
            } else {
                //返回数组中第一个重复的数字
                while (mid > 0 && a[mid - 1] == a[mid])
                    mid--;
                //返回数组中最后一个重复的数字
               /* while (mid > 0 && a[mid + 1] == a[mid])
                    mid++;*/
                return mid;
            }
        }

        return -1;
    }

    /**
     * 递归实现
     *
     * @param a
     * @param low
     * @param high
     * @param key
     * @return
     */
    private static int getPosition1(int[] a, int low, int high, int key) {

        if (low <= high) {
            int mid = (low + high) / 2;
            if (key == a[mid]) {
               /* while (mid > 0 && a[mid + 1] == a[mid]) {
                    mid++;
                }*/
                while (mid > 0 && a[mid - 1] == a[mid]) {
                    mid--;
                }
                return mid;
            } else if (key < a[mid]) {
                getPosition1(a, low, mid - 1, key);
            } else {
                getPosition1(a, mid + 1, high, key);
            }
        }

        return -1;
    }


    private static int recursionBinarySearch(int[] a, int low, int high, int key) {

        if (key < a[low] || key > a[high] || low > high) {
            return -1;
        }

        int mid = (low + high) / 2;
        if (a[mid] > key) {
            //比关键字大则关键字在左区域
            return recursionBinarySearch(a, low, mid - 1, key);
        } else if (a[mid] < key) {
            //比关键字小则关键字在右区域
            return recursionBinarySearch(a, mid + 1, high, key);
        } else {
            /* while (mid > 0 && a[mid + 1] == a[mid]) {
                    mid++;
                }*/
            while (mid > 0 && a[mid - 1] == a[mid]) {
                mid--;
            }
            return mid;
        }
    }

    public static void main(String[] args) {

        //int a[] = { 1, 1, 2, 2, 2, 2, 2, 8, 9, 10 };
        int[] a = new int[]{1, 2, 2, 2, 2, 2, 2, 8, 9, 10, 11};
        long startTime = System.nanoTime();
        int position = 0;
        for (int i = 0; i < 100000; i++) {
            position = getPosition(a, 2);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("非递归getPosition:" + duration / 100000);
        System.out.println("返回的位置是: " + position + "  对应的数组元素是: " + a[position]);

        long startTime1 = System.nanoTime();
        int position1 = 0;
        for (int i = 0; i < 100000; i++) {
            position1 = getPosition1(a, 0, a.length - 1, 2);
        }
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.println("递归getPosition1:" + duration1 / 100000);
        System.out.println("返回的位置是: " + position1 + "  对应的数组元素是: " + a[position1]);

        long startTime2 = System.nanoTime();
        int position2 = 0;
        for (int i = 0; i < 100000; i++) {
            position2 = recursionBinarySearch(a, 0, a.length - 1, 2);
        }
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("递归recursionBinarySearch:" + duration2 / 100000);
        System.out.println("返回的位置是: " + position2 + "  对应的数组元素是: " + a[position2]);
    }

}

/**
 *非递归getPosition:42
 返回的位置是: 1  对应的数组元素是: 2
 递归getPosition1:35
 返回的位置是: 1  对应的数组元素是: 2
 递归recursionBinarySearch:34
 返回的位置是: 1  对应的数组元素是: 2

 递归用的时间还相对少一些
 */
