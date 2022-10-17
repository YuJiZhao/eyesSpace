package com.eyes.eyesspace.common.feign.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel
public class VeriCodeEmailDto {
    @ApiModelProperty("邮箱")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("邮件主题")
    @NotNull(message = "邮件主题不能为空")
    private String subject;

    @ApiModelProperty("key")
    @NotNull(message = "key不能为空")
    private String key;
}
