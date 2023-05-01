package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel
public class VideoAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("视频标题")
    @NotNull(message = "视频标题不能为空")
    private String title;

    @ApiModelProperty("视频作者")
    private String originalAuthor = "佚名";

    @ApiModelProperty("视频封面链接")
    @NotNull(message = "视频封面不能为空")
    private String pictureUrl;

    @ApiModelProperty("视频文件链接")
    @NotNull(message = "视频文件不能为空")
    private String videoUrl;

    @ApiModelProperty("转载地址")
    @NotNull(message = "转载地址不能玩为空")
    private String originalUrl;

    @ApiModelProperty("站长感言")
    private String ownerComment = "暂无";

    @ApiModelProperty("视频状态")
    private Integer status = StatusEnum.PUBLIC.getStatus();
}
