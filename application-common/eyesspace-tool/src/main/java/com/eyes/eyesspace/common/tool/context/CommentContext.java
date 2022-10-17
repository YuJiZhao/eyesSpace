package com.eyes.eyesspace.common.tool.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentContext {
    BLOG(0, "博客评论"),

    SHUOSHUO(1, "说说评论"),

    MUSIC(2, "音乐评论"),

    VIDEO(3, "视频评论"),

    SITE(4,  "全站留言");

    private final Integer type;

    private final String description;
}
