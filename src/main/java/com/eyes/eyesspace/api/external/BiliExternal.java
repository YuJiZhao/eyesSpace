package com.eyes.eyesspace.api.external;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.httpUtils.HttpUtils;
import com.eyes.eyesspace.api.result.BiliVideoInfoResult;
import java.util.HashMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * bilibili 相关拓展接口
 * @bug: 暂时不可用
 * @author eyesYeager
 * @date 2023/5/19 20:53
 */
@Component
public class BiliExternal {
  // bilibili的视频直链接口 api url
  // 官方链接：https://api.aagtool.top/api/bzspjx
  private static final String BILI_VIDEO_API_BASE_URL = "https://api.aagtool.top/api/bzspjx";

  // bilibili视频页面地址
  private static final String BILI_VIDEO_BASE_URL = "https://www.bilibili.com/video/";

  private final HttpUtils httpUtils;

  public BiliExternal(HttpUtils httpUtils) {
    this.httpUtils = httpUtils;
  }

  /**
   * 获取bilibili指定视频的相关信息
   * @param bv 视频bv号
   */
  public BiliVideoInfoResult getBiliVideoInfo(String bv) throws CustomException {
    try {
      String response = httpUtils.getResponse(
          BILI_VIDEO_API_BASE_URL,
          HttpMethod.GET,
          MediaType.APPLICATION_FORM_URLENCODED,
          new HashMap<String, String>(){{
            put("url", BILI_VIDEO_BASE_URL + bv);
          }},
          new HashMap<String, String>(){{  // 经过测试，必须携带这个请求头，否则无法请求
            put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
          }});
      BiliVideoInfoResult result = JSON.parseObject(response, BiliVideoInfoResult.class);
      if (!BiliVideoInfoResult.SUCCESS_CODE.equals(result.getCode())) {
        throw new Exception(result.getTitle());
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw new CustomException("fail to execute getBiliVideoInfo(" + bv + "), error:" + e.getMessage());
    }
  }
}
