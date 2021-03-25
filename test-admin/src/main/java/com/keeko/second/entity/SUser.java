package com.keeko.second.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户相关
 *
 * @author chensq
 */
@Getter
@Setter
public class SUser {
    /**
     * 主键id
     */
    //为了解决前端js只能解析Long类型16位
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    /**
     * 用户昵称
     */
    private String wechatName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * openid
     */
    private String openId;
    /**
     * 用户头像
     */
    private String headPortrait;

}
