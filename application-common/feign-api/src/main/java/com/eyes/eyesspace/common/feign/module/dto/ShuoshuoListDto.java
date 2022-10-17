package com.eyes.eyesspace.common.feign.module.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.eyes.eyesspace.common.feign.module.bean.HomeListBean;

import java.util.List;

@Data
@ApiModel
public class ShuoshuoListDto implements HomeListBean {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("说说内容")
    private String content;

    @ApiModelProperty("说说图片")
    private List<String> picList;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("说说状态")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @ApiModelProperty("创建时间")
    private String createTime;
}
