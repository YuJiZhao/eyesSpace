package com.eyes.eyesspace.service.entertain.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.entertain.module.music.Vo.MusicAddVo;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicAddDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {
    MusicAddDto addMusic(MusicAddVo musicAddVo) throws CustomException;

    FileUploadDto addMusicCover(MultipartFile multipartFile) throws CustomException;

    FileUploadDto addMusicFile(MultipartFile multipartFile) throws CustomException;

    List<MusicListDto> getMusicList(Integer page, Integer pageSize);

    MusicListInfoDto getMusicListInfo();

    MusicInfoDto getMusicInfo(Integer id);

    String getMusicLrc(String id) throws CustomException;
}
