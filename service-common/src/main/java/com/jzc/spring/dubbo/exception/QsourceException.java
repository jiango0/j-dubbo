package com.jzc.spring.dubbo.exception;


import com.jzc.spring.dubbo.constant.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;

public class QsourceException extends IllegalArgumentException {

    private String code;
    private String msg;
    private String errorMsg;

    public QsourceException(ExceptionEnum exceptionEnum) {
        if (exceptionEnum != null) {
            this.code = exceptionEnum.getCode();
            this.msg = exceptionEnum.getMsg();
            this.errorMsg = exceptionEnum.getErrorMsg();
        }
    }

    public QsourceException(String code, String msg, String errorMsg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public static QsourceException busiError(String errorMsg) {
        return busiError(null, errorMsg);
    }

    public static QsourceException busiError(String msg, String errorMsg) {
        if (StringUtils.isBlank(msg)) {
            msg = ExceptionEnum.BusiException.getMsg();
        }
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = ExceptionEnum.BusiException.getErrorMsg();
        }
        return new QsourceException(ExceptionEnum.BusiException.getCode(), msg, errorMsg);
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
}
