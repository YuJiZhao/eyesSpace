package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.sync.model.dto.ShuoAddPicDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ShuoAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("说说内容")
    private String content;

    @ApiModelProperty("说说状态")
    private Integer status;

    @ApiModelProperty("说说图片列表")
    private List<ShuoAddPicDTO> picList;
}
