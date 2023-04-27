package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.MusicAddVO;
import com.eyes.eyesspace.persistent.dto.MusicInfoDTO;
import com.eyes.eyesspace.persistent.dto.MusicListDTO;
import com.eyes.eyesspace.sync.model.vo.MusicListInfoVO;
import com.eyes.eyesspace.sync.model.vo.UserMusicInfoVO;
import com.eyes.eyesspace.sync.model.request.MusicAddRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {
    MusicAddVO addMusic(MusicAddRequest musicAddRequest) throws CustomException;

    FileUploadVO addMusicCover(MultipartFile multipartFile) throws CustomException;

    FileUploadVO addMusicFile(MultipartFile multipartFile) throws CustomException;

    List<MusicListDTO> getMusicList(Integer page, Integer pageSize);

    MusicListInfoVO getMusicListInfo();

    MusicInfoDTO getMusicInfo(Integer id);

    String getMusicLrc(String id) throws CustomException;

    UserMusicInfoVO getMusicInfoByUser() throws CustomException;

    void doUserLike(String id) throws CustomException;
}
