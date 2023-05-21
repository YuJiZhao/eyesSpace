package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HomeTypeEnum {
    BLOG(0, "博客"),

    SHUOSHUO(1, "说说"),

    VERSION(2, "版本");

    private final Integer type;

    private final String description;
}
