package com.keeko.redis.controller;

import com.keeko.redis.config.ResultResponse;
import com.keeko.redis.lock.RedisLockAnnotation;
import com.keeko.redis.lock.RedisLockTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestRedisLockController {

    @GetMapping("/testRedisLock")
    @RedisLockAnnotation(typeEnum = RedisLockTypeEnum.ONE, lockTime = 3)
    public ResultResponse testRedisLock(@RequestParam("userId") Long userId) {
        try {
            System.out.println("睡眠执行前");
            Thread.sleep(10000);
            System.out.println("睡眠执行后");
        } catch (Exception e) {
            // log error
            System.out.println("异常");
        }
        return null;
    }
}
