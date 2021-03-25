package com.keeko.second.service.impl;

import com.keeko.second.dao.TestMapper;
import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关
 *
 * @author chensq
 */
@Service
public class TestServiceImpl implements ITestService {

    private final TestMapper testMapper;

    @Autowired
    public TestServiceImpl(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SUser selectById(String id) {
        return testMapper.selectById(id);
    }
}
