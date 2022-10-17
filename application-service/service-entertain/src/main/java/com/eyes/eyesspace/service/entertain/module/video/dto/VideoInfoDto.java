package com.eyes.eyesspace.service.entertain.module.video.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoInfoDto {
    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("原作者")
    private String originalAuthor;

    @ApiModelProperty("封面图片地址")
    private String pictureUrl;

    @ApiModelProperty("原视频地址")
    private String originalUrl;

    @ApiModelProperty("我的评论")
    private String ownerComment;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("发布时间")
    private String createTime;
}
