package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentNoticeTypeEnum {
    COMMENT(0, "评论"),

    REPLY(1, "回复");

    private final Integer type;

    private final String description;
}
