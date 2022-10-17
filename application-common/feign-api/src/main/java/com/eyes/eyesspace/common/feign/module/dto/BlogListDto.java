package com.eyes.eyesspace.common.feign.module.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.eyes.eyesspace.common.feign.module.bean.HomeListBean;

import java.util.Date;

@Data
@ApiModel
public class BlogListDto implements HomeListBean {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客摘要")
    private String summary;

    @ApiModelProperty("博客分类")
    private String category;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("收藏量")
    private Integer collections;

    @ApiModelProperty("博客字数")
    private Integer words;

    @ApiModelProperty("博客状态")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
