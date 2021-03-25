package com.keeko.redis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户相关
 *
 * @author chensq
 */
@Data
public class RUser implements Serializable {
    /**
     * 主键id
     */
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
