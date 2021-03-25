package com.keeko.config;

import com.keeko.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 初始化配置类 只在项目启动时加载一次
 *
 * @author chensq
 */
@Component
public class StaticConstant implements InitializingBean {
    /**
     * 开始时间
     */
    public static final String BEGIN_TIME = " 00:00:00";
    /**
     * 结束时间
     */
    public static final String END_TIME = " 23:59:59";
    /**
     * 租赁订单号前缀
     */
    public static final String ORDER_NO_PREFIX = "ZL";
    /**
     * 测试订单号前缀
     */
    public static final String TEST_ORDER_NO_PREFIX = "TEST";

    @Value("${oss.file.endpoint}")
    private String oss_file_endpoint;

    @Value("${oss.file.keyid}")
    private String oss_file_keyid;

    @Value("${oss.file.keysecret}")
    private String oss_file_keysecret;

    @Value("${book-admin.basePath}")
    private String book_admin_basePath;

    @Value("${book-admin.pushPath}")
    private String book_admin_pushPath;

    @Value("${book-admin.wechatBasePath}")
    private String book_admin_wechatBasePath;

    public static String OSS_END_POINT;
    public static String OSS_ACCESS_KEY_ID;
    public static String OSS_ACCESS_KEY_SECRET;
    public static String bookAdminBasePath;
    public static String bookAdminPushPath;
    public static String wechatBasePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_END_POINT = oss_file_endpoint;
        OSS_ACCESS_KEY_ID = oss_file_keyid;
        OSS_ACCESS_KEY_SECRET = oss_file_keysecret;
        bookAdminBasePath = book_admin_basePath;
        bookAdminPushPath = book_admin_pushPath;
        wechatBasePath = book_admin_wechatBasePath;
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
