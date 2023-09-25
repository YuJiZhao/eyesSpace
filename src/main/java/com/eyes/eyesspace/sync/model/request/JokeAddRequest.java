package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/9/25 9:23
 */
@Data
@ApiModel
public class JokeAddRequest {
  @ApiModelProperty("梗图url列表")
  private List<String> urlList;

  @ApiModelProperty("梗图类别")
  @NotNull(message = "类别不能为空")
  private String category;

  @ApiModelProperty("状态")
  private Integer status = 0;
}
