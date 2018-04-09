package com.gyj.Test.String;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个英文字符串,请写一段代码找出这个字符串中首先出现三次的那个英文字符。
 * 输入数据一个字符串，包括字母, 数字等。输出首先出现三次的那个英文字符
 * Created by Gao on 2018/3/30.
 */
public class FirstShowThreeTimesChar {

    private static char firstShowThreeTimesChar(String str) {

        if (str == null || str.length() == 0) {
            return '\0';
        }

        char result = str.charAt(0);
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                    if (map.get(c) == 3) {
                        result = c;
                        break;
                    }
                } else {
                    map.put(c, 1);
                }
            }
        }

        //注意下面这样写不仅会耗时，因为统计的是所有字符的次数，而且有问题，因为依次统计的是所有字符的次数，不是首先出现三次的字符
        /*for (char c1 : str.toCharArray()) {
            if ((c1 >= 'a' && c1 <= 'z') || (c1 >= 'A' && c1 <= 'Z')) {
                if (map.get(c1) >= 3) {
                    result = c1;
                    break;
                }
            }
        }*/
        System.out.println("首先出现三次的字符是：" + result);

        return result;
    }

    //以空间换时间
    private static char firstShowThreeTimesChar1(String str) {

        if (str == null || str.length() == 0) {
            return '\0';
        }

        char result = str.charAt(0);
        int count[] = new int[256];
        for (char c : str.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count[c]++;
            }
            if (count[c] == 3) {
                result = c;
                break;
            }
        }
        System.out.println("首先出现三次的字符是：" + result);

        return result;
    }


    public static void main(String[] args) {

        String str = "lnZ[)#8l6L9~?tN=t:iT_45>'(6YY\"[5PV'#:s,Q0Zc79if]Z-r]yKs3}e3s`$evqGViV>bP9dzH2ziMT]y!xz+R." +
                "M=Z:2{G'8de1sXn3UEc\")hd8|%#VQ[6=S-&DwFKt$*gSu'3|h'H>cB\"rq(\\vM9HO:.h_n'~L]5+:+j)GSUDYN\\5`Ss$C9H-}ObPEuL$Dpsnm9,]" +
                "vktC#f{fC\"eSuCv\\-QNb|cFP^EsS2S(C8's'&q&w~eY%78VL8@Ey\"On$e?(uo\\Uv-cki@lqXM(Fg!#K`p$dsY5G(Kg7ZsS&tr;*x{+E/-^.AMxa" +
                "[?Hz,d\\8O?{P-f9.CW{M[V'BGj,B??0^R[mFAiM^*O:/zn.8N6CUg\"I))yYgi9yb3K^hfU1g\"W\\;gmIyb+tK!,p|R}p\\HZVt,\\@*8__G=ZZIg>" +
                "2m6N6kL([7p,D^Pa5-6~XBPiTTjr{x\\4@bg,4AO5MHsJ@wWt\\8kUf>5ec:K0qgW,d,YO?+*w/SnFQw+Q3\"w-wFwi*{.fbld-r7|)XBc[&]_#@z*";

        String str1 = "Have you ever gone shopping and";

        firstShowThreeTimesChar(str);
        firstShowThreeTimesChar1(str);
    }
}
