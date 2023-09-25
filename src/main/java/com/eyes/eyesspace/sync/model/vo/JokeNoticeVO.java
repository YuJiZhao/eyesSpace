package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/9/25 8:51
 */
@Data
@ApiModel
@AllArgsConstructor
public class JokeNoticeVO {
  @ApiModelProperty("梗图说")
  private String notice;
}
