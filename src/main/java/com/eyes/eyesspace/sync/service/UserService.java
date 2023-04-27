package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.UserInfoVO;
import com.eyes.eyesspace.sync.model.request.UserInfoUpdateRequest;
import com.eyes.eyesspace.sync.model.vo.UserAvatarUpdateVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:48
 */

public interface UserService {
  UserInfoVO getUserInfo() throws CustomException;

  void updateUserInfo(UserInfoUpdateRequest userInfoUpdateRequest) throws CustomException;

  UserAvatarUpdateVO updateUserAvatar(MultipartFile file) throws CustomException;
}
