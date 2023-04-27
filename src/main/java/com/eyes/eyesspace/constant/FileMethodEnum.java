package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eyesYeager
 * @date 2023/1/25 20:25
 */
@Getter
@AllArgsConstructor
public enum FileMethodEnum {
  UPLOAD(0),

  DELETE(1);

  private final int method;
}
