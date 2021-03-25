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
public class User {
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
     * 手机号
     */
    private String agencyId;
    /**
     * 性别 0:女 1:男
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

    private List<User> usetList;


    /**
     * 用户id
     */
    private String memberId;
    /**
     * 天数
     */
    private int days;

    /** 创建时间 */
    private Date createTime;
}
