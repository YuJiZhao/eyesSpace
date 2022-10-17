package com.eyes.eyesspace.service.site.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UserUpdateInfoDto {
    @ApiModelProperty("用户昵称")
    @NotNull(message = "用户昵称不能为空")
    @Length(message = "昵称长度必须在2-10以内", min = 2, max = 10)
    private String name;
}
