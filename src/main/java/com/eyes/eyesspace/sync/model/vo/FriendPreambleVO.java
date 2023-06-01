package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 18:49
 */
@Data
@ApiModel
@AllArgsConstructor
public class FriendPreambleVO {
  @ApiModelProperty("前言")
  private String content;
}
