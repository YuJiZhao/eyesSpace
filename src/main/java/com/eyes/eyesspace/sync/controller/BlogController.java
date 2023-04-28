package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.persistent.dto.BlogCategoryDTO;
import com.eyes.eyesspace.persistent.dto.BlogInfoDTO;
import com.eyes.eyesspace.persistent.dto.BlogLabelDTO;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.sync.model.request.BlogAddRequest;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.BlogAddVO;
import com.eyes.eyesspace.sync.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 */
@Api(tags = "博客模块")
@RestController
@RefreshScope
@RequestMapping("/blog")
@Validated
public class BlogController {
    @Value("${business.page-size.blog:10}")
    private Integer blogPageSize;

    @Value("${business.comment.size:6}")
    private Integer commentSize;

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation("发布博客")
    @Permission(PermissionEnum.ADMIN)
    @PostMapping("/addBlog")
    public Result<BlogAddVO> addBlog(@Validated @RequestBody BlogAddRequest blogAddRequest) throws CustomException {
        return Result.success(blogService.addBlog(blogAddRequest));
    }

    @ApiOperation("上传博客图片")
    @Permission(PermissionEnum.ADMIN)
    @PostMapping("/addBlogPic")
    public Result<FileUploadVO> addBlogPic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(blogService.addBlogPic(multipartFile));
    }

    @ApiOperation("获取博客总体信息")
    @Limiter
    @Permission
    @GetMapping("/getBlogListInfo")
    public Result<BlogListInfoVO> getBlogListInfo() throws CustomException {
        return Result.success(blogService.getBlogListInfo());
    }

    @ApiOperation("获取博客列表")
    @Limiter
    @Permission
    @GetMapping("/getBlogList")
    public Result<PageBind<BlogListDTO>> getBlogList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String label
            ) throws CustomException {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        // TODO：太难看了，记得改
        if (category.equals("undefined")) category = null;
        if (label.equals("undefined")) label = null;
        return Result.success(blogService.getBlogList(page, blogPageSize, category, label));
    }

    @ApiOperation("获取单个博客详细信息")
    @Limiter
    @Permission
    @GetMapping("/getBlogInfo/{id}")
    public Result<BlogInfoDTO> getBlogInfo(
            @NotNull(message = "博客id不能为空")
            @PathVariable Integer id) throws CustomException {
        return Result.success(blogService.getBlogInfo(id));
    }

    @ApiOperation("获取所有博客分类")
    @Limiter
    @Permission
    @GetMapping("/getBlogCategory")
    public Result<List<BlogCategoryDTO>> getBlogCategory() throws CustomException {
        return Result.success(blogService.getBlogCategory());
    }

    @ApiOperation("获取博客标签")
    @Limiter
    @Permission
    @GetMapping("/getBlogLabel")
    public Result<List<BlogLabelDTO>> getBlogLabel() throws CustomException {
        return Result.success(blogService.getBlogLabel());
    }

    @ApiOperation("评论博客")
    @Limiter
    @Permission(PermissionEnum.USER)
    @PostMapping("/doBlogComment")
    public Result<Void> doBlogComment(@Validated @RequestBody CommentAddRequest commentAddRequest) throws CustomException {
        blogService.doBlogComment(commentAddRequest);
        return Result.success();
    }

    @ApiOperation("获取博客评论")
    @Limiter
    @Permission
    @GetMapping("/getBlogCommentList")
    public Result<List<CommentListVO>> getBlogCommentList(
            Integer id,
            @RequestParam(required = false) Integer page) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        return Result.success(blogService.getBlogCommentList(id, page, commentSize));
    }

    @ApiOperation("删除博客评论")
    @Limiter
    @Permission(PermissionEnum.USER)
    @DeleteMapping("/delBlogComment/{id}")
    public Result<Void> delBlogComment(@PathVariable Integer id) throws CustomException {
        blogService.delBlogComment(id);
        return Result.success();
    }
}