package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.FootprintDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ContextVO {
    @ApiModelProperty("网站名称")
    private String spaceName;

    @ApiModelProperty("作者名称")
    private String ownerName;

    @ApiModelProperty("作者头像")
    private String ownerAvatar;

    @ApiModelProperty("座右铭")
    private String ownerMotto;

    @ApiModelProperty("作者足迹")
    private List<FootprintDTO> footprint;

    @ApiModelProperty("建站时间")
    private String buildTime;

    @ApiModelProperty("网站公告")
    private String announce;
}