package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel
public class ShuoListVO {
    @ApiModelProperty("加密id")
    private String id;

    @ApiModelProperty("说说内容")
    private String content;

    @ApiModelProperty("说说图片列表")
    private List<String> picList;

    @ApiModelProperty("阅读量")
    private Integer views;

    @ApiModelProperty("评论数")
    private Integer comments;

    @ApiModelProperty("说说状态")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @ApiModelProperty("发布时间")
    private String createTime;
}
