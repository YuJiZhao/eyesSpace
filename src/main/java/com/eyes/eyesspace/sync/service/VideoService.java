package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddVO;
import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.sync.model.vo.VideoListInfoVO;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    VideoAddVO addVideo(VideoAddRequest videoAddRequest) throws CustomException;

    FileUploadVO addVideoCover(MultipartFile multipartFile) throws CustomException;

    FileUploadVO addVideoFile(MultipartFile multipartFile) throws CustomException;

    List<VideoListDTO> getVideoList(Integer page, Integer pageSize);

    VideoListInfoVO getVideoListInfo();

    VideoInfoDTO getVideoInfo(Integer id);

    UserVideoInfoVO getVideoInfoByUser() throws CustomException;

    void doUserLike(String id) throws CustomException;
}
