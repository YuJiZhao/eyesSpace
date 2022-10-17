package com.eyes.eyesspace.service.entertain.module.music.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class MusicListInfoDto {
    @ApiModelProperty("音乐总数")
    private Integer totalNum;

    @ApiModelProperty("公开音乐数")
    private Integer publicNum;

    @ApiModelProperty("私有音乐数")
    private Integer privateNum;

    @ApiModelProperty("删除音乐数")
    private Integer deleteNum;

    @ApiModelProperty("总播放量")
    private Integer viewsNum;

    @ApiModelProperty("总点赞量")
    private Integer likesNum;

    @ApiModelProperty("总评论量")
    private Integer commentsNum;
}
