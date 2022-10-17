package com.eyes.eyesspace.service.entertain.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.entertain.module.music.Vo.MusicAddVo;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicAddDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListInfoDto;
import com.eyes.eyesspace.service.entertain.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Api(tags = "歌曲模块")
@RestController
@RequestMapping("/entertain/music/music")
@Validated
public class MusicController {
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @ApiOperation("添加歌曲")
    @PostMapping("/addMusic")
    public StandardResult<MusicAddDto> addMusic(@RequestBody MusicAddVo musicAddVo) throws CustomException {
        return Result.success(musicService.addMusic(musicAddVo));
    }

    @ApiOperation("上传歌曲封面")
    @PostMapping("/addMusicCover")
    public StandardResult<FileUploadDto> addMusicCover(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(musicService.addMusicCover(multipartFile));
    }

    @ApiOperation("上传歌曲文件")
    @PostMapping("/addMusicFile")
    public StandardResult<FileUploadDto> addMusicFile(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(musicService.addMusicFile(multipartFile));
    }

    @ApiOperation("获取歌曲列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", defaultValue="1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue="10")
    })
    @GetMapping("/getMusicList")
    public StandardResult<List<MusicListDto>> getMusicList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        pageSize = (Objects.isNull(pageSize) || pageSize > 10) ? 10 : pageSize;
        return Result.success(musicService.getMusicList(page, pageSize));
    }

    @ApiOperation("获取歌曲总体信息")
    @GetMapping("/getMusicListInfo")
    public StandardResult<MusicListInfoDto> getMusicListInfo() {
        return Result.success(musicService.getMusicListInfo());
    }

    @ApiOperation("获取单个歌曲详细信息")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/getMusicInfo")
    public StandardResult<MusicInfoDto> getMusicInfo(Integer id) {
        return Result.success(musicService.getMusicInfo(id));
    }

    @ApiOperation("获取单个歌曲歌词")
    @ApiImplicitParam(name = "id", value = "id")
    @GetMapping("/getMusicLrc")
    public String getMusicLrc(@NotNull(message = "id不能为空") String id) throws CustomException {
        return musicService.getMusicLrc(id);
    }
}
