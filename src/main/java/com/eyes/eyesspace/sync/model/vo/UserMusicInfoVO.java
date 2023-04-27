package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserMusicInfoVO {
    @ApiModelProperty("加密id")
    private String id;

    @ApiModelProperty("歌曲名称")
    private String title;

    @ApiModelProperty("歌曲作者")
    private String author;

    @ApiModelProperty("歌曲音频地址")
    private String url;

    @ApiModelProperty("歌曲封面地址")
    private String pic;

    @ApiModelProperty("歌曲播放量")
    private Integer views;

    @ApiModelProperty("歌曲点赞量")
    private Integer likes;

    @ApiModelProperty("歌曲评论量")
    private Integer comments;

    @ApiModelProperty("当前用户是否点赞")
    private Boolean isLike;
}
