package com.keeko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableScheduling  //开启定时任务
//@MapperScan("com.keeko.admin.dao") //1: 扫描包 单个数据库时使用
//@ComponentScan(basePackages= {"com.keeko.admin"}) //2: 分布式时使用
//@ComponentScan(excludeFilters ={@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.zhuyin.appApi.controller.*")}) //排除不要扫描指定的包
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
