package com.keeko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试相关
 *
 * @author chensq
 * https://www.cnblogs.com/betterboyz/p/8669879.html 参考
 */
@Controller
@RequestMapping("/test")
public class TestController {
    /**
     * 测试访问
     */
    @GetMapping("/index")
    public String exception() {
        System.out.println("请求成功!");
        return "index";
    }
    /**
     * 测试访问
     */
    @GetMapping("/send")
    public String send() {
        System.out.println("send请求成功!");
        return "send";
    }
    /**
     * 测试访问
     */
    @GetMapping("/receive")
    public String receive() {
        System.out.println("receive请求成功!");
        return "receive";
    }

}
