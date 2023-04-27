package com.eyes.eyesspace.sync.common.result;

import com.eyes.eyesTools.common.result.ResultCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码类
 * @author eyesYeager
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements ResultCodeInterface {
    SUCCESS(200, "success"),

    FAILURE(400, "fail"),

    // 1000 - 2000: 程序异常
    NONE_HTTP_SERVLET_REQUEST(1000, "HttpServletRequest获取失败"),

    // 2001 - 3000: 业务异常
    NO_TIMES(2001, "今日播放次数已达上限");

    private final Integer code;

    private final String message;
}