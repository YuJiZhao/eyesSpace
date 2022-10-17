package com.eyes.eyesspace.service.article.module.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ShuoshuoListInfoDto {
    @ApiModelProperty("说说总数")
    private Integer totalNum;

    @ApiModelProperty("公开说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer publicNum;

    @ApiModelProperty("私有说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer privateNum;

    @ApiModelProperty("删除说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deleteNum;

    @ApiModelProperty("总浏览量")
    private Integer viewsNum;

    @ApiModelProperty("总评论量")
    private Integer commentsNum;

    public ShuoshuoListInfoDto(Integer totalNum, Integer viewsNum, Integer commentsNum) {
        this.totalNum = totalNum;
        this.publicNum = publicNum;
        this.viewsNum = viewsNum;
        this.commentsNum = commentsNum;
    }
}
