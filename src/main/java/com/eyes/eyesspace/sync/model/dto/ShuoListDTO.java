package com.eyes.eyesspace.sync.model.dto;

import com.eyes.eyesspace.sync.model.bean.HomeListBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

/**
 * @author eyesYeager
 * @date 2023/4/28 15:13
 */

@Data
public class ShuoListDTO implements HomeListBean {
  private String id;

  @JsonIgnore
  private Integer originalId;

  private String content;

  private List<String> picList;

  private Integer views;

  private Integer comments;

  private Integer status;

  private String createTime;
}
