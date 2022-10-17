package com.eyes.eyesspace.common.feign.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CommentChildDto {
    @ApiModelProperty("评论id")
    private Integer id;

    @ApiModelProperty("回复对象id")
    private Integer replyId;

    @ApiModelProperty("回复对象昵称")
    private String replyName;

    @ApiModelProperty("评论人头像")
    private String avatar;

    @ApiModelProperty("评论人昵称")
    private String name;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("是否是请求用户发的评论")
    private Boolean owner;

    @ApiModelProperty("被评论数")
    private Integer comments;
}
