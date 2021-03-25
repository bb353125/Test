package com.keeko.second.service.impl;

import com.keeko.second.dao.TestMapper;
import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import com.keeko.second.service.ITestService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关
 *
 * @author chensq
 */
@Service
@Primary //两种方法 1:优先注入  2: @Autowired @Qualifier(value = "TestServiceImpl1")
public class TestServiceImpl1 implements ITestService1 {

    private final TestMapper testMapper;

    @Autowired
    public TestServiceImpl1(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SUser selectById(String id) {
        SUser sUser = new SUser();
        sUser.setId(id+"1111");
        System.out.println(id+"1111");
        return sUser;
    }
}
