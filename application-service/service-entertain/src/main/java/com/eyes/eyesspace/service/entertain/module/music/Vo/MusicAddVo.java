package com.eyes.eyesspace.service.entertain.module.music.Vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MusicAddVo {
    @ApiModelProperty(hidden=true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("音乐标题")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("封面图片地址")
    private String pic;

    @ApiModelProperty("音乐地址")
    private String url;

    @ApiModelProperty("歌词")
    private String lrc;

    @ApiModelProperty("我的评论")
    private String ownerComment;

    @ApiModelProperty("音乐状态")
    private Integer status;
}
