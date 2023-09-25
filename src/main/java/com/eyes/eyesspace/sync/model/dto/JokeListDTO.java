package com.eyes.eyesspace.sync.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/9/25 11:21
 */
@Data
@ApiModel
public class JokeListDTO {
  @ApiModelProperty("梗图id")
  private Long id;

  @ApiModelProperty("梗图图片列表")
  private List<String> urlList;

  @ApiModelProperty("梗图类别")
  private String category;
}
