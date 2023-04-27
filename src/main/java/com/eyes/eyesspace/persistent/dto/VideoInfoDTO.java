package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoInfoDTO {
    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("原作者")
    private String originalAuthor;

    @ApiModelProperty("封面链接")
    private String pictureUrl;

    @ApiModelProperty("转载地址")
    private String originalUrl;

    @ApiModelProperty("站长评论")
    private String ownerComment;

    @ApiModelProperty("视频链接")
    private String videoUrl;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("上传时间")
    private String createTime;
}
