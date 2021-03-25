package com.keeko.utils;

import com.keeko.master.entity.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 时间 相关
 *
 * @author chensq
 */
public class TimeUtil {

    /**
     * 测试用
     */
    public static void test(int time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间加10分钟
        long endTime = System.currentTimeMillis() + 6000000;
        String format = simpleDateFormat.format(endTime);
        System.out.println(format);
        //获取当前时间月份减1
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, -1);
        Date date = ca.getTime();
        //加一个小时
        ca.add(Calendar.HOUR_OF_DAY, 1);
        //加10分钟
        ca.add(Calendar.MINUTE, 10);
        //当前时间减去一年，即一年前的时间
        ca.add(Calendar.YEAR, -1);
        //1天
        ca.add(Calendar.DAY_OF_MONTH, 1);
    }

    /**
     * 计算出时间
     * @param time 时间戳如 1分2秒 60000 + 2000
     * @return 字符串时间 如: 01:40:02
     */
    public static String getTimeString(long time) {
        long hour = time / (60 * 60 * 1000);
        long min = (time / (60 * 1000)) - hour * 60;
        long sec = time / 1000 - min * 60 - hour * 60 * 60;
        String h = hour + "";
        if (hour == 0) {
            h = "00";
        } else if (h.length() == 1) {
            h = "0" + h;
        }
        String m = min + "";
        if (min == 0) {
            m = "00";
        } else if (m.length() == 1) {
            m = "0" + m;
        }
        String s = sec + "";
        if (sec == 0) {
            s = "00";
        } else if (s.length() == 1) {
            s = "0" + s;
        }
        return h + ":" + m + ":" + s;
    }


    /**
     * 根据时间排序（其他排序如根据id排序也类似）
     *
     * @param list
     */
    private static List<User> ListSort(List<User> list) {
        //用Collections这个工具类传list进来排序
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
                   /* Date dt1 = o1.getCreateTime();
                    Date dt2 = o2.getCreateTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;//小的放前面
                    }else {
                        return -1;
                    }*/

            }
        });
        //Collections.reverse(list);
        return list;
    }


    /**
     * 获取距离结束时间还有多少天
     */
    public static long getDay(Date endTime) {
        Date startTime = new Date();
        long timediffer = startTime.getTime() - endTime.getTime();
        return (int) timediffer / (1000 * 60 * 60 * 24); //小时少24 分钟再少个60
    }


}
