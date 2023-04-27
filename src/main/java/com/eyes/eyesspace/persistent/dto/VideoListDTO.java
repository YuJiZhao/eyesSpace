package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoListDTO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("封面链接")
    private String pictureUrl;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("上传时间")
    private String createTime;
}
