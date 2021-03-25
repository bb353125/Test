package com.keeko.redis.config;

/**
 * 返回页面对象 code200表示成功 500表示失败
 */
public class ResultResponse {

    private String code;
    private boolean success;
    private String msg;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public ResultResponse() {
        super();
    }

    public ResultResponse(boolean success, String msg) {
        super();
        if (success) {
            this.code = "200";
        } else {
            this.code = "500";
        }

        this.success = success;
        this.msg = msg;
    }

    public ResultResponse(boolean success, Object data) {
        super();
        if (success) {
            this.code = "200";
        } else {
            this.code = "500";
        }
        this.success = success;
        this.data = data;
    }

    public ResultResponse(String c, String msg) {
        super();
        this.code = c;
        this.success = false;
        this.msg = msg;
    }

}
