package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/9/25 9:51
 */
@Data
public class JokeAddDTO {
  private Long id;

  private String urlList;

  private Long categoryId;

  private Integer status;
}
