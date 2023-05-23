package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/22 14:44
 */
@Data
@ApiModel
public class AnimeListInfoVO {
  @ApiModelProperty("动漫总数")
  private Long totalNum;

  @ApiModelProperty("公开动漫数")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long publicNum;

  @ApiModelProperty("私有动漫数")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long privateNum;

  @ApiModelProperty("已删除动漫数")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long deleteNum;

  @ApiModelProperty("总点击量")
  private Integer clickNum;

  @ApiModelProperty("总评论数")
  private Integer commentNum;

  public AnimeListInfoVO(Integer clickNum, Integer commentNum) {
    this.clickNum = clickNum;
    this.commentNum = commentNum;
  }
}
