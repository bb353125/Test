package com.keeko.config;

import com.keeko.common.ResultResponse;
import com.keeko.common.exception.AdminException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 所有异常 跳转至错误页面
 *
 * @author chensq
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截JWT token错误异常
     */
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResultResponse malformedJwtException() {
        return new ResultResponse("403", "请重新登录!");
    }

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(AdminException.class)
    @ResponseBody
    public ResultResponse adminException(AdminException e) {
        e.printStackTrace();
        System.out.println("异常");
        return new ResultResponse(false, e.getMessage());
    }

    /**
     * 拦截自定义异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse exception(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        //保存日志信息
        System.out.println(str);
        System.out.println("异常");
        System.out.println(e.getMessage());
        return new ResultResponse(false, e.getMessage());
    }
}
