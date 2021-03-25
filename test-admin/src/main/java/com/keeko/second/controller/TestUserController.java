package com.keeko.second.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.keeko.common.ResultResponse;
import com.keeko.common.annotation.BussinessLog;
import com.keeko.common.annotation.TokenAnnotation;
import com.keeko.common.dictMap.DictMap;
import com.keeko.master.entity.User;
import com.keeko.second.entity.SUser;
import com.keeko.second.service.ITestService;
import com.keeko.second.service.ITestService1;
import com.keeko.second.service.impl.TestServiceImpl3;
import com.keeko.utils.*;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 用户相关
 *
 * @author chensq
 */
@Controller
@RequestMapping("/user2")
public class TestUserController {

    private final ITestService testService;
    @Autowired
    private ITestService1 testService1;

    @Autowired
    public TestUserController(ITestService testService) {
        this.testService = testService;
    }

    /**
     * 测试多个实现类 优先执行哪个实现类
     * SessionHolderInterceptor
     */
    @GetMapping("/one")
    @ResponseBody
    public ResultResponse testone(HttpServletRequest request) {
        SUser sUser = testService1.selectById("TWO");
        return new ResultResponse(true, sUser);
    }

    /**
     * 测试注解
     */
    @PostMapping("/logAop")
    @ResponseBody
    @BussinessLog(value = "客户列表导出", key = "name", dict = DictMap.class,param = "#user")
    public ResultResponse logAop(@RequestBody User user) {
        System.out.println("请求成功!");
        return new ResultResponse(true, "");
    }

    /**
     * 测试Session和Ehcache缓存
     */
    @GetMapping("/user")
    @ResponseBody
    public ResultResponse createMenu(HttpServletRequest request) {
        //获取 request ?号后面所有参数
        String requestURL = request.getQueryString();
        System.out.println(requestURL);
        request.getSession().setAttribute("userId", "12345678");
        Object userId = request.getSession().getAttribute("userId");
        System.out.println("Obj=" + userId);
        //Ehcache缓存
        EhcacheUtil.getInstance().put("CONSTANT", "validataCode", "参数值12345678");
        String validataCode = (String) EhcacheUtil.getInstance().get("CONSTANT", "validataCode");
        System.out.println(validataCode);
        return new ResultResponse(true, requestURL);
    }

    /**
     * 测试注解
     */
    @GetMapping("/annotation")
    @ResponseBody
    @TokenAnnotation(value = "#token")
    public ResultResponse annotation(HttpServletRequest request, @RequestHeader(value = "Token") String token) {
        //获取 request ?号后面所有参数
        String requestURL = request.getQueryString();
        System.out.println(requestURL);
        request.getSession().setAttribute("userId", "456789");

        return new ResultResponse(true, requestURL);
    }

    /**
     * 根据id查询用户
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public ResultResponse appInfo(@PathVariable("id") String id, HttpServletRequest request) {
        SUser user = testService.selectById(id);
        return new ResultResponse(true, user);
    }

    /**
     * 测试发送验证码
     */
    @GetMapping("/smsCode")
    @ResponseBody
    public ResultResponse getSmsCode(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return new ResultResponse(true, "手机号不能为空");
        }
        //先去缓存中拿 若是拿得到则不需要重新发送
        String smsCode = (String) EhcacheUtil.getInstance().get("CONSTANT", phone);
        if (StringUtils.isNotEmpty(smsCode)) {
            return new ResultResponse(true, "验证码五分钟内有效");
        }
        try {
            SmsSingleSenderResult result = AliyunOSSUtil.sendSmsCode(phone);
            System.out.println(result);
        } catch (HTTPException | IOException e) {
            return new ResultResponse(false, "发送短信失败");
        }
        return new ResultResponse(true, "验证码已发送，请注意查收短信！");
    }

    /**
     * 校验前端传过来的验证码是否正确
     **/
    public ResultResponse checkSmsCode(final String phone, final String code) {
        String smsCode = (String) EhcacheUtil.getInstance().get("CONSTANT", phone);
        if (StringUtils.isEmpty(smsCode)) {
            return new ResultResponse(false, "验证码已失效，请重新获取");
        }
        if (!smsCode.equals(code)) {
            return new ResultResponse(false, "验证码输入错误，请仔细确认");
        }
        //使用过清除缓存中的验证码
        EhcacheUtil.getInstance().put("CONSTANT", phone, "");
        return new ResultResponse(true, "操作成功");
    }

}
