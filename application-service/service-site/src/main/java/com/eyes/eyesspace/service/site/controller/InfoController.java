package com.eyes.eyesspace.service.site.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.site.model.dto.UserInfoDto;
import com.eyes.eyesspace.service.site.model.dto.UserUpdateInfoDto;
import com.eyes.eyesspace.service.site.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "配置文案模块")
@RestController
@RequestMapping("/site/user/info")
@Validated
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @ApiOperation("获取用户基本信息")
    @GetMapping("/getUserInfo")
    public StandardResult<UserInfoDto> getUserInfo() throws CustomException {
        return Result.success(infoService.getUserInfo());
    }

    @ApiOperation("修改用户基本信息")
    @PutMapping("/updateUserInfo")
    public StandardResult<Void> updateUserInfo(@Validated @RequestBody UserUpdateInfoDto userUpdateInfoDto) throws CustomException {
        infoService.updateUserInfo(userUpdateInfoDto);
        return Result.success();
    }

    @ApiOperation("修改用户头像")
    @PostMapping("/updateUserAvatar")
    public StandardResult<FileUploadDto> updateUserAvatar(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
        return Result.success(infoService.updateUserAvatar(multipartFile));
    }
}
