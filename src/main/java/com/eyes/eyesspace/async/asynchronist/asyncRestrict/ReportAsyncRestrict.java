package com.eyes.eyesspace.async.asynchronist.asyncRestrict;

import com.eyes.eyesspace.async.model.DailyReportModel;
import com.eyes.eyesspace.async.model.MonthlyReportModel;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:30
 */

public interface ReportAsyncRestrict {

  /**
   * 异步发送日报
   * @param dailyReportModel
   */
  void sendDailyReport(DailyReportModel dailyReportModel);

  /**
   * 异步发送月报
   * @param monthlyReportModel
   */
  void sendMonthlyReport(MonthlyReportModel monthlyReportModel);
}
