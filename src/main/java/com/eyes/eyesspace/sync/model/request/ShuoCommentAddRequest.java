package com.eyes.eyesspace.sync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShuoCommentAddRequest {
    @JsonIgnore
    private Integer id;

    private Integer uid;

    @NotNull(message = "对象内容id不能为空")
    private String objectId;

    private Integer landlord;

    private Integer replyId;

    @NotNull(message = "原始评论内容不能为空")
    private String originalComment;

    @NotNull(message = "评论内容不能为空")
    private String comment;
}