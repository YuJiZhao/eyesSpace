package com.eyes.eyesspace.service.entertain.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoAddDto;
import com.eyes.eyesspace.service.entertain.module.video.vo.VideoAddVo;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListInfoDto;
import com.eyes.eyesspace.service.entertain.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/entertain/video/video")
@Api(tags = "视频模块")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation("添加视频")
    @PostMapping("/addVideo")
    public StandardResult<VideoAddDto> addVideo(@RequestBody VideoAddVo videoAddVo) throws CustomException {
        return Result.success(videoService.addVideo(videoAddVo));
    }

    @ApiOperation("上传视频封面")
    @PostMapping("/addVideoCover")
    public StandardResult<FileUploadDto> addVideoCover(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(videoService.addVideoCover(multipartFile));
    }

    @ApiOperation("上传视频文件")
    @PostMapping("/addVideoFile")
    public StandardResult<FileUploadDto> addVideoFile(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(videoService.addVideoFile(multipartFile));
    }

    @ApiOperation("获取视频列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", defaultValue="1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue="10")
    })
    @GetMapping("/getVideoList")
    public StandardResult<List<VideoListDto>> getVideoList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        pageSize = (Objects.isNull(pageSize) || pageSize > 10) ? 10 : pageSize;
        return Result.success(videoService.getVideoList(page, pageSize));
    }

    @ApiOperation("获取视频总体信息")
    @GetMapping("/getVideoListInfo")
    public StandardResult<VideoListInfoDto> getVideoListInfo() {
        return Result.success(videoService.getVideoListInfo());
    }

    @ApiOperation("获取单个视频详细信息")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/getVideoInfo")
    public StandardResult<VideoInfoDto> getVideoInfo(Integer id) {
        return Result.success(videoService.getVideoInfo(id));
    }
}
