package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.VersionListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/20 19:20
 */
@Data
@ApiModel
@AllArgsConstructor
public class VersionListVO {
  @ApiModelProperty("版本总数")
  private Integer total;

  @ApiModelProperty("版本数据")
  private List<VersionListDTO> data;
}
