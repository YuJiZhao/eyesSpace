package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.FriendListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 11:52
 */
@Data
@ApiModel
@AllArgsConstructor
public class FriendListVO {
  @ApiModelProperty("友链总数")
  private Integer total;

  @ApiModelProperty("友链数据")
  private List<FriendListDTO> data;
}
