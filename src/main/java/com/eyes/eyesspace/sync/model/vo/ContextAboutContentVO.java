package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/2/24 12:50
 */
@Data
@AllArgsConstructor
@ApiModel
public class ContextAboutContentVO {
  @ApiModelProperty("内容")
  private String content;
}
