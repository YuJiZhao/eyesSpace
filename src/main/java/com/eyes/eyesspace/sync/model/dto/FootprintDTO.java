package com.eyes.eyesspace.sync.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FootprintDTO {
    @ApiModelProperty("网站图标")
    private String icon;

    @ApiModelProperty("网站链接")
    private String url;

    @ApiModelProperty("网站名称")
    private String name;
}
