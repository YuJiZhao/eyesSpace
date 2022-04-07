package com.eyes.eyesblog.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    //成功提示码
    SUCCESS(200, "success"),

    //自定义失败信息
    FAILURE(400, "fail"),

    //通用错误码 50001~50099
    PROGRAM_INSIDE_EXCEPTION(50001, "program exception"),
    REQUEST_PARAM_ERROR(50002, "parameter error");

    // 用户模块错误码 50100~50199

    // 资源模块错误码 50200~50299

    // 外部请求模块错误码 50300~50399

    private final Integer code;
    private final String message;
}
