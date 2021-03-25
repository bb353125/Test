package com.keeko.rabbitMQ.controller;

import com.keeko.rabbitMQ.config.ResultResponse;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class RabbitMQController {


    @Autowired
    private Channel channel;


    public static void main(String[] args) throws Exception{
        new Receiver("A",50).work();
        new Receiver("B",100).work();
        new Receiver("C",500).work();

    }

    /**
     * 测试
     */
    @GetMapping("/info")
    @ResponseBody
    public ResultResponse appInfo(String name, String i) {






        return new ResultResponse(true, "");
    }

    public void test() {
        System.out.println(Thread.currentThread().getName() + " 已连接正在等待消息...");
        String key;
        key = "lock:" + "id";
        boolean flag;
        try {
            //尝试加锁
            do {
                flag = true;//stringRedisTemplate.opsForValue().setIfAbsent(key, Thread.currentThread().getName());
            } while (!flag);
            //stringRedisTemplate.expire(key, 3600, TimeUnit.SECONDS);
            System.out.println("根据key 保存3600秒");
            System.out.println(Thread.currentThread().getName() + " 获取redis锁成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            if (key != null) {
                //删除根据 key
                //stringRedisTemplate.delete(key);
                System.out.println("删除根据 key");
                System.out.println(Thread.currentThread().getName() + " 处理完成释放锁成功");
            }
        }
    }

}
