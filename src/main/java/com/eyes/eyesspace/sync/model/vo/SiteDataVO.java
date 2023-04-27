package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/2/23 20:27
 */
@Data
@AllArgsConstructor
@ApiModel
public class SiteDataVO {
  @ApiModelProperty("网站访问量")
  private Integer visitNum;

  @ApiModelProperty("网站访客数")
  private Integer visitorNum;
}
