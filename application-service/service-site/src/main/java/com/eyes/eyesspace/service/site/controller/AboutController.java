package com.eyes.eyesspace.service.site.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.site.service.AboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/site/about")
@Api(tags = "留言模块")
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @ApiOperation("留言")
    @PostMapping("/doAboutComment")
    public StandardResult<Void> doAboutComment(@Validated @RequestBody CommentAddDto commentAddDto) throws CustomException {
        aboutService.doAboutComment(commentAddDto);
        return Result.success();
    }

    @ApiOperation("获取留言")
    @GetMapping("/getAboutCommentList")
    public StandardResult<List<CommentListDto>> getAboutCommentList(
            Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        if (Objects.isNull(pageSize) || pageSize > 10) pageSize = 5;
        return Result.success(aboutService.getAboutCommentList(id, page, pageSize));
    }

    @ApiOperation("删除留言")
    @DeleteMapping("/delAboutComment/{id}")
    public StandardResult<Void> delAboutComment(@PathVariable Integer id) throws CustomException {
        aboutService.delAboutComment(id);
        return Result.success();
    }
}