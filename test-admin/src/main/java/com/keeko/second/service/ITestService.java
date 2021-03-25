package com.keeko.second.service;

import com.keeko.second.entity.SUser;

/**
 * 用户相关
 *
 * @author chensq
 */
public interface ITestService {

    SUser selectById(String id);
}
