package com.eyes.eyesspace.persistent.po;

import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/6/1 15:10
 */
@Data
public class FriendOperatePO {
  private String email;

  private String name;

  private String introduce;

  private String avatar;

  private String address;

  private Integer status;
}
