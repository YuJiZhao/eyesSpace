package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.sync.model.dto.ShuoAddPicDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ShuoAddRequest {
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("说说内容")
    @NotNull(message = "说说内容不能为空")
    private String content;

    @ApiModelProperty("说说状态")
    private Integer status = StatusEnum.PUBLIC.getStatus();

    @ApiModelProperty("说说图片列表")
    private List<ShuoAddPicDTO> picList;
}
