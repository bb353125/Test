package com.keeko.master.dao;

import com.keeko.master.entity.CharacterCondition;
import com.keeko.master.entity.CharacterGroupCondition;
import com.keeko.master.entity.SysUser;
import com.keeko.master.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试相关
 *
 * @author chensq
 */
@Repository
public interface SysUserMapper {

    /**
     * 根据账号查询用户信息
     *
     * @param username 账号
     */
    SysUser selectByLoginName(String username);

    /**
     * 更新用户信息
     *
     * @param user 入参
     * @return 影响行数
     */
    int updateById(SysUser user);

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    SysUser selectById(String id);

    /**
     * 新增 sys_user_token 用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    int insert(SysUser sysUser);
}
