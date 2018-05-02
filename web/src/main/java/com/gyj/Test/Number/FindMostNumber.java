package com.gyj.Test.Number;

import java.util.*;

/**
 * 数组中找出出现次数最多的数字
 * Created by Gao on 2018/3/28.
 */
public class FindMostNumber {

    /**
     * 思路：最简单的办法就是采用两层for循环去遍历，时间复杂度为O(n2)，其次可以
     * 先用快速排序将数组排序，然后再找次数最多且最大的数，时间复杂度O(NLogN)，
     * 第三种方法可以采用HashMap，这种方式时间复杂度为O(N)，但是需要空间复杂度O(N)
     */
    //这种情况只是支持最多一个出现次数最多的数字，假设有2个或者2个以上的数字出现的次数一样多
    //使用HashMap，每个Entry的key存放数组中的数字，value存放该数字出现的次数，首先遍历数组元素构造HashMap，
    //然后遍历每个Entry，找出最大value对应的key，即是出现次数最多的那个数。此算法的时间复杂度为O(n)
    private static int findMostNumber(int[] n) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : n) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        //得到value为maxCount的key，也就是数组中出现次数最多的数字
       /* Collection<Integer> count = map.values();
        int maxCount = Collections.max(count);
        int maxNum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (maxCount == entry.getValue()) {
                maxNum = entry.getKey();
            }
        }*/

        int maxNum1 = 0;
        int maxCount1 = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount1) {  // >= maxMun输出是最后一个出现次数最多的数字，如有多个出现次数一样的数字
                maxNum1 = entry.getKey();
                maxCount1 = entry.getValue();
            }
        }

        System.out.print("出现次数最多的第一个数字为" + maxNum1 + " ");
        System.out.println("该数一共出现了" + maxCount1 + "次");
        return maxNum1;
    }

    private static int mostAndMaxNumber(int[] a) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])) {
                map.put(a[i], map.get(a[i]) + 1);
            } else {
                map.put(a[i], 1);
            }
        }

        int count = -1;
        int max = Integer.MIN_VALUE;
        /*Iterator<Map.Entry<Integer, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            if (entry.getValue() > count || (entry.getValue() == count && entry.getKey() > max)) {
                max = entry.getKey();
                count = entry.getValue();
            }
        }*/
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= count) {
                max = entry.getKey();
                count = entry.getValue();
            }
        }

        System.out.println("出现次数最多的最后一个数字为" + max + " " + "该数一共出现了" + count + "次");
        return max;
    }

    /**
     * 以空间换时间，可以定义一个计数数组int count[256]，用来对数组中数字出现的次数进行计数（只能针对数组中数字的范围1~255），count数组中最大的元素对应的下标，
     * 即为出现次数最多的那个数。这是一种典型的空间换时间算法（所需数组空间的大小完全取决于数组中数字的大小）。一般情况下，除非内存空间足够大且数组中的数不是很大，
     * 否则一般不采用这种方法。
     *
     * @param a
     */
    public static int findMostNumber1(int[] a) {

        int[] count = new int[256];
        for (int i = 0; i < a.length; i++) {
            count[a[i]]++;
        }
        int maxCount = count[0];
        int maxNum = 0;
        for (int i = 1; i < count.length; i++) {
            if (count[i] >= maxCount) {  //有等于就是出现次数最多的最后一个数字
                maxCount = count[i];
                maxNum = i;
            }
        }
        /*for (int i = 0; i < count.length; i++) {
            if (count[i] == maxCount) {
                maxNum = i;
                //break;  出现次数最多的最后一个数字，加上break表示出现次数最多的第一个数字
            }
        }
*/
        System.out.println("出现次数最多的最后一个数字为" + maxNum + " " + "该数一共出现了" + maxCount + "次");
        return maxNum;
    }


    /**
     * 扩展：找出出现次数最多的一个或者多个（多个是两个或者两个以上出现次数最多且相同的数）
     *
     * @param n
     * @return
     */
    private static List mostAndMaxNumbe2(int[] n) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : n) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        int maxCount = 0;
        List<Integer> list = new ArrayList<>();
       /* for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }*/

        maxCount = Collections.max(map.values());

        for (int i : map.keySet()) {
            if (map.get(i).equals(maxCount)) {
                list.add(i);
            }
        }

        System.out.print("出现次数最多的数字为:");
        for (int j : list) {
            System.out.print(j + " ");
        }
        System.out.println("一共出现了" + maxCount + "次");

        return list;
    }


    /**
     * @param n
     * @return
     */
    private static List mostAndMaxNumbe3(int[] n) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : n) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.addAll(map.values());

        Collections.sort(list);
        for (int key : map.keySet()) {
            if (map.get(key).equals(list.get(list.size() - 1))) {
                list1.add(key);
            }
        }

        System.out.print("出现次数最多的数字为:");
        for (int k : list1) {
            System.out.print(k + " ");
        }
        System.out.println("一共出现了" + list.get(list.size() - 1) + "次");

        return list;
    }


    public static void main(String[] args) {

        int[] a = {1, 2, 2, 2, 3, 3, 3, 5, 5, 5, 5, 6, 6, 6, 6};

        findMostNumber(a);

        mostAndMaxNumber(a);

        findMostNumber1(a);

        mostAndMaxNumbe2(a);

        mostAndMaxNumbe3(a);

    }
}

/**
 出现次数最多的第一个数字为5 该数一共出现了4次
 出现次数最多的最后一个数字为6 该数一共出现了4次
 出现次数最多的最后一个数字为6 该数一共出现了4次
 出现次数最多的数字为:5 6 一共出现了4次
 出现次数最多的数字为:5 6 一共出现了4次
 */
