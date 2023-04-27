package com.eyes.eyesspace.persistent.po;

import lombok.Data;

@Data
public class CommentChildPO {
    private Integer id;

    private Long uid;

    private Integer landlord;

    private Integer replyId;

    private String comment;

    private Integer comments;

    private String createTime;

    private Integer status;
}
