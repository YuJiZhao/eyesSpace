package com.eyes.eyesspace.service.article.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ShuoshuoAddPicDto {
    @ApiModelProperty("图片id")
    private String pid;

    @ApiModelProperty("图片链接")
    private String url;
}
