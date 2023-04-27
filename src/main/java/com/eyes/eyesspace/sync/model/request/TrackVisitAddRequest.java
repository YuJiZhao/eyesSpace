package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class TrackVisitAddRequest {
    @ApiModelProperty("进站入口路径")
    @NotNull(message = "入口路径不能为空")
    private String path;
}
