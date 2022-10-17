package com.eyes.eyesspace.common.feign.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel
public class VeriCodeImgCheckDto {
    @ApiModelProperty("图形验证码")
    @NotNull(message = "图形验证码不能为空")
    private String code;

    @ApiModelProperty("key")
    @NotBlank(message = "缺失key值")
    private String key;
}
