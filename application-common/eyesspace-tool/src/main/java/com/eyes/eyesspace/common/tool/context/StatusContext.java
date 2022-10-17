package com.eyes.eyesspace.common.tool.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusContext {
    PUBLIC(0, "公开"),

    PRIVATE(1, "私有"),

    DELETE(2, "删除");

    private final Integer status;

    private final String description;
}
