package com.keeko.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import java.util.Random;

/**
 * 发送短信校验
 *
 * @author chensq
 */
public class SendSmsUtil {
    /**
     * 发送短信校验码
     *
     * @param phone 手机号
     */
    public static String sendSmsCode(final String phone) throws Exception {
        //腾讯短信应用的 SDK AppID
        int appid = 1400214620;
        //腾讯云短信中的 App Key
        String appkey = "c5427155380e1abeb5c5a98d76adcfbd";
        // NOTE: 这里是短信模板ID需要在短信控制台中申请
        int templateId = 343085;
        //设置信息标头，如【腾讯云】
        String smsSign = "消毒柜";
        String activetime = "5";
        //初始化单发
        SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
        //随机生成6位的验证码
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        int j = 6;
        for (int i = 0; i < j; i++) {
            str.append(random.nextInt(10));
        }
        String[] params = {"", str.toString(), activetime};
        //普通单发
        // 签名参数未提供或者为空时，会使用默认签名发送短信，这里的13800138000是为用户输入的手机号码
        SmsSingleSenderResult result = singleSender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
        return str.toString();
    }

}
