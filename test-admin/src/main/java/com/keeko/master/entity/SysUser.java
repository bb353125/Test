package com.keeko.master.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 用户相关
 *
 * @author chensq
 */
@Getter
@Setter
public class SysUser {
    /**
     * 主键id
     */
    private String id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户token
     */
    private String token;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 过期时间
     */
    private Integer expire;
    /**
     * 更新时间过期时间
     */
    private Date expireTime;


}
