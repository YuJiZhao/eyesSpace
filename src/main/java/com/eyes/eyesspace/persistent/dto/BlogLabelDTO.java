package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BlogLabelDTO {
    @ApiModelProperty("标签名")
    private String label;

    @ApiModelProperty("当前标签下博客总数")
    private Integer num;
}
