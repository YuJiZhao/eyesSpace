package com.eyes.eyesspace.service.article.module.dto;

import com.eyes.eyesspace.service.article.module.po.BlogDataPo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel
public class BlogListInfoDto {
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

    @ApiModelProperty("总浏览量")
    private Integer viewsNum;

    @ApiModelProperty("总点赞量")
    private Integer likesNum;

    @ApiModelProperty("总收藏量")
    private Integer collectionsNum;

    @ApiModelProperty("总评论量")
    private Integer commentsNum;

    @ApiModelProperty("博客总字数")
    private Integer words;

    public BlogListInfoDto(Integer totalNum, BlogDataPo blogDataPo, Integer words) {
        this.totalNum = totalNum;
        this.viewsNum = blogDataPo.getViewsNum();
        this.likesNum = blogDataPo.getLikesNum();
        this.collectionsNum = blogDataPo.getCollectionsNum();
        this.commentsNum = blogDataPo.getCommentsNum();
        this.words = words;
    }

    public BlogListInfoDto(Integer totalNum, Integer publicNum, Integer privateNum, Integer deleteNum, BlogDataPo blogDataPo, Integer words) {
        this.totalNum = totalNum;
        this.publicNum = publicNum;
        this.privateNum = privateNum;
        this.deleteNum = deleteNum;
        this.viewsNum = blogDataPo.getViewsNum();
        this.likesNum = blogDataPo.getLikesNum();
        this.collectionsNum = blogDataPo.getCollectionsNum();
        this.commentsNum = blogDataPo.getCommentsNum();
        this.words = words;
    }
}
