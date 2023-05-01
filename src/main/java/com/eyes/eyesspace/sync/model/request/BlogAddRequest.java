package com.eyes.eyesspace.sync.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class BlogAddRequest {
    @ApiModelProperty("博客标题")
    @NotNull(message = "博客标题不能为空")
    private String title;

    @ApiModelProperty("博客内容")
    @NotNull(message = "博客内容不能为空")
    private String content;

    @ApiModelProperty("博客摘要")
    @NotNull(message = "博客摘要不能为空")
    @Length(max = 250, message = "摘要不能超过250字")
    private String summary;

    @ApiModelProperty("博客字数")
    @NotNull(message = "字数不能为空")
    private Integer words;

    @ApiModelProperty("博客状态")
    private Integer status = StatusEnum.PUBLIC.getStatus();

    @ApiModelProperty("博客标签")
    @NotNull(message = "博客标签不能为空")
    private List<String> labels;

    @ApiModelProperty("博客分类")
    @NotNull(message = "博客分类不能为空")
    private String category;
}
