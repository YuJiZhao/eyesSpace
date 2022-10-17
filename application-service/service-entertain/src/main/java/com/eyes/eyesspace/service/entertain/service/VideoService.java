package com.eyes.eyesspace.service.entertain.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoAddDto;
import com.eyes.eyesspace.service.entertain.module.video.vo.VideoAddVo;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    VideoAddDto addVideo(VideoAddVo videoAddVo) throws CustomException;

    FileUploadDto addVideoCover(MultipartFile multipartFile) throws CustomException;

    FileUploadDto addVideoFile(MultipartFile multipartFile) throws CustomException;

    List<VideoListDto> getVideoList(Integer page, Integer pageSize);

    VideoListInfoDto getVideoListInfo();

    VideoInfoDto getVideoInfo(Integer id);
}
