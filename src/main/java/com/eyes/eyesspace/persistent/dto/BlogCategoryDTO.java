package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BlogCategoryDTO {
    @ApiModelProperty("分类名")
    private String category;

    @ApiModelProperty("当前分类博客总数")
    private Integer num;
}
