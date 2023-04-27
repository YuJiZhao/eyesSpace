package com.eyes.eyesspace.job.report;

import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.queue.constant.QueueConstant;
import com.eyes.eyesspace.queue.model.MonthlyReportModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author eyesYeager
 * @date 2023/2/9 14:20
 */
@Component
public class MonthlyReport {
  private static final String SUBJECT = " | 月报";

  private final RabbitTemplate rabbitTemplate;

  private final TrackMapper trackMapper;

  private final CommentMapper commentMapper;

  public MonthlyReport(RabbitTemplate rabbitTemplate, TrackMapper trackMapper, CommentMapper commentMapper) {
    this.rabbitTemplate = rabbitTemplate;
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
    rabbitTemplate.convertAndSend(QueueConstant.EMAIL_REPORT_MONTHLY, new MonthlyReportModel(
        SUBJECT,
        trackMapper.getVisitNumByTime(lastFirstDayStr, currentFirstDayStr),
        trackMapper.getVisitorNumByTime(lastFirstDayStr, currentFirstDayStr),
        commentMapper.getCommentNumByTime(lastFirstDayStr, currentFirstDayStr),
        commentMapper.getLeaveMsgNumByTime(lastFirstDayStr, currentFirstDayStr)
    ));
  }
}
