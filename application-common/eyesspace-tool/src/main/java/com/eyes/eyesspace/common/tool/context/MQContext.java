package com.eyes.eyesspace.common.tool.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQContext {
    EMAIL_LOGIN_VERI("email.loginVeri", "登录邮箱验证");

    private final String queue;

    private final String description;
}
