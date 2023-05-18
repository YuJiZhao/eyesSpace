package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/18 15:45
 */
@Data
@ApiModel
public class VideoAddBatchBiliRequest {
  @ApiModelProperty("视频标题")
  @NotNull(message = "视频标题不能为空")
  private String title;

  @ApiModelProperty("视频封面链接(非本站链接)")
  @NotNull(message = "视频封面链接不能为空")
  private String cover;

  @ApiModelProperty("视频原作者")
  @NotNull(message = "视频原作者不能为空")
  private String name;

  @ApiModelProperty("视频bv号")
  @NotNull(message = "视频bv号不能为空")
  private String bvid;

  @ApiModelProperty("视频文件链接")
  @NotNull(message = "视频文件链接不能为空")
  private String videoUrl;

  @ApiModelProperty("站长感言")
  private String ownerComment;
}
