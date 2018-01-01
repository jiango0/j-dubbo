package com.jzc.spring.dubbo.web;


import com.jzc.spring.dubbo.constant.ExceptionEnum;
import com.jzc.spring.dubbo.exception.QsourceException;

public class ResponseModel {

    public static <T> DubboResponse<T> returnObjectSuccess(T t) {
        return new DubboResponse<>(true, "200", "success", "", t);
    }

    public static <T> DubboResponse<T> returnListSuccess(T t) {
        return new DubboResponse<>(true, "200", "success", "", t);
    }

    public static <T> DubboResponse<T> returnException(Exception e) {
        if (e instanceof QsourceException) {
            QsourceException qe = (QsourceException) e;
            return new DubboResponse<T>(false, qe.getCode(), qe.getMsg(), qe.getErrorMsg(), null);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException ll = (IllegalArgumentException) e;
            return new DubboResponse<T>(false,
                    ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getMsg(),
                    ll.getMessage(),
                    null);
        } else {
            return new DubboResponse<T>(false,
                    ExceptionEnum.Exception.getCode(),
                    ExceptionEnum.Exception.getMsg(),
                    ExceptionEnum.Exception.getErrorMsg(),
                    null);
        }
    }


}
