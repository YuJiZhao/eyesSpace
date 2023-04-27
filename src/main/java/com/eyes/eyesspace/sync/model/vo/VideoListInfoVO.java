package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class VideoListInfoVO {
    @ApiModelProperty("视频总数")
    private Integer totalNum;

    @ApiModelProperty("公开视频数")
    private Integer publicNum;

    @ApiModelProperty("私有视频数")
    private Integer privateNum;

    @ApiModelProperty("删除视频数")
    private Integer deleteNum;

    @ApiModelProperty("总播放量")
    private Integer viewsNum;

    @ApiModelProperty("总点赞量")
    private Integer likesNum;

    @ApiModelProperty("总评论量")
    private Integer commentsNum;
}
