package com.eyes.eyesspace.service.backstage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class AddSpaceVisitVo {
    @ApiModelProperty("访问入口路径")
    @NotNull(message = "入口路径不能为空")
    private String path;
}
