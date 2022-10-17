package com.eyes.eyesspace.service.article.module.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ShuoshuoAddDto {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("说说内容")
    private String content;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("说说图片")
    private List<ShuoshuoAddPicDto> picList;
}
