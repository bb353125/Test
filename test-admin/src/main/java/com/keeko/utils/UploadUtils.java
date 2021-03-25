package com.keeko.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * 上传文件工具类
 *
 * @author chensq
 */
public class UploadUtils {

    /**
     * 拼接json
     * @param result
     * @param message
     * @return
     */
    public static String makeJsonResponse(boolean result, String message,String coverAmount) {
        if (result) {
            return "{\"result\":\"success\",\"msg\":\"" + message + "\",\"coverAmount\":\"" + coverAmount + "\"}";
        }
        return "{\"result\":\"faild\",\"msg\":\"" + message + "\"}";
    }

    /**
     *ajax打印到前台
     * @param content
     * @param response
     */
    public static void writeToPage(String content, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(content);
        } catch (final IOException e) {
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * 获取配置文件
     * @param propsName  配置文件名字
     * @return
     */
    public static String getPropertieValue(String propsName, String key) {
        try {
            InputStream inputStream;
            final ClassLoader cl = UploadUtils.class.getClassLoader();
            if (cl != null) {
                inputStream = cl.getResourceAsStream(propsName);
            } else {
                inputStream = ClassLoader.getSystemResourceAsStream(propsName);
            }
            final Properties dbProps = new Properties();
            dbProps.load(inputStream);
            inputStream.close();
            return dbProps.getProperty(key);
        } catch (final Exception e) {
        }
        return "";
    }

    /**
     *验证文件大小
     * @param myfile
     * @param stream
     * @return
     */
    public static String validateImg(MultipartFile myfile, InputStream stream) {
        try {
            final long size = myfile.getSize();
            if (size > (1024 * 1024 * 50)) {
                return UploadUtils.makeJsonResponse(false, "error1","");
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 验证文件是否已经存在
     * @param file
     * @return
     */
    public static String validateExistOrNo(File file){
        if(file.exists()){
            return UploadUtils.makeJsonResponse(false, "error3","");
        }
        return "success";
    }

    /**
     *判断字符串
     * @param s
     * @return
     */
    public static boolean isNull(String s) {
        if ((s == null) || "".equals(s)) {
            return true;
        } else {
            return false;
        }
    }

}
