package com.eyes.eyesspace.service.article.module.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class BlogInfoDto {
    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客摘要")
    private String summary;

    @ApiModelProperty("博客内容")
    private String content;

    @ApiModelProperty("博客分类")
    private String category;

    @ApiModelProperty("博客标签")
    private List<String> labels;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("是否点赞")
    private boolean existLike;

    @ApiModelProperty("收藏量")
    private Integer collections;

    @ApiModelProperty("是否收藏")
    private boolean existCollect;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("博客字数")
    private Integer words;

    @ApiModelProperty("博客状态")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
