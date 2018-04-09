package com.gyj.Test.Array;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 在Java中如何高效的判断数组中是否包含某个元素
 * Created by Gao on 2018/3/20.
 */
public class IsArrayContainsOne {

    //使用List
    public static boolean useList(String[] a, String value) {
        //System.out.println(Arrays.asList(a));
        return Arrays.asList(a).contains(value);
    }

    //使用Set
    public static boolean useSet(String[] a, String value) {
        Set<String> set = new HashSet<String>(Arrays.asList(a));
        //System.out.println(set);[CD, BC, EF, DE, AB, JK] 转换成数组
        return set.contains(value);
    }

    //使用循环判断
    public static boolean useLoop(String[] a, String value) {
        for (String s : a) {
            if (s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    //查找有序数组中是否包含某个值的用法, 二分查找
   /* public static int binarySearch(Object[] a, Object key) {
        return binarySearch0(a, 0, a.length, key);
    }
    // Like public version, but without range checks.
    private static int binarySearch0(Object[] a, int fromIndex, int toIndex, Object key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable midVal = (Comparable)a[mid];
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }*/
    public static boolean useArraysBinarySearch(String[] a, String value) {
        int b = Arrays.binarySearch(a, value);
        if (b > 0)
            return true;
        else
            return false;
    }

    //使用ArrayUtils
   /* public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }*/
    public static boolean useArrayUtils(String[] a, String value) {
        return ArrayUtils.contains(a, value);
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"CD", "BC", "EF", "DE", "AB", "JK"};

        //java中System.nanoTime()返回的是纳秒，nanoTime而返回的可能是任意时间，甚至可能是负数, nanoTime提供了纳秒级别的精度，但实际上获得的值可能没有精确到纳秒
        //java中System.currentTimeMillis()返回的毫秒，这个毫秒其实就是自1970年1月1日0时起的毫秒数，Date()其实就是相当于Date(System.currentTimeMillis());
        //new Date()所做的事情其实就是调用了System.currentTimeMillis()。如果仅仅是需要或者毫秒数，那么完全可以使用System.currentTimeMillis()去代替new Date()，效率上会高一点。
        //use list
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useList(arr, "A");
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("useList:" + duration / 1000000);

        //use set
        long startTime2 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useSet(arr, "A");
        }
        long endTime2 = System.nanoTime();
        long duration2 = endTime2 - startTime2;
        System.out.println("useSet:" + duration2 / 1000000);

        //use loop
        long startTime3 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useLoop(arr, "A");
        }
        long endTime3 = System.nanoTime();
        long duration3 = endTime3 - startTime3;
        System.out.println("useLoop:" + duration3 / 1000000);

        //use Arrays.binarySearch()
        long startTime4 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useArraysBinarySearch(arr, "A");
        }
        long endTime4 = System.nanoTime();
        long duration4 = endTime4 - startTime4;
        System.out.println("useArraysBinarySearch:" + duration4 / 1000000);

        //use useArrayUtils()
        long startTime5 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            useArrayUtils(arr, "A");
        }
        long endTime5 = System.nanoTime();
        long duration5 = endTime5 - startTime5;
        System.out.println("useArrayUtils:" + duration5 / 1000000);
    }
}
/**
 *  useList:7
    useSet:49
    useLoop:3
    useArraysBinarySearch:4
    useArrayUtils:38
    显然，使用一个简单的循环方法比使用任何集合都更加高效。许多开发人员为了方便，都使用第一种方法，但是他的效率也相对较低。
    因为将数组压入Collection类型中，首先要将数组元素遍历一遍，然后再使用集合类做其他操作。
    如果使用Arrays.binarySearch()方法，数组必须是已排序的。由于上面的数组并没有进行排序，所以该方法不可使用。
    实际上，如果你需要借助数组或者集合类高效地检查数组中是否包含特定值，一个已排序的列表或树可以做到时间复杂度为O(log(n))，hashset可以达到O(1)相比较之下，
    我更倾向于使用ArrayUtils工具类来进行一些合数祖相关的操作。毕竟他可以让我少写很多代码（因为自己写代码难免有Bug，
    毕竟apache提供的开源工具类库都是经过无数开发者考验过的），而且，效率上也并不低太多。
 */
