package com.eyes.eyesspace.persistent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MusicListDTO {
    @ApiModelProperty("歌曲id")
    private Integer id;

    @ApiModelProperty("歌曲名称")
    private String title;

    @ApiModelProperty("歌曲作者")
    private String author;

    @ApiModelProperty("歌曲封面")
    private String pic;

    @ApiModelProperty("歌曲播放量")
    private Integer views;

    @ApiModelProperty("歌曲点赞量")
    private Integer likes;

    @ApiModelProperty("歌曲评论量")
    private Integer comments;

    @ApiModelProperty("歌曲状态")
    private Integer status;

    @ApiModelProperty("歌曲上传时间")
    private String createTime;
}
