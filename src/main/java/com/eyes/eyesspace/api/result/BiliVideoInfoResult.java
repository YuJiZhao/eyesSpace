package com.eyes.eyesspace.api.result;

import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/5/19 21:07
 */

@Data
public class BiliVideoInfoResult {
  public static final String SUCCESS_CODE = "0";

  // 状态码，成功时为0
  private String code;

  // 视频标题，获取失败时为错误信息
  private String title;

  // 视频封面链接(不要被变量名迷惑)
  private String first_frame;

  // 视频播放量
  private String stat_view;

  // 视频直链
  private String url;
}
