package com.keeko.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreateIdUtils {

    /**
     * 返回固定16位的字母数字混编的字符串。
     */
    public static String getId() {
        int length = 16;
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

}
