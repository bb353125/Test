package com.keeko.master.service.impl;

import com.keeko.common.ResultResponse;
import com.keeko.master.controller.sys.TokenGenerator;
import com.keeko.master.dao.SysUserMapper;
import com.keeko.master.entity.SysUser;
import com.keeko.master.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户相关
 *
 * @author chensq
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    private final SysUserMapper sysUserMapper;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param username 账号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser selectByLoginName(String username) {
        return sysUserMapper.selectByLoginName(username);
    }

    /**
     * 根据id创建token
     *
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultResponse createToken(String id) {
        //生成一个token
        String token =TokenGenerator.generateValue(); //"d59b71b94e53c7af4771014077e1423d";

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setToken(token);
            sysUser.setUpdateTime(now);
            sysUser.setExpireTime(expireTime);

            //保存token
            sysUserMapper.insert(sysUser);
        }else{
            sysUser.setToken(sysUser.getToken());
            //sysUser.setToken(token); /*一个用户只能一个人登录，由于业务需求变动了！*/
            sysUser.setUpdateTime(now);
            sysUser.setExpireTime(expireTime);
            //更新token
            sysUserMapper.updateById(sysUser);
        }
        sysUser.setExpire(EXPIRE);
        return new ResultResponse(true, sysUser);
    }

    /**
     * 登出
     *
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(String id) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setToken(token);
        sysUser.setUpdateTime(new Date());
        sysUserMapper.updateById(sysUser);
    }
}
