package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    PUBLIC(0, "公开"),

    PRIVATE(1, "私有"),

    DELETE(2, "删除");

    private final Integer status;

    private final String description;
}
