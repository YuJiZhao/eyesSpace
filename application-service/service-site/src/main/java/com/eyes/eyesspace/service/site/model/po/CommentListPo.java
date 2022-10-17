package com.eyes.eyesspace.service.site.model.po;

import lombok.Data;

@Data
public class CommentListPo {
    private Integer id;

    private Integer uid;

    private String comment;

    private String createTime;

    private Integer comments;

    private String replyList;

    private Integer status;
}
