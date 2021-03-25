package com.keeko.master.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
/**
 * 上传文件相关
 *
 * @author chensq
 */
@Controller
public class File1Controller {

    /**
     * 下载图片
     *
     * @param imgUrl 图片地址
     */
    @GetMapping("/test1/uploading")
    @ResponseBody
    public void uploadImage(String imgUrl, HttpServletResponse response) {
        //String urlList = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546100952419&di=4b9466f689800898b761d411c0eed2d2&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F14ce36d3d539b600d3924a1feb50352ac65cb73e.jpg";
        InputStream ins = null;
        OutputStream out = null;
        try {
            String filename = "123.png";
            //当文件名不是英文名的时候，最好使用url解码器去编码一下，
            filename = URLEncoder.encode(filename, "UTF-8");
            //将响应的类型设置为图片”
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            //File file = new File(imgUrl);
            //FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(imgUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            out = response.getOutputStream();
            int len = 0;
            byte[] by = new byte[1024 * 10];
            while ((len = dataInputStream.read(by)) > 0) {
                out.write(by, 0, len);
            }
            out.close();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }





}
