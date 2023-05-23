package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.AnimeListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/22 17:54
 */
@Data
@ApiModel
@AllArgsConstructor
public class AnimeListVO {
  @ApiModelProperty("动漫总数")
  private Integer total;

  @ApiModelProperty("动漫列表数据")
  private List<AnimeListDTO> data;
}
