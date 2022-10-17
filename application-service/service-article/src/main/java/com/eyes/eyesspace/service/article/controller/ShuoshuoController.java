package com.eyes.eyesspace.service.article.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoCommentAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoListInfoDto;
import com.eyes.eyesspace.service.article.service.ShuoshuoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/article/shuoshuo")
@Api(tags = "说说模块")
public class ShuoshuoController {
    private final ShuoshuoService shuoshuoService;

    public ShuoshuoController(ShuoshuoService shuoshuoService) {
        this.shuoshuoService = shuoshuoService;
    }

    @ApiOperation("添加说说")
    @PostMapping("/addShuoshuo")
    public StandardResult<Void> addShuoShuo(@RequestBody ShuoshuoAddDto shuoshuoAddDto) throws CustomException {
        shuoshuoService.addShuoshuo(shuoshuoAddDto);
        return Result.success();
    }

    @ApiOperation("上传说说图片")
    @PostMapping("/uploadShuoshuoPic")
    public StandardResult<FileUploadDto> uploadShuoshuoPic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(shuoshuoService.uploadShuoshuoPic(multipartFile));
    }

    @ApiOperation("获取说说总体信息")
    @GetMapping("/getShuoshuoListInfo")
    public StandardResult<ShuoshuoListInfoDto> getShuoshuoListInfo() throws CustomException {
        return Result.success(shuoshuoService.getShuoshuoListInfo());
    }

    @ApiOperation("获取说说列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", defaultValue="1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue="10")
    })
    @GetMapping("/getShuoshuoList")
    public StandardResult<List<ShuoshuoListDto>> getShuoshuoList (
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        pageSize = (Objects.isNull(pageSize) || pageSize > 10) ? 10 : pageSize;
        return Result.success(shuoshuoService.getShuoshuoList(page, pageSize));
    }

    @ApiOperation("获取说说单条信息")
    @GetMapping("/getSingleShuoshuoByString")
    public StandardResult<ShuoshuoListDto> getShuoshuoInfoByString (String id) throws CustomException {
        return Result.success(shuoshuoService.getShuoshuoInfoByString(id));
    }

    @ApiOperation("获取说说单条信息")
    @GetMapping("/getSingleShuoshuo/{id}")
    public StandardResult<ShuoshuoListDto> getShuoshuoInfo (@PathVariable Integer id) throws CustomException {
        return Result.success(shuoshuoService.getShuoshuoInfo(id));
    }

    @ApiOperation("评论说说")
    @PostMapping("/doShuoComment")
    public StandardResult<Void> doShuoComment(@Validated @RequestBody ShuoshuoCommentAddDto shuoshuoCommentAddDto) throws CustomException {
        shuoshuoService.doShuoComment(shuoshuoCommentAddDto);
        return Result.success();
    }

    @ApiOperation("获取说说评论")
    @GetMapping("/getShuoCommentList")
    public StandardResult<List<CommentListDto>> getShuoCommentList(
            String id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        if (Objects.isNull(page) || page < 1) page = 1;
        if (Objects.isNull(pageSize) || pageSize > 10) pageSize = 5;
        return Result.success(shuoshuoService.getShuoCommentList(id, page, pageSize));
    }

    @ApiOperation("删除说说评论")
    @DeleteMapping("/delShuoComment/{id}")
    public StandardResult<Void> delShuoComment(@PathVariable Integer id) throws CustomException {
        shuoshuoService.delShuoComment(id);
        return Result.success();
    }
}