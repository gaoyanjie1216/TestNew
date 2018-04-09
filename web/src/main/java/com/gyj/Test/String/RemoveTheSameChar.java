package com.gyj.Test.String;

/**
 * 输入两个字符串，从第一字符串中删除第二个字符串中所有的字符。例如，输入”They are students.”和”aeiou”，
 * 则删除之后的第一个字符串变成”Thy r stdnts.”
 * Created by Gao on 2018/3/31.
 */
public class RemoveTheSameChar {

    private static String removeTheSameChar(String str1, String str2) {

        StringBuffer sb = new StringBuffer();
        for (char c : str1.toCharArray()) {
            if (str2.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        System.out.println(sb.toString());

        return sb.toString();
    }

    private static String removeTheSameChar1(String str1, String str2) {

        for (int i = 0; i < str2.length(); i++) {
            str1 = str1.replaceAll(str2.substring(i, i + 1), "");
        }
        System.out.println(str1);

        return str1;
    }

    public static void main(String[] args) {

        String str1 = "They are students.";
        String str2 = "aeiou";

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            removeTheSameChar(str1, str2);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("countZero1:" + duration / 1000);  // countZero1:35924


        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            removeTheSameChar1(str1, str2);
        }
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.println("countZero1:" + duration1 / 1000); // countZero1:64284
    }
}
