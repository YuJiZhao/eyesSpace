package com.eyes.eyesspace.sync.model.vo;

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
public class ShuoListInfoVO {
    @ApiModelProperty("说说总数")
    private Integer totalNum;

    @ApiModelProperty("公开说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer publicNum;

    @ApiModelProperty("私有说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer privateNum;

    @ApiModelProperty("已删除说说数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deleteNum;

    @ApiModelProperty("说说总阅读量")
    private Integer viewsNum;

    @ApiModelProperty("说说总评论量")
    private Integer commentsNum;

    public ShuoListInfoVO(Integer totalNum, Integer viewsNum, Integer commentsNum) {
        this.totalNum = totalNum;
        this.viewsNum = viewsNum;
        this.commentsNum = commentsNum;
    }
}
