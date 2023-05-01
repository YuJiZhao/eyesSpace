package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.UserInfoVO;
import com.eyes.eyesspace.sync.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 * @date 2023/2/3 13:24
 */

@Api(tags = "用户模块")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ApiOperation("获取用户基本信息")
  @Limiter
  @Permission(PermissionEnum.USER)
  @GetMapping("/getUserInfo")
  public Result<UserInfoVO> getUserInfo() throws CustomException {
    return Result.success(userService.getUserInfo());
  }

//  @ApiOperation("修改用户基本信息")
//  @Limiter
//  @Permission(PermissionEnum.USER)
//  @PutMapping("/updateUserInfo")
//  public Result<Void> updateUserInfo(@Validated @RequestBody UserInfoUpdateRequest userInfoUpdateRequest) throws CustomException {
//    userService.updateUserInfo(userInfoUpdateRequest);
//    return Result.success();
//  }

//  @ApiOperation("修改用户头像")
//  @Limiter
//  @Permission(PermissionEnum.USER)
//  @PostMapping("/updateUserAvatar")
//  public Result<UserAvatarUpdateVO> updateUserAvatar(@RequestPart("file") MultipartFile file) throws CustomException {
//    return Result.success(userService.updateUserAvatar(file));
//  }
}
