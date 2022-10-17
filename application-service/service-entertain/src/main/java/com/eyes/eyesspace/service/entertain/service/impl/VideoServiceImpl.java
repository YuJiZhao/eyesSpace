package com.eyes.eyesspace.service.entertain.service.impl;

import com.eyes.eyesspace.common.service.service.FileService;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.entertain.mapper.VideoMapper;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoAddDto;
import com.eyes.eyesspace.service.entertain.module.video.po.VideoDataPo;
import com.eyes.eyesspace.service.entertain.module.video.vo.VideoAddVo;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListInfoDto;
import com.eyes.eyesspace.service.entertain.service.VideoService;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoMapper videoMapper;
    private final FileService fileService;
    private static final String VIDEO_PATH = ConfigContext.getString("VIDEO_PATH");
    private static final String VIDEO_COVER_PATH = ConfigContext.getString("VIDEO_COVER_PATH");

    public VideoServiceImpl(VideoMapper videoMapper, FileService fileService) {
        this.videoMapper = videoMapper;
        this.fileService = fileService;
    }

    @Override
    public VideoAddDto addVideo(VideoAddVo videoAddVo) throws CustomException {
        if (!videoMapper.addVideo(videoAddVo)) {
            throw new CustomException("视频添加失败！");
        }
        return new VideoAddDto(videoAddVo.getId());
    }

    @Override
    public FileUploadDto addVideoCover(MultipartFile multipartFile) throws CustomException {
        return fileService.sUpload(multipartFile, VIDEO_COVER_PATH);
    }

    @Override
    public FileUploadDto addVideoFile(MultipartFile multipartFile) throws CustomException {
        return fileService.bUpload(multipartFile, VIDEO_PATH);
    }

    @Override
    public List<VideoListDto> getVideoList(Integer page, Integer pageSize) {
        return videoMapper.getVideoList((page - 1) * pageSize, pageSize);
    }

    @Override
    public VideoListInfoDto getVideoListInfo() {
        VideoDataPo videoDataPo = videoMapper.getVideoData();
        return new VideoListInfoDto(
                videoMapper.getVideoListInfo(null),
                videoMapper.getVideoListInfo(0),
                videoMapper.getVideoListInfo(1),
                videoMapper.getVideoListInfo(2),
                videoDataPo.getViewsNum(),
                videoDataPo.getLikesNum(),
                videoDataPo.getCommentsNum()
        );
    }

    @Override
    public VideoInfoDto getVideoInfo(Integer id) {
        return videoMapper.getVideoInfo(id, "ROLE_admin");
    }
}
