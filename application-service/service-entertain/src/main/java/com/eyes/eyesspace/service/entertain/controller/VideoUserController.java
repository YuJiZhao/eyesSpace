package com.eyes.eyesspace.service.entertain.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.entertain.module.video.dto.UserVideoInfoDto;
import com.eyes.eyesspace.service.entertain.service.VideoUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/entertain/video/user")
@Api(tags = "视频_用户模块")
@Validated
public class VideoUserController {
    private final VideoUserService videoUserService;

    public VideoUserController(VideoUserService videoUserService) {
        this.videoUserService = videoUserService;
    }

    @ApiOperation("获取单个视频信息")
    @GetMapping("/getVideoInfo")
    public StandardResult<UserVideoInfoDto> getVideoInfoByUser() throws CustomException {
        return Result.success(videoUserService.getVideoInfoByUser());
    }

    @ApiOperation("用户点赞视频")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/doUserLike")
    public StandardResult<Void> doUserLike(@NotNull(message = "id不能为空") String id) throws CustomException {
        videoUserService.doUserLike(id);
        return Result.success();
    }
}
