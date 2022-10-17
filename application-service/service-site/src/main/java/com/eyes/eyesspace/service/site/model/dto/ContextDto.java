package com.eyes.eyesspace.service.site.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ContextDto {
    @ApiModelProperty("空间名称")
    private String spaceName;

    @ApiModelProperty("拥有者名称")
    private String ownerName;

    @ApiModelProperty("拥有者头像")
    private String ownerAvatar;

    @ApiModelProperty("拥有者签名")
    private String ownerMotto;

    @ApiModelProperty("网络足迹")
    private List<FootprintDto> footprint;

    @ApiModelProperty("建站时间")
    private String buildTime;

    @ApiModelProperty("公告")
    private String announce;

    @ApiModelProperty("公告版本")
    private String announceVersion;

    @ApiModelProperty("访问量")
    private Integer visitNum;

    @ApiModelProperty("访客数")
    private Integer visitorNum;

    @ApiModelProperty("用户数")
    private Integer userNum;

    @ApiModelProperty("博客总数")
    private Integer blogNum;

    @ApiModelProperty("说说总数")
    private Integer shuoNum;
}