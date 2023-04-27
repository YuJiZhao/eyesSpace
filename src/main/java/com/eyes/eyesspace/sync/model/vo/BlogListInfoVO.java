package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.persistent.po.BlogDataPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BlogListInfoVO {
    @ApiModelProperty("博客总数")
    private Integer totalNum;

    @ApiModelProperty("公开博客数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer publicNum;

    @ApiModelProperty("私有博客数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer privateNum;

    @ApiModelProperty("删除博客数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deleteNum;

    @ApiModelProperty("博客总阅读量")
    private Integer viewsNum;

    @ApiModelProperty("博客总评论数")
    private Integer commentsNum;

    @ApiModelProperty("博客总字数量")
    private Integer words;

    public BlogListInfoVO(Integer totalNum, BlogDataPO blogDataPo, Integer words) {
        this.totalNum = totalNum;
        this.viewsNum = blogDataPo.getViewsNum();
        this.commentsNum = blogDataPo.getCommentsNum();
        this.words = words;
    }

    public BlogListInfoVO(Integer totalNum, Integer publicNum, Integer privateNum, Integer deleteNum, BlogDataPO blogDataPo, Integer words) {
        this.totalNum = totalNum;
        this.publicNum = publicNum;
        this.privateNum = privateNum;
        this.deleteNum = deleteNum;
        this.viewsNum = blogDataPo.getViewsNum();
        this.commentsNum = blogDataPo.getCommentsNum();
        this.words = words;
    }
}
