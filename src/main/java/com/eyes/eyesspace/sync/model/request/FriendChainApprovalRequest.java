package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 14:08
 */
@Data
@ApiModel
public class FriendChainApprovalRequest {
  @ApiModelProperty("友链id")
  @NotNull(message = "友链id不能为空")
  private Integer id;

  @ApiModelProperty("备注")
  private String notes = "暂无";
}
