package com.gyj.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Gao on 2017/12/12.
 */
public class DateUtils {

    /**
     * 获取当前时间的最近天数
     * @param days
     * @return
     * @throws ParseException
     */
    public static String getStatetime(int days) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -days);
        java.util.Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
}
