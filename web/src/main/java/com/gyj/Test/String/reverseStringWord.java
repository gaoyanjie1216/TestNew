package com.gyj.Test.String;

/**
 * 把一个句子中的单词进行反转，例如，“how are you”,反转后为“you are how”
 * Created by Gao on 2018/3/12.
 */
public class reverseStringWord {

    public static void swap(char a[], int front, int end) {
        while (front < end) {
            char temp = a[end];
            a[end] = a[front];
            a[front] = temp;
            front++;
            end--;
        }
    }

    public String swapWords(String s) {
        char a[] = s.toCharArray();
        swap(a, 0, a.length - 1);  //对整个字符串进行字符串反转
        int begin = 0;
        for (int i = 0; i < a.length-1; i++) {  //对每个单词进行字符串反转
            if (a[i] == ' ') {
                swap(a, begin, i - 1);
                begin = i + 1;
            }
        }

        swap(a, begin, a.length - 1);
        return new String(a);
    }


    private static void swapWords1(String str) {

        String[] a = str.split(" ");
        for (int i = a.length - 1; i >= 0; i--) {
            /*System.out.print(a[i]);
            if (i != 0)                  //没有这个if 输出 oldyouarehow
                System.out.print(" ");
            */
            if (i == 0) {
                System.out.print(a[i]);
            } else {
                System.out.print(a[i] + " ");
            }
        }
    }

    private static String swapWords2(String str) {

        String[] a = str.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = a.length - 1; i >= 0; i--) {
            sb.append(a[i]);
            if (i != 0) {
                sb.append(" ");
            }
        }
        System.out.print(sb.toString());

        return sb.toString();
    }

    private static void swapWords3(String str) {

        String[] string = str.split(" ");
        for (int i = 0; i < string.length; i++) {
            System.out.print(string[string.length - i - 1] + " ");
        }
    }


    public static void main(String[] args) {
        String str = "how are you old ";

        //代码输出 old you are how
        System.out.println(new reverseStringWord().swapWords(str));

        swapWords1(str);
        System.out.println();

        swapWords2(str);
        System.out.println();

        swapWords3(str);

    }
}
