package com.jzc.spring.dubbo.web;

import java.io.Serializable;

public class DubboResponse<T> implements Serializable {

    private boolean success;

    private String code;

    private String msg;

    private String errorMsg;

    private T data;

    public DubboResponse(boolean success, String code, String msg, String errorMsg, T t) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
        this.data = t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
