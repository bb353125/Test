package com.keeko.second.service.impl;

import com.keeko.second.dao.TestMapper;
import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import com.keeko.second.service.ITestService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关
 *
 * @author chensq
 */
@Service
public class TestServiceImpl3 implements ITestService1 {

    private final TestMapper testMapper;

    @Autowired
    public TestServiceImpl3(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SUser selectById(String id) {
        SUser sUser = new SUser();
        sUser.setId(id+"3333");
        System.out.println(id+"333");
        return sUser;
    }
}
