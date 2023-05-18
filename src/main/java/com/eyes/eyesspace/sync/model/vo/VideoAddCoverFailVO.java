package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/18 16:09
 */
@Data
@ApiModel
@AllArgsConstructor
public class VideoAddCoverFailVO {
  @ApiModelProperty("视频标题")
  private String title;

  @ApiModelProperty("视频bv号")
  private String bvid;

  @ApiModelProperty("错误信息")
  private String error;
}
