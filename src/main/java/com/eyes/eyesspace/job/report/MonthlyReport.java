package com.eyes.eyesspace.job.report;

import com.eyes.eyesspace.async.asynchronist.asyncRestrict.ReportAsyncRestrict;
import com.eyes.eyesspace.async.model.MonthlyReportModel;
import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 月报
 * @author eyesYeager
 * @date 2023/2/9 14:20
 */
@Component
public class MonthlyReport {
  private static final String SUBJECT = " | 月报";

  private final TrackMapper trackMapper;

  private final CommentMapper commentMapper;

  @Resource
  private ReportAsyncRestrict reportActuator;

  public MonthlyReport(TrackMapper trackMapper, CommentMapper commentMapper) {
    this.trackMapper = trackMapper;
    this.commentMapper = commentMapper;
  }

  @Scheduled(cron="0 0 0 28-31 * ?")
  public void execute(){
    // 检查是否是最后一天(@Scheduled不支持L，因此只好这么做)
    final Calendar c = Calendar.getInstance();
    if (c.get(Calendar.DATE) != c.getActualMaximum(Calendar.DATE)) {
      return;
    }

    // 本月第一天
    LocalDateTime date = LocalDateTime.now();
    LocalDateTime currentFirstDay = date.with(TemporalAdjusters.firstDayOfMonth());
    String currentFirstDayStr = currentFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    // 上月第一天
    LocalDateTime lastDate = date.minusMonths(1);
    LocalDateTime lastFirstDay = lastDate.with(TemporalAdjusters.firstDayOfMonth());
    String lastFirstDayStr = lastFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    // 邮件通知
    reportActuator.sendMonthlyReport(new MonthlyReportModel(
        SUBJECT,
        trackMapper.getVisitNumByTime(lastFirstDayStr, currentFirstDayStr),
        trackMapper.getVisitorNumByTime(lastFirstDayStr, currentFirstDayStr),
        commentMapper.getCommentNumByTime(lastFirstDayStr, currentFirstDayStr),
        commentMapper.getLeaveMsgNumByTime(lastFirstDayStr, currentFirstDayStr)
    ));
  }
}
