package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/2/10 15:23
 */
@Data
@ApiModel
public class UserInfoVO {
  @ApiModelProperty("用户id")
  private Long id;

  @ApiModelProperty("脱敏邮箱")
  private String email;

  @ApiModelProperty("用户头像")
  private String avatar;

  @ApiModelProperty("用户创建时间")
  private String createTime;
}
