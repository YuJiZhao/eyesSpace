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
public class AuthDto {
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("角色")
    private String role;
}
