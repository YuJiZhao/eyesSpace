package com.eyes.eyesspace.sync.model.dto;

import com.eyes.eyesspace.sync.model.bean.HomeListBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/21 13:53
 */
@Data
@ApiModel
public class VersionListDTO implements HomeListBean {
  @JsonIgnore
  private Integer id;

  @ApiModelProperty("版本号")
  private String version;

  @ApiModelProperty("版本类型")
  private Integer type;

  @ApiModelProperty("版本描述")
  private String description;

  @ApiModelProperty("发布时间")
  private String createTime;

  @ApiModelProperty("版本图片")
  private List<String> picList;
}
