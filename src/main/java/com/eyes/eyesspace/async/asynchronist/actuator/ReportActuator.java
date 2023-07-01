package com.eyes.eyesspace.async.asynchronist.actuator;

import com.eyes.eyesspace.async.asynchronist.asyncRestrict.ReportAsyncRestrict;
import com.eyes.eyesspace.async.asynchronist.pool.SingleThreadPool;
import com.eyes.eyesspace.async.model.DailyReportModel;
import com.eyes.eyesspace.async.model.MonthlyReportModel;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:31
 */
@Component
public class ReportActuator implements ReportAsyncRestrict {
  private final ExecutorService singleThreadPool = SingleThreadPool.getExecutor();

  @Resource
  private ReportAsyncRestrict reportAchieve;

  @Override
  public void sendDailyReport(DailyReportModel dailyReportModel) {
    singleThreadPool.execute(() -> reportAchieve.sendDailyReport(dailyReportModel));
  }

  @Override
  public void sendMonthlyReport(MonthlyReportModel monthlyReportModel) {
    singleThreadPool.execute(() -> reportAchieve.sendMonthlyReport(monthlyReportModel));
  }
}
