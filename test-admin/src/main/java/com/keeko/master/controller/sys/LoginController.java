package com.keeko.master.controller.sys;

import com.keeko.common.ResultResponse;
import com.keeko.master.entity.SysUser;
import com.keeko.master.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * 登录相关
 *
 * @author chensq
 */
@RestController
@RequestMapping("/sys")
public class LoginController {
    @Autowired
    private ISysUserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResultResponse login(@RequestBody SysUser vo) throws IOException {
        //用户信息
        SysUser user = userService.selectByLoginName(vo.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(vo.getPassword(), vo.getSalt()).toHex())) {
            return new ResultResponse(false, "账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            return new ResultResponse(false, "账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        return userService.createToken(user.getId());

        //做个登录日志
        //sysUserLoginService.update(user.getUserId());
        //return r;
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public ResultResponse logout() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        userService.logout(user.getId());
        return new ResultResponse(true, "操作成功!");
    }

}
