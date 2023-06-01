package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 9:34
 */
@Data
public class FriendChainApplyDTO {
  private Long uid;

  private String email;

  private String name;

  private String introduce;

  private String avatar;

  private String address;
}
