package com.eyes.eyesspace.service.entertain.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.entertain.module.video.dto.UserVideoInfoDto;

public interface VideoUserService {
    UserVideoInfoDto getVideoInfoByUser() throws CustomException;

    void doUserLike(String id) throws CustomException;
}
