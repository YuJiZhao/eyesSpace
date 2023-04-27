package com.eyes.eyesspace.sync.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class MusicListInfoVO {
    @ApiModelProperty("歌曲总数")
    private Integer totalNum;

    @ApiModelProperty("公开歌曲总数")
    private Integer publicNum;

    @ApiModelProperty("私有歌曲总数")
    private Integer privateNum;

    @ApiModelProperty("删除歌曲总数")
    private Integer deleteNum;

    @ApiModelProperty("歌曲总播放数")
    private Integer viewsNum;

    @ApiModelProperty("歌曲总点赞数")
    private Integer likesNum;

    @ApiModelProperty("歌曲总评论数")
    private Integer commentsNum;
}
