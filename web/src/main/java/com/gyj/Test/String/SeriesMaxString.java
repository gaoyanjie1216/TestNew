package com.gyj.Test.String;

/**
 * 读入一个字符串str，输出字符串str中的连续最长的数字串, 个测试输入包含1个测试用例，一个字符串str，长度不超过255。
 * 输入 abcd12345ed125ss123456789，输出123456789
 * Created by Gao on 2018/4/4.
 */
public class SeriesMaxString {

    /**
     * 法一自己想出来的最初的想法
     */
    private static String seriesMaxString(String str) {

        StringBuffer sb = new StringBuffer();
        sb.append(str.charAt(0));

        for (int i = 1; i < str.length(); i++) {
            int flag = 0;
            //Character.isDigit()判断一个字符是否是数字，是返回true，不是则返回false
            if ((str.charAt(i) >= '0' && str.charAt(i) <= '9') && !Character.isDigit(str.charAt(i - 1))) {
                sb.append(",");
            }

            if ((str.charAt(i - 1) >= '0' && str.charAt(i - 1) <= '9') && !Character.isDigit(str.charAt(i))) {
                sb.append(",");
                flag = 1;
                sb.append(str.charAt(i));
            }
            if (flag != 1) {
                sb.append(str.charAt(i));
            }
        }

        //System.out.println(sb.toString());
        String[] split = sb.toString().split(",");
        int maxLength = 0;
        String maxStr = "";
        for (int i = 0; i < split.length; i++) {
            if (split[i].toCharArray()[0] >= '0' && split[i].toCharArray()[0] <= '9') {
                if (split[i].length() > maxLength) {
                    maxLength = split[i].length();
                    maxStr = split[i];
                }
            }
        }
        System.out.print("最长的数字串是：" + maxStr + "  长度为：" + maxLength + "  ");

        return maxStr;
    }


    /**
     * 参考的解法，时间短
     */
    private static String seriesMaxString1(String str) {

        int max = 0, count = 0, end = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                count++;
                if (count > max) {
                    max = count;
                    end = i;
                }
            } else {
                count = 0;
            }
        }
        String substring = str.substring(end - max + 1, end + 1);
        System.out.print("最长的数字串是：" + substring + "  长度为：" + max + "  ");

        return substring;
    }


    public static void main(String[] args) {

        String str = "12432412hhjhjh412342412341423fggf3244 f 3r ";

        long startTime = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            seriesMaxString(str);
        }
        long endTime = System.nanoTime();
        System.out.println("seriesMaxString运行时间:" + (endTime - startTime) / 1000);


        long startTime1 = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            seriesMaxString1(str);
        }
        long endTime1 = System.nanoTime();
        System.out.println("seriesMaxString1运行时间:" + (endTime1 - startTime1) / 1000);
    }
}

/**
 * str="8568sfgdf12sasfasf348aa345353453453%%%%&**&*53453534564g56546yyrgdfgdfgdgdsgdfgy464stt56dd789"
 * 最长的数字串是：345353453453  长度为：12  seriesMaxString运行时间:303
 * 最长的数字串是：345353453453  长度为：12  seriesMaxString1运行时间:28
 */
