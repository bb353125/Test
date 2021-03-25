package com.keeko.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.keeko.config.StaticConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@Slf4j
public class AliyunOSSUtil {

    public static String uploadImage(InputStream is, String id, String extension) {
        String endpoint = StaticConstant.OSS_END_POINT;
        String accessKeyId = StaticConstant.OSS_ACCESS_KEY_ID;
        String accessKeySecret = StaticConstant.OSS_ACCESS_KEY_SECRET;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String baseUrl = "http://zt-pic.oss-cn-beijing.aliyuncs.com/";
        String bucketName = "zt-pic";
        String fileName = "cr/tmp/" + id + "." + extension;
        try {
            client.putObject(bucketName, fileName, is);
            return baseUrl + fileName;
        } catch (OSSException oe) {
            System.out.println(oe.getMessage());
        } catch (ClientException ce) {
            System.out.println(ce.getMessage());
        } finally {
            client.shutdown();
        }
        return null;
    }

    public static String uploadMedia(InputStream is, String id, String extension) {
        String endpoint = StaticConstant.OSS_END_POINT;
        String accessKeyId = StaticConstant.OSS_ACCESS_KEY_ID;
        String accessKeySecret = StaticConstant.OSS_ACCESS_KEY_SECRET;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String baseUrl = "http://zt-media.oss-cn-beijing.aliyuncs.com/";
        String bucketName = "zt-media";
        String fileName = "cr/tmp/" + id + "." + extension;

        try {
            client.putObject(bucketName, fileName, is);
            return baseUrl + fileName;
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            client.shutdown();
        }
        return null;
    }

    /**
     * 发送短信校验码
     *
     * @param phone 手机号
     */
    public static SmsSingleSenderResult sendSmsCode(final String phone) throws HTTPException, IOException {
        //短信应用SDK AppID 1400开头
        int appid = 1400214620;
        //短信应用SDK AppKey
        String appkey = "c5427155380e1abeb5c5a98d76adcfbd";
        // NOTE: 这里是短信模板ID需要在短信控制台中申请
        int templateId = 343085;
        //设置信息标头，如【测试】
        String smsSign = "测试";
        //有效时长
        String activetime = "5";
        //初始化单发
        SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
        //随机生成6位的验证码
        String smsCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            smsCode += random.nextInt(10);
        }
        String[] params = {"", smsCode, activetime};
        //smsCode 这个就是验证码
        EhcacheUtil.getInstance().put("CONSTANT", phone, smsCode);
        // 签名参数未提供或者为空时，会使用默认签名发送短信，这里的13800138000是为用户输入的手机号码
        return singleSender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
    }

}
