package com.eyes.eyesspace.common.feign.module.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel
public class AuthVo {
    @ApiModelProperty("url")
    @NotNull(message = "缺失url")
    private String url;

    @ApiModelProperty("token")
    private String token;
}
