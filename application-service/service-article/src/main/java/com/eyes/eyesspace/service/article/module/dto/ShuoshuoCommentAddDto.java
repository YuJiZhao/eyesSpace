package com.eyes.eyesspace.service.article.module.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class ShuoshuoCommentAddDto {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty(hidden = true)
    private Integer uid;

    @ApiModelProperty("对象内容id")
    @NotNull(message = "对象内容id不能为空")
    private String objectId;

    @ApiModelProperty("楼主帖子id")
    private Integer landlord;

    @ApiModelProperty("回复评论id")
    private Integer replyId;

    @ApiModelProperty("原始评论")
    @NotNull(message = "原始评论内容不能为空")
    private String originalComment;

    @ApiModelProperty("评论内容")
    @NotNull(message = "评论内容不能为空")
    private String comment;
}