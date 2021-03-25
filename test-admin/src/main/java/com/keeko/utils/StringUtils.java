package com.keeko.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断字符串是否为空
 *
 * @author chensq
 */
public class StringUtils {

	public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

	public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 返回固定16位的字母数字混编的字符串。
     */
    public static String getId() {
        int length = 16;
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    public static void main(String[] args) throws ParseException {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        long t = 1589437563470L; //2020-05-14 14:26:03
        String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
        System.out.println(result2);
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result2);
        System.out.println(date.toString());
    }



    /**
     * 2019-06-01 三大运营商号码均可验证(不含卫星通信1349)
     *
     * @param phone 手机号
     */
    public static boolean isMobile(String phone) {
        Pattern p;
        Matcher m;
        boolean b = false;
        // 验证手机号
        String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
        if (isNotEmpty(phone)) {
            p = Pattern.compile(s2);
            m = p.matcher(phone);
            b = m.matches();
        }
        return b;
    }
}