package com.keeko.master.service;

import com.keeko.common.ResultResponse;
import com.keeko.master.entity.SysUser;

/**
 * 用户相关
 *
 * @author chensq
 */
public interface ISysUserService {

    /**
     * 根据账号查询用户信息
     *
     * @param username 账号
     */
    SysUser selectByLoginName(String username);
    /**
     * 根据id创建token
     *
     * @param id 用户id
     */
    ResultResponse createToken(String id);
    /**
     * 登出
     *
     * @param id 用户id
     */
    void logout(String id);
}
