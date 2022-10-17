package com.eyes.eyesspace.service.article.module.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class BlogAddDto {
    @ApiModelProperty("id")
    private Integer id;
}
