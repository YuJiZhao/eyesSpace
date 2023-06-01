package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 11:13
 */
@Data
@ApiModel
@AllArgsConstructor
public class FriendListInfoVO {
  @ApiModelProperty("有效友链")
  private Long validChain;

  @ApiModelProperty("无效友链")
  private Long invalidChain;

  @ApiModelProperty("审核中友链")
  private Long verifyingChain;
}
