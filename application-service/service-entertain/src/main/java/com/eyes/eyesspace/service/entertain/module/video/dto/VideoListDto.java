package com.eyes.eyesspace.service.entertain.module.video.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoListDto {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("封面图片地址")
    private String pictureUrl;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("视频状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private String createTime;
}
