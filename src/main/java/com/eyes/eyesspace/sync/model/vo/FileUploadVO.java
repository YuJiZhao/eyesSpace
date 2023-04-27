package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/1/25 15:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class FileUploadVO {
  @ApiModelProperty("文件链接")
  private String url;
}
