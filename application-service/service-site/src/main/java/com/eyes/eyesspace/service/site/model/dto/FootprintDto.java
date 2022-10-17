package com.eyes.eyesspace.service.site.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "足迹Dto")
public class FootprintDto {
    @ApiModelProperty("图标链接")
    private String icon;

    @ApiModelProperty("链接地址")
    private String url;

    @ApiModelProperty("足迹名称")
    private String name;
}
