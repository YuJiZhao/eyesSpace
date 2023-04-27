package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesAuth.thrift.generate.user.UserUpdateInfoReceiver;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.SensitiveInfoUtils;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.sync.common.result.ResultCode;
import com.eyes.eyesspace.sync.convert.UserConvert;
import com.eyes.eyesspace.sync.model.vo.UserInfoVO;
import com.eyes.eyesspace.sync.model.request.UserInfoUpdateRequest;
import com.eyes.eyesspace.sync.model.vo.UserAvatarUpdateVO;
import com.eyes.eyesspace.sync.service.UserService;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:48
 */
@Slf4j
@RefreshScope
@Service
public class UserServiceImpl implements UserService {
  @Value("${file.avatar-max-size:5120}")
  private Integer avatarMaxSize;

  private final TTClientPool ttClientPool;

  public UserServiceImpl(TTClientPool ttClientPool) {
    this.ttClientPool = ttClientPool;
  }

  @Override
  public UserInfoVO getUserInfo() throws CustomException {
    Long uid = UserInfoHolder.getUid();
    TTSocket ttSocket = null;
    try {
      ttSocket = ttClientPool.getConnect();
      UserInfoReturnee userInfo = ttSocket.getUserClient().getUserInfo(uid);
      ttClientPool.returnConnection(ttSocket);
      UserInfoVO userInfoVO = UserConvert.INSTANCE.returnee2Dto(userInfo);
      userInfoVO.setEmail(SensitiveInfoUtils.email(userInfoVO.getEmail()));
      return userInfoVO;
    } catch (TTCustomException e) {
      throw new CustomException(e.getMsg());
    } catch (Exception e) {
      ttClientPool.invalidateObject(ttSocket);
      e.printStackTrace();
      throw new CustomException(ResultCode.FAILURE);
    }
  }

  @Override
  public void updateUserInfo(UserInfoUpdateRequest userInfoUpdateRequest) throws CustomException {
    Long uid = UserInfoHolder.getUid();
    TTSocket ttSocket = null;
    try {
      UserUpdateInfoReceiver userUpdateInfoReceiver = new UserUpdateInfoReceiver(
          uid,
          userInfoUpdateRequest.getName()
      );
      ttSocket = ttClientPool.getConnect();
      ttSocket.getUserClient().updateUserInfo(userUpdateInfoReceiver);
      ttClientPool.returnConnection(ttSocket);
    } catch (TTCustomException e) {
      throw new CustomException(e.getMsg());
    } catch (Exception e) {
      ttClientPool.invalidateObject(ttSocket);
      e.printStackTrace();
      throw new CustomException(ResultCode.FAILURE);
    }
  }

  @Override
  public UserAvatarUpdateVO updateUserAvatar(MultipartFile file) throws CustomException {
    Long uid = UserInfoHolder.getUid();

    // 头像格式校验
    String originalFilename = file.getOriginalFilename();
    if (Objects.isNull(originalFilename)) {
      throw new CustomException("文件格式错误");
    }
    int lastIndex = originalFilename.lastIndexOf(".");
    if (lastIndex == -1) {
      throw new CustomException("文件格式错误");
    }
    String suffix = originalFilename.substring(lastIndex);
    if (!MediaConstant.imgContain(suffix)) {
      throw new CustomException("图片格式不支持");
    }

    // 头像大小校验
    long size = file.getSize();
    if (size / 1024 > avatarMaxSize) {
      throw new CustomException("图片过大！最大为:" + (avatarMaxSize / 1024) + "MB");
    }

    // 头像文件格式处理
    ByteBuffer bufferFile;
    try {
      byte[] byteFile = file.getBytes();
      bufferFile = ByteBuffer.wrap(byteFile);
    } catch (IOException e) {
      log.error("头像文件格式处理错误！");
      e.printStackTrace();
      throw new CustomException("程序异常");
    }

    // 修改头像
    TTSocket ttSocket = null;
    try {
      ttSocket = ttClientPool.getConnect();
      String url = ttSocket.getUserClient().updateUserAvatar(uid, bufferFile);
      ttClientPool.returnConnection(ttSocket);
      return new UserAvatarUpdateVO(url);
    } catch (TTCustomException e) {
      throw new CustomException(e.getMsg());
    } catch (Exception e) {
      ttClientPool.invalidateObject(ttSocket);
      e.printStackTrace();
      throw new CustomException(ResultCode.FAILURE);
    }
  }
}
