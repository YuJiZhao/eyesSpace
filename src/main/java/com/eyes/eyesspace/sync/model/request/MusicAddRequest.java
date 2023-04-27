package com.eyes.eyesspace.sync.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MusicAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("歌曲名称")
    private String title;

    @ApiModelProperty("歌曲作者")
    private String author;

    @ApiModelProperty("歌曲封面")
    private String pic;

    @ApiModelProperty("歌曲音频文件地址")
    private String url;

    @ApiModelProperty("歌词")
    private String lrc;

    @ApiModelProperty("作者评论")
    private String ownerComment;

    @ApiModelProperty("歌曲状态")
    private Integer status;
}
