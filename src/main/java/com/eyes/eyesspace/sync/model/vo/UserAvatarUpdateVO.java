package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/2/10 15:44
 */
@Data
@AllArgsConstructor
@ApiModel
public class UserAvatarUpdateVO {
  @ApiModelProperty("新头像url")
  private String url;
}
