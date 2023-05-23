package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/22 14:19
 */
@Data
@ApiModel
@AllArgsConstructor
public class AnimeNoticeVO {
  @ApiModelProperty("站长说")
  private String notice;
}
