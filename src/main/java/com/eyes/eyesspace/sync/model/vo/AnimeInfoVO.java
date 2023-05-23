package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/22 20:02
 */

@Data
@ApiModel
public class AnimeInfoVO {
  @ApiModelProperty("动漫名称")
  private String title;

  @ApiModelProperty("动漫类型")
  private String type;

  @ApiModelProperty("观看时期")
  private String period;

  @ApiModelProperty("介绍")
  private String introduce;

  @ApiModelProperty("站长说")
  private String word;

  @ApiModelProperty("封面")
  private String cover;

  @ApiModelProperty("点击量")
  private Integer view;

  @ApiModelProperty("评论数")
  private Integer comment;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createTime;
}
