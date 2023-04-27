package com.eyes.eyesspace.sync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("视频作者")
    private String originalAuthor;

    @ApiModelProperty("视频封面链接")
    private String pictureUrl;

    @ApiModelProperty("视频文件链接")
    private String videoUrl;

    @ApiModelProperty("转载地址")
    private String originalUrl;

    @ApiModelProperty("作者评价")
    private String ownerComment;

    @ApiModelProperty("视频状态")
    private Integer status;
}
