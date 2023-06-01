package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 15:24
 */
@Data
@ApiModel
public class FriendChainRefuseRequest {
  @ApiModelProperty("id")
  @NotNull(message = "id不能为空")
  private Integer id;

  @ApiModelProperty("备注")
  private String notes = "暂无";

  @ApiModelProperty("是否需要置位删除状态")
  @NotNull(message = "needDelete不能为空")
  private Boolean needDelete;
}
