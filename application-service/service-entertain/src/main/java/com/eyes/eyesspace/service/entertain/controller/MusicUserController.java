package com.eyes.eyesspace.service.entertain.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.entertain.module.music.dto.UserMusicInfoDto;
import com.eyes.eyesspace.service.entertain.service.MusicUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/entertain/music/user")
@Api(tags = "歌曲_用户模块")
@Validated
public class MusicUserController {
    private final MusicUserService musicUserService;

    public MusicUserController(MusicUserService musicUserService) {
        this.musicUserService = musicUserService;
    }

    @ApiOperation("获取指定歌曲信息")
    @GetMapping("/getMusicInfo")
    public StandardResult<UserMusicInfoDto> getMusicInfoByUser() throws CustomException {
        return Result.success(musicUserService.getMusicInfoByUser());
    }

    @ApiOperation("用户点赞音乐")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/doUserLike")
    public StandardResult<Void> doUserLike(@NotNull(message = "id不能为空") String id) throws CustomException {
        musicUserService.doUserLike(id);
        return Result.success();
    }
}
