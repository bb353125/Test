package com.keeko.master.controller;

import com.keeko.common.ResultResponse;
import com.keeko.utils.QRCodeMax2;
import com.keeko.utils.SendEmailUtils2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/agent/email")
public class EmailController {

    //@Value("${zhuying.domain}")
    private String domain;

    @PostMapping("/send")
    @ResponseBody
    public ResultResponse uploadImage(Long vipMemberId, String no, String address1, String address, String email) {
        String address2 = "";
        String address3 = "";
        if (address.length() > 10){
            address1 = address.substring(0, 10);
            if (address.length() <= 20){
                address2 = address.substring(10);
            } else {
                address2 = address.substring(10, 20);
            }
        }
        if (address.length() > 20){
            address3 = address.substring(20);
        }
        String imgPrefix = "./static/";
        String centre = imgPrefix + "centre.png";
        String centreBak = imgPrefix + "中号.png";
        String max = imgPrefix + "max.png";
        String maxBak = imgPrefix + "大号.png";
        String min = imgPrefix + "min.png";
        String minBak = imgPrefix + "小号.png";

        File centreFileBegin = new File(centre);//背景图片
        File centreFileEnd = new File(centreBak);//生成图片位置
        String url = "https://" + domain + "/shareReg.html?abcex=" + vipMemberId;//二维码链接
        String note = "网点编号: No." + no;//文字描述
        String tui = "地址: " + address1;//文字描述
        //生成图地址,背景图地址,二维码宽度,二维码高度,二维码识别地址,文字描述1,文字描述2,文字大小,图片x轴方向,图片y轴方向,文字1||2xy轴方向
        QRCodeMax2.CreatQRCode(centreFileEnd, centreFileBegin, 430, 430, url, note, tui, 100,
                1690, 2870, 870, 2970, 870, 3070,
                address2, 1190, 3170, address3, 1190, 3270);//200

        File maxFileBegin = new File(max);//背景图片
        File maxBakFileEnd = new File(maxBak);//生成图片位置
        //生成图地址,背景图地址,二维码宽度,二维码高度,二维码识别地址,文字描述1,文字描述2,文字大小,图片x轴方向,图片y轴方向,文字1||2xy轴方向
        QRCodeMax2.CreatQRCode(maxBakFileEnd, maxFileBegin, 1250, 1250, url, note, tui, 260,
                4790, 8100, 2400, 8400, 2400, 8700,
                address2, 3220, 9000, address3, 3220, 9300);//300

        File minFileBegin = new File(min);//背景图片
        File minBakFileEnd = new File(minBak);//生成图片位置
        //生成图地址,背景图地址,二维码宽度,二维码高度,二维码识别地址,文字描述1,文字描述2,文字大小,图片x轴方向,图片y轴方向,文字1||2xy轴方向
        QRCodeMax2.CreatQRCode(minBakFileEnd, minFileBegin, 315, 315, url, note, tui, 70,
                1190, 2023, 600, 2100, 600, 2180,
                address2, 820, 2260, address3, 820, 2325);//200

        File affix1 = new File(centreBak);
        File affix2 = new File(maxBak);
        File affix3 = new File(minBak);
        List<File> attachments = new ArrayList<>();
        attachments.add(affix1);
        attachments.add(affix2);
        attachments.add(affix3);

        String theme1 = "【彩票助赢软件】彩票店推广海报";
        String theme2 = "<p>您好！</p>" +
                "<p style='white-space: pre;'>      附件为【彩票助赢软件】彩票店推广海报，请查收！</p>" +
                "<p></p> "+
                "<p></p>" +
                "<p></p>" +
                "<p>================</p>" +
                "<p>助赢科技有限公司</p>" +
                "<p>联系电话：400 6876276</p>" +
                "<p>地址：厦门市集美区软件园三期B15栋1501</p>";
        //发送邮件
        SendEmailUtils2 se = new SendEmailUtils2();
        se.doSendHtmlEmail(theme1, theme2, email, attachments);

        return new ResultResponse(true, "请查收邮件!");
    }


}











