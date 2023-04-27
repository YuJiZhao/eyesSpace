package com.eyes.eyesspace.queue.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eyesYeager
 * @date 2023/2/9 20:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportModel implements Serializable {
  private String subject;

  private Integer visitNum;

  private Integer visitorNum;

  private Integer commentNum;

  private Integer leaveMsgNum;
}