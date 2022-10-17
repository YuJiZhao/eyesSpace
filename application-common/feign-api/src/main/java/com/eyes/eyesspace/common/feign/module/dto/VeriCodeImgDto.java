package com.eyes.eyesspace.common.feign.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class VeriCodeImgDto {
    @ApiModelProperty("base64图片")
    private String base64Img;

    @ApiModelProperty("key")
    private String key;
}
