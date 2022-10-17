package com.eyes.eyesspace.service.entertain.module.music.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MusicListDto {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("音乐标题")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("封面图片地址")
    private String pic;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("音乐状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private String createTime;
}
