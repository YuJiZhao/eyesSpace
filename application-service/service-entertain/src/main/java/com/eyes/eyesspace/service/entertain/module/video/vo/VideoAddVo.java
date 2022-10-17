package com.eyes.eyesspace.service.entertain.module.video.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VideoAddVo {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

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

    @ApiModelProperty("视频状态")
    private Integer status;

    @ApiModelProperty("视频地址")
    private String videoUrl;
}
