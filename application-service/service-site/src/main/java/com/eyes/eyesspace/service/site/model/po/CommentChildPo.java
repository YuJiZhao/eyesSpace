package com.eyes.eyesspace.service.site.model.po;

import lombok.Data;

@Data
public class CommentChildPo {
    private Integer id;

    private Integer uid;

    private Integer landlord;

    private Integer replyId;

    private String comment;

    private Integer comments;

    private String createTime;

    private Integer status;
}
