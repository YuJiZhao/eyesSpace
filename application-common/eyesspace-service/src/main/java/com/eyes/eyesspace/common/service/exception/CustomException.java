package com.eyes.eyesspace.common.service.exception;

import lombok.ToString;
import com.eyes.eyesspace.common.tool.result.ResultCode;

@ToString
public class CustomException extends Exception{
    protected Integer errorCode;

    protected String errorMsg;

    protected String errorTrueMsg;

    public CustomException() {
        this.errorCode = ResultCode.FAILURE.getCode();
        this.errorMsg = ResultCode.FAILURE.getMessage();
        this.errorTrueMsg = ResultCode.FAILURE.getMessage();
    }

    public CustomException(String errorMsg) {
        this.errorCode = ResultCode.FAILURE.getCode();
        this.errorMsg = errorMsg;
        this.errorTrueMsg = errorMsg;
    }

    public CustomException(String errorMsg, String errorTrueMsg) {
        this.errorCode = ResultCode.FAILURE.getCode();
        this.errorMsg = errorMsg;
        this.errorTrueMsg = errorTrueMsg;
    }

    public CustomException(ResultCode resultCode) {
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
        this.errorTrueMsg = resultCode.getMessage();
    }

    public CustomException(ResultCode resultCode, String errorTrueMsg) {
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
        this.errorTrueMsg = errorTrueMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorTrueMsg() {
        return errorTrueMsg;
    }
}
