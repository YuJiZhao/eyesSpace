package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/9/25 10:51
 */
@Data
@AllArgsConstructor
@ApiModel
public class JokeAddVO {
  @ApiModelProperty("新梗图id")
  private Long id;
}
