package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddVO;
import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.sync.model.vo.VideoListInfoVO;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import com.eyes.eyesspace.sync.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Api(tags = "视频模块")
@RefreshScope
@RestController
@RequestMapping("/video")
public class VideoController {
    @Value("${business.page-size.video:10}")
    private Integer videoPageSize;

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation("发布视频")
    @Permission(PermissionEnum.ADMIN)
    @PostMapping("/addVideo")
    public Result<VideoAddVO> addVideo(@RequestBody VideoAddRequest videoAddRequest) throws CustomException {
        return Result.success(videoService.addVideo(videoAddRequest));
    }

    @ApiOperation("上传视频封面")
    @Permission(PermissionEnum.ADMIN)
    @PostMapping("/addVideoCover")
    public Result<FileUploadVO> addVideoCover(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(videoService.addVideoCover(multipartFile));
    }

    @ApiOperation("上传视频文件")
    @Permission(PermissionEnum.ADMIN)
    @PostMapping("/addVideoFile")
    public Result<FileUploadVO> addVideoFile(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(videoService.addVideoFile(multipartFile));
    }

    @ApiOperation("获取视频列表")
    @Permission(PermissionEnum.ADMIN)
    @GetMapping("/getVideoList")
    public Result<List<VideoListDTO>> getVideoList(@RequestParam(required = false) Integer page) {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        return Result.success(videoService.getVideoList(page, videoPageSize));
    }

    @ApiOperation("获取视频总体信息")
    @Permission(PermissionEnum.ADMIN)
    @GetMapping("/getVideoListInfo")
    public Result<VideoListInfoVO> getVideoListInfo() {
        return Result.success(videoService.getVideoListInfo());
    }

    @ApiOperation("获取指定视频信息")
    @Permission(PermissionEnum.ADMIN)
    @GetMapping("/getVideoInfo")
    public Result<VideoInfoDTO> getVideoInfo(Integer id) {
        return Result.success(videoService.getVideoInfo(id));
    }

    @ApiOperation("获取随机视频信息")
    @Permission(PermissionEnum.USER)
    @GetMapping("/getVideoInfoByUser")
    public Result<UserVideoInfoVO> getVideoInfoByUser() throws CustomException {
        return Result.success(videoService.getVideoInfoByUser());
    }

    @ApiOperation("点赞/取消点赞视频")
    @Limiter
    @Permission(PermissionEnum.USER)
    @GetMapping("/doUserLike")
    public Result<Void> doUserLike(@NotNull(message = "id不能为空") String id) throws CustomException {
        videoService.doUserLike(id);
        return Result.success();
    }
}
