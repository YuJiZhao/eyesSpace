package com.eyes.eyesspace.service.entertain.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.entertain.module.music.dto.UserMusicInfoDto;

public interface MusicUserService {
    UserMusicInfoDto getMusicInfoByUser() throws CustomException;

    void doUserLike(String id) throws CustomException;
}
