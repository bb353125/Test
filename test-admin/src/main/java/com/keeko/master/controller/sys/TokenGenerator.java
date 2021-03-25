package com.keeko.master.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.keeko.common.exception.AdminException;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

/**
 * 生成token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 14:41
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            // todo throw new AdminException("生成Token失败", e);
            return "";
        }
    }

    public static void main(String[] args) {
        String id = "1350319134955941123";
        String phoneNum = "18206079949";
        String secret = "123hji2ih2g4j2n2";
        JSONObject json = new JSONObject();
        json.put("userId", id);
        json.put("phoneNum", phoneNum);
        json.put("secret", secret);

        String token = Base64.getEncoder().encodeToString(json.toString().getBytes());
        System.out.println(token);
        String result = new String(Base64.getDecoder().decode(token));
        System.out.println(result);
        JSONObject obj = (JSONObject) JSON.parse(result);
        if (obj == null) {
            System.out.println("...");
        }
        String id1 = obj.getString("userId");
        String phoneNum1 = obj.getString("phoneNum");
        System.out.println(id1);
        System.out.println(phoneNum1);

    }
}
