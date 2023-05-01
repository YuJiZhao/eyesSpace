package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel
public class MusicAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("歌曲名称")
    @NotNull(message = "歌曲名称不能为空")
    private String title;

    @ApiModelProperty("歌曲作者")
    private String author = "佚名";

    // TODO：可以搞个默认歌曲封面
    @ApiModelProperty("歌曲封面")
    @NotNull(message = "歌曲封面不能为空")
    private String pic;

    @ApiModelProperty("歌曲音频文件地址")
    @NotNull(message = "歌曲音频文件地址不能为空")
    private String url;

    @ApiModelProperty("歌词")
    private String lrc = "暂无";

    @ApiModelProperty("站长感言")
    private String ownerComment = "暂无";

    @ApiModelProperty("歌曲状态")
    private Integer status = StatusEnum.PUBLIC.getStatus();
}
