package com.eyes.eyesspace.common.tool.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码类
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "success"),

    FAILURE(400, "fail"),

    AUTHENTICATION_ERROR(1000, "权限错误，拒绝访问"),

    AUTHENTICATION_ERROR_ILLEGAL_JWT(1001, "权限错误，拒绝访问"),

    AUTHENTICATION_EXPIRED(1002, "身份过期，请重新登录"),

    ACCOUNT_FREEZING(1003, "账号被冻结"),

    GATEWAY_EXCEPTION(1004, "请求出错"),

    NONE_HTTP_SERVLET_REQUEST(1005, "请求出错"),

    GATEWAY_BYPASSED(1006, "权限异常"),

    NO_TIMES(2000, "今日次数已用尽");

    private final Integer code;

    private final String message;
}