package com.eyes.eyesspace.service.site.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.site.model.dto.UserInfoDto;
import com.eyes.eyesspace.service.site.model.dto.UserUpdateInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

public interface InfoService {
    UserInfoDto getUserInfo() throws CustomException;

    void updateUserInfo(UserUpdateInfoDto userUpdateInfoDto) throws CustomException;

    FileUploadDto updateUserAvatar(MultipartFile multipartFile) throws CustomException;
}
