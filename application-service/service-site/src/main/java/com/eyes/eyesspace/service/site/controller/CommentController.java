package com.eyes.eyesspace.service.site.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.service.site.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/site/comment")
@Api(tags = "评论模块")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation("发表评论")
    @PostMapping("/publishComment")
    public StandardResult<Void> publishComment(
            @Validated @RequestBody CommentAddDto commentAddDto,
            @NotNull(message = "评论类型不能为空") @RequestParam Integer type) throws CustomException {
        commentService.publishComment(commentAddDto, type);
        return Result.success();
    }

    @ApiOperation("获取评论")
    @GetMapping("/getCommentList/{id}")
    public StandardResult<List<CommentListDto>> getCommentList(
            @PathVariable("id") Integer id,
            @RequestParam Integer type,
            @RequestParam(required = false) Integer uid,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        if (Objects.isNull(pageSize) || pageSize > 10) pageSize = 5;
        return Result.success(commentService.getCommentList(id, type, uid, page, pageSize));
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/delComment/{id}")
    public StandardResult<Void> delComment(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) Integer uid) throws CustomException {
        commentService.delComment(id, uid);
        return Result.success();
    }

    // 修改评论
}