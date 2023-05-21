package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/20 16:40
 */
@Data
@ApiModel
public class VersionInfoVO {
  @ApiModelProperty("网站版本")
  private String siteVersion;

  @ApiModelProperty("前端版本")
  private String frontendVersion;

  @ApiModelProperty("后端版本")
  private String backendVersion;

  @ApiModelProperty("版本总数")
  private Integer versionNum;
}
