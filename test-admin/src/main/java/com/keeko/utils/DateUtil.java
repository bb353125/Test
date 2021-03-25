package com.keeko.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 日期格式转String
     *
     * @param date 需要转的日期
     * @param pattern 需要的格式 如: yyyy-MM-dd HH:mm:ss
     * @return 字符串格式日期
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * String 转 日期格式
     *
     * @param time 需要转的日期
     * @param pattern 需要的格式 如: yyyy-MM-dd HH:mm:ss
     * @return date格式日期
     */
    public static Date parse(String time, String pattern) {
        if (StringUtils.isNotEmpty(time)){
            try {
                return new SimpleDateFormat(pattern).parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2019-01-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        System.out.println("昨天：" + simpleDateFormat.format(calendar.getTime()));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        System.out.println(year);
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        System.out.println(month);
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);

    }

}
