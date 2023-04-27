package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.CommentChildDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CommentListVO {
    @ApiModelProperty("评论id")
    private Integer id;

    @JsonIgnore
    private Long uid;

    @ApiModelProperty("评论用户头像")
    private String avatar;

    @ApiModelProperty("评论用户名称")
    private String name;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("是否是当前用户评论")
    private Boolean owner;

    @ApiModelProperty("评论数")
    private Integer comments;

    @ApiModelProperty("子评论")
    private List<CommentChildDTO> children;
}