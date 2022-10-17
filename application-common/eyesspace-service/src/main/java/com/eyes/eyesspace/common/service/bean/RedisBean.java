package com.eyes.eyesspace.common.service.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisBean {
    private LocalDateTime expireTime;
    private Object data;
}