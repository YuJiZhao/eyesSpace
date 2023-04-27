package com.eyes.eyesspace.sync.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CommentChildDTO {
    @ApiModelProperty("评论id")
    private Integer id;

    @JsonIgnore
    private Long uid;

    @ApiModelProperty("回复对象评论id")
    private Integer replyId;

    @ApiModelProperty("回复对象昵称")
    private String replyName;

    @ApiModelProperty("回复对象头像")
    private String avatar;

    @ApiModelProperty("评论者昵称")
    private String name;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("是否是当前用户评论")
    private Boolean owner;

    @ApiModelProperty("评论数")
    private Integer comments;
}
