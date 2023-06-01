package com.eyes.eyesspace.sync.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 11:53
 */
@Data
@ApiModel
public class FriendListDTO {
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("网站名称")
  private String name;

  @ApiModelProperty("网站介绍")
  private String introduce;

  @ApiModelProperty("站长头像")
  private String avatar;

  @ApiModelProperty("网站地址")
  private String address;

  @ApiModelProperty("网站状态")
  private Integer status;
}
