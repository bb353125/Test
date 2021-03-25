package com.keeko.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author niunafei
 * @function
 * @email niunafei0315@163.com
 * @date 2020/6/5  2:19 PM
 */
public class PhoneUtils {

    /**
     * ^ 匹配输入字符串开始的位置
     * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
     * $ 匹配输入字符串结尾的位置
     */
    private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆号码
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {

        Matcher m = HK_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是正整数的方法
     */
    public static boolean isNumeric(String string) {
        return NUM_PATTERN.matcher(string).matches();
    }

    /**
     * 校验邮箱
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 校验password 6至20位字母混编
     */
    public static boolean isPassword(String password){
        if (null==password || "".equals(password)){
            return false;
        }
        return password.matches("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$");
    }
    /**
     * 校验password 6至20位大小写字母混编
     */
    public static boolean checkPassword(String password){
        if (null==password || "".equals(password)){
            return false;
        }
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,20}$");
    }
}
