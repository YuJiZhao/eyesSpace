package com.eyes.eyesspace.common.feign.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CommentListDto {
    @ApiModelProperty("评论id")
    private Integer id;

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

    @ApiModelProperty("评论回复")
    private List<CommentChildDto> children;
}