package com.keeko.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码 相关
 *
 * @author chensq
 */
public class QRCodeUtil {

    /**
     * 生成二维码 直接给二维码图片
     *
     * @param ticket 微信ticket
     */
    public static void sendQRCode(String ticket, HttpServletResponse response){
        OutputStream os;
        try {
            os = response.getOutputStream();
            String ticketURL = String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", ticket);
            URL url = new URL(ticketURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8 * 1000);
            conn.connect();
            InputStream in = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建二维码指定跳转url
     *
     * @author chensq
     */
    public static void createQRCode(String url, HttpServletResponse response) throws IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>(16);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        ServletOutputStream stream = response.getOutputStream();
        // 图像宽度
        int width = 200;
        // 图像高度
        int height = 200;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            // 输出图像
            MatrixToImageWriter.writeToStream(bitMatrix, "jpeg", stream);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 创建谷歌二维码 只有参数 页面展示
     *
     * @param mac 设备号
     */
    public static void google(String mac, HttpServletResponse response) {
        //二维码图片的宽度
        int width = 300;
        //二维码图片的高度
        int height = 300;
        //二维码格式
        String format = "jpg";
        //定义二维码内容参数
        HashMap hints = new HashMap();
        //设置字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置容错等级，在这里我们使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置边框距
        hints.put(EncodeHintType.MARGIN, 2);

        BufferedInputStream in = null;
        OutputStream os = null;
        //生成二维码
        try {
            //指定二维码内容
            BitMatrix bitMatrix = new MultiFormatWriter().encode(mac, BarcodeFormat.QR_CODE, width, height, hints);
            // 如果没有files文件夹，则创建
            String filePath = "/static/google/123.jpg";
            Path path = Paths.get(filePath);
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(filePath));
            }
            File file = new File(filePath);
            //生成二维码
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);
            in = new BufferedInputStream(new FileInputStream(file));
            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建谷歌二维码 只有参数 直接下载
     *
     * @param mac 设备号
     */
    public static void createGoogleQRCode(String mac, HttpServletResponse response) {
        String fileName = mac + ".jpg";
        mac = macAppendColon(mac);
        //二维码图片的宽度
        int width = 500;
        //二维码图片的高度
        int height = 500;
        //二维码格式
        String format = "jpg";
        //定义二维码内容参数
        HashMap hints = new HashMap();
        //设置字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置容错等级，在这里我们使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置边框距
        hints.put(EncodeHintType.MARGIN, 2);

        BufferedInputStream in = null;
        OutputStream os = null;
        //生成二维码
        try {
            //指定二维码内容
            BitMatrix bitMatrix = new MultiFormatWriter().encode(mac, BarcodeFormat.QR_CODE, width, height, hints);
            // 如果没有files文件夹，则创建
            String filePath = "/static/google/123.jpg";
            Path path = Paths.get(filePath);
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(filePath));
            }
            File file = new File(filePath);
            //生成二维码
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);
            in = new BufferedInputStream(new FileInputStream(file));
            //当文件名不是英文名的时候，最好使用url解码器去编码一下，
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //将响应的类型设置为图片
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设备编号添加冒号 从第二位开始插入冒号，结尾不加
     *
     * @param mac 设备编号
     * @return 有加冒号后的设备编号
     */
    public static String macAppendColon(String mac) {
        if (StringUtils.isEmpty(mac)) {
            return "";
        }
        String colon = ":";
        if (mac.contains(colon)) {
            return mac;
        }
        int max = 15;
        if (mac.length() == max){
            mac = mac.substring(3, mac.length());
        }
        int three = 3;
        for (int i = 2; i < mac.length(); i = i + three) {
            mac = mac.substring(0, i) + ":" + mac.substring(i, mac.length());
        }
        return mac;
    }

}
