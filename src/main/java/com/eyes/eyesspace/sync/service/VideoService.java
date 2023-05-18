package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.sync.model.request.VideoAddBatchBiliRequest;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddCoverFailVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddVO;
import com.eyes.eyesspace.sync.model.vo.VideoListInfoVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    VideoAddVO addVideo(VideoAddRequest videoAddRequest) throws CustomException;

    List<VideoAddCoverFailVO> addBatchBiliVideo(List<VideoAddBatchBiliRequest> videoAddBatchBiliRequestList);

    FileUploadVO addVideoCover(MultipartFile multipartFile) throws CustomException;

    FileUploadVO addVideoFile(MultipartFile multipartFile) throws CustomException;

    List<VideoListDTO> getVideoList(Integer page, Integer pageSize);

    VideoListInfoVO getVideoListInfo();

    VideoInfoDTO getVideoInfo(Integer id);

    UserVideoInfoVO getVideoInfoByUser() throws CustomException;

    void doUserLike(String id) throws CustomException;
}
