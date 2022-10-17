package com.eyes.eyesspace.common.tool.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HomeTypeContext {
    BLOG(0, "博客"),

    SHUOSHUO(1, "说说");

    private final Integer type;

    private final String description;
}
