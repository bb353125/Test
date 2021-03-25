package com.keeko.common.exception;

import com.keeko.common.enums.ExceptionEnum;

/**
 * 自定义异常
 *
 * @author chensq
 */
public class CustomException extends RuntimeException {

    private String code;

    private String message;


    public CustomException() {
    }

    public CustomException(ExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMsg();
    }

    public CustomException(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
