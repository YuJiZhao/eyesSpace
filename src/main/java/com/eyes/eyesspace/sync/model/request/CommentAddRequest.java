package com.eyes.eyesspace.sync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CommentAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty(hidden = true)
    private Long uid;

    @ApiModelProperty("对象id")
    @NotNull(message = "对象内容id不能为空")
    private Integer objectId;

    @ApiModelProperty("楼主帖子id")
    private Integer landlord;

    @ApiModelProperty("回复对象帖子id")
    private Integer replyId;

    @ApiModelProperty("原始评论内容")
    @NotNull(message = "原始评论内容不能为空")
    private String originalComment;

    @ApiModelProperty("处理后的评论内容")
    @NotNull(message = "评论内容不能为空")
    private String comment;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String url;
}
