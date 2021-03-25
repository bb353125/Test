package com.keeko.rabbitMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * EnableScheduling //开启缓存功能
 * PropertySource //扫描指定文件
 * @author chensq
 */
@SpringBootApplication
@EnableScheduling //开启缓存功能
@PropertySource("classpath:application.yml")
public class RabbitMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

}

