package com.keeko.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * 百度天气相关
 * http://lbsyun.baidu.com/apiconsole/key#/home  配置应用 获取ak:WCvM0jwmAi5N3ZmWmTadIUWjSCBzX4vb
 *
 * @author chensq
 */
public class BaiduWeather {

    public static void main(String[] args) {
        //测试
        System.out.println(getWeather("郑州").toString());
        System.out.println("123");
    }

    //获取天气信息
    public static String getWeather(String city) {
        String result = "";
        try {
            result = BaiduWeather.getXmlCode(URLEncoder.encode(city, "utf-8"));  //设置输入城市的编码，以满足百度天气api需要
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getXmlCode(String city) throws UnsupportedEncodingException {
        //String requestUrl = "http://api.map.baidu.com/telematics/v3/weather?location="+city+"&output=json&ak=WCvM0jwmAi5N3ZmWmTadIUWjSCBzX4vb";
        String requestUrl = "http://api.map.baidu.com/weather/v1/?district_id=222405&data_type=all&ak=WCvM0jwmAi5N3ZmWmTadIUWjSCBzX4vb"; //GET请求
        StringBuffer buffer = null;
        try {
            // 建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            // 获取输入流
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 读取返回结果
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
