package com.eyes.eyesspace.service.site.service.impl;

import com.eyes.eyesspace.common.service.service.FileService;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.tool.context.MediaContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.site.mapper.InfoMapper;
import com.eyes.eyesspace.service.site.model.dto.UserInfoDto;
import com.eyes.eyesspace.service.site.model.dto.UserUpdateInfoDto;
import com.eyes.eyesspace.service.site.service.InfoService;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class InfoServiceImpl implements InfoService {
    private final InfoMapper infoMapper;
    private final FileService fileService;
    private static final String AVATAR_PATH = ConfigContext.getString("AVATAR_PATH");

    public InfoServiceImpl(InfoMapper infoMapper, FileService fileService) {
        this.infoMapper = infoMapper;
        this.fileService = fileService;
    }

    @Override
    public UserInfoDto getUserInfo() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String uid;
        if(Objects.isNull(uid = request.getHeader(ConfigContext.getString("ID_HEADER")))) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }
        return infoMapper.getUserInfo(Integer.valueOf(uid));
    }

    @Override
    public void updateUserInfo(UserUpdateInfoDto userUpdateInfoDto) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String uid;
        if(Objects.isNull(uid = request.getHeader(ConfigContext.getString("ID_HEADER")))) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }

        if (!infoMapper.updateUserInfo(Integer.valueOf(uid), userUpdateInfoDto.getName())) {
            throw new CustomException("修改信息失败!");
        }
    }

    @Override
    public FileUploadDto updateUserAvatar(MultipartFile multipartFile) throws CustomException {
        Integer id = IdentityUtils.getRequestIdNeed();

        String originalFilename = multipartFile.getOriginalFilename();
        if (Objects.isNull(originalFilename)) {
            throw new CustomException("文件错误");
        }
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!MediaContext.image.contains(fileType)) {
            throw new CustomException("图片格式不支持");
        }

        FileUploadDto fileUpload = fileService.sUpload(multipartFile, AVATAR_PATH);
        if (!infoMapper.updateAvatar(fileUpload.getUrl(), id)) {
            throw new CustomException("更新头像失败");
        }
        return fileUpload;
    }
}
