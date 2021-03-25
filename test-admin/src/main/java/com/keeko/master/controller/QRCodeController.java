package com.keeko.master.controller;

import com.keeko.utils.QRCodeUtil1;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 二维码相关
 *
 * @author chensq
 */
public class QRCodeController {

    /**
     * 参考: https://www.cnblogs.com/DurantSimpson/p/11510646.html
     * 下载单张二维码图片png格式
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String fileName = "fileName123.png";
        response.setHeader("content-disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.setHeader("content-type", "image/png");
        BufferedImage image = QRCodeUtil1.createImage("http://agent.xmzhuying.com/zyjhAgent/vip/test", "标题随便输入");
        ImageIO.write(image, QRCodeUtil1.FORMAT_NAME, response.getOutputStream());
    }


}
