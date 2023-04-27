package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MusicInfoDTO {
    @ApiModelProperty("歌曲名称")
    private String title;

    @ApiModelProperty("歌曲作者")
    private String author;

    @ApiModelProperty("音频文件地址")
    private String url;

    @ApiModelProperty("歌曲封面地址")
    private String pic;

    @ApiModelProperty("站长评论")
    private String ownerComment;

    @ApiModelProperty("播放量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("上传时间")
    private String createTime;
}
