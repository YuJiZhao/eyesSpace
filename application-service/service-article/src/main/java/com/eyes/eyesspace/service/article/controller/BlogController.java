package com.eyes.eyesspace.service.article.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.article.module.dto.*;
import com.eyes.eyesspace.service.article.module.vo.BlogAddVo;
import com.eyes.eyesspace.service.article.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.service.article.module.dto.BlogInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Api(tags = "博客模块")
@RestController
@RequestMapping("/article/blog")
@Validated
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation("添加博客")
    @PostMapping("/addBlog")
    public StandardResult<BlogAddDto> addBlog(@Validated @RequestBody BlogAddVo blogAddVo) throws CustomException {
        return Result.success(blogService.addBlog(blogAddVo));
    }

    @ApiOperation("上传博客图片")
    @PostMapping("/addBlogPic")
    public StandardResult<FileUploadDto> addBlogPic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(blogService.addBlogPic(multipartFile));
    }

    @ApiOperation("获取博客总体信息")
    @GetMapping("/getBlogListInfo")
    public StandardResult<BlogListInfoDto> getBlogListInfo() throws CustomException {
        return Result.success(blogService.getBlogListInfo());
    }

    @ApiOperation("获取博客列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", defaultValue="1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue="10")
    })
    @GetMapping("/getBlogList")
    public StandardResult<BlogListTotalDto> getBlogList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String label
            ) throws CustomException {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        pageSize = (Objects.isNull(pageSize) || pageSize > 20) ? 10 : pageSize;
        if (category.equals("undefined")) category = null;
        if (label.equals("undefined")) label = null;
        return Result.success(blogService.getBlogList(page, pageSize, category, label));
    }

    @ApiOperation("获取单个博客详细信息")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/getBlogInfo/{id}")
    public StandardResult<BlogInfoDto> getBlogInfo(
            @NotNull(message = "博客id不能为空")
            @PathVariable Integer id) throws CustomException {
        return Result.success(blogService.getBlogInfo(id));
    }

    @ApiOperation("获取单个博客简要信息")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/getBlogSummaryInfo/{id}")
    public StandardResult<BlogListDto> getBlogSummaryInfo(
            @NotNull(message = "博客id不能为空")
            @PathVariable Integer id) throws CustomException {
        return Result.success(blogService.getBlogSummaryInfo(id));
    }

    @ApiOperation("获取所有博客分类")
    @GetMapping("/getBlogCategory")
    public StandardResult<List<BlogCategoryDto>> getBlogCategory() throws CustomException {
        return Result.success(blogService.getBlogCategory());
    }

    @ApiOperation("获取所有博客标签")
    @GetMapping("/getBlogLabel")
    public StandardResult<List<BlogLabelDto>> getBlogLabel() throws CustomException {
        return Result.success(blogService.getBlogLabel());
    }

    @ApiOperation("点赞博客")
    @GetMapping("/doBlogLike")
    public StandardResult<Void> doBlogLike(@NotNull(message = "id不能为空") Integer id) throws CustomException {
        blogService.doBlogLike(id);
        return Result.success();
    }

    @ApiOperation("收藏博客")
    @GetMapping("/doBlogCollect")
    public StandardResult<Void> doBlogCollect(@NotNull(message = "id不能为空") Integer id) throws CustomException {
        blogService.doBlogCollect(id);
        return Result.success();
    }

    @ApiOperation("评论博客")
    @PostMapping("/doBlogComment")
    public StandardResult<Void> doBlogComment(@Validated @RequestBody CommentAddDto commentAddDto) throws CustomException {
        blogService.doBlogComment(commentAddDto);
        return Result.success();
    }

    @ApiOperation("获取博客评论")
    @GetMapping("/getBlogCommentList")
    public StandardResult<List<CommentListDto>> getBlogCommentList(
            Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        if (Objects.isNull(pageSize) || pageSize > 10) pageSize = 5;
        return Result.success(blogService.getBlogCommentList(id, page, pageSize));
    }

    @ApiOperation("删除博客评论")
    @DeleteMapping("/delBlogComment/{id}")
    public StandardResult<Void> delBlogComment(@PathVariable Integer id) throws CustomException {
        blogService.delBlogComment(id);
        return Result.success();
    }
}