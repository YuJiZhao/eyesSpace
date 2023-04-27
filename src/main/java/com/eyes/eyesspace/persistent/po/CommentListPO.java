package com.eyes.eyesspace.persistent.po;

import lombok.Data;

@Data
public class CommentListPO {
    private Integer id;

    private Long uid;

    private String comment;

    private String createTime;

    private Integer comments;

    private String replyList;

    private Integer status;
}
