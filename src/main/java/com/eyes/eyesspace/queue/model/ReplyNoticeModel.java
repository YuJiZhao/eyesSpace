package com.eyes.eyesspace.queue.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/2/9 10:00
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeModel implements Serializable {
  private String subject;

  private Integer replyId;

  private String url;
}
