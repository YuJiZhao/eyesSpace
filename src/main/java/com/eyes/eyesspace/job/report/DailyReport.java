package com.eyes.eyesspace.job.report;

import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.queue.constant.QueueConstant;
import com.eyes.eyesspace.queue.model.DailyReportModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 日报
 * @author eyesYeager
 * @date 2023/2/9 13:53
 */

@Component
public class DailyReport {
  private static final String SUBJECT = " | 日报";

  private final RabbitTemplate rabbitTemplate;

  private final TrackMapper trackMapper;

  private final CommentMapper commentMapper;

  public DailyReport(RabbitTemplate rabbitTemplate, TrackMapper trackMapper, CommentMapper commentMapper) {
    this.rabbitTemplate = rabbitTemplate;
    this.trackMapper = trackMapper;
    this.commentMapper = commentMapper;
  }

  @Scheduled(cron="0 0 0 * * ?")
  public void execute(){
    // 今天
    Date date = new Date();
    SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
    String today = dfDate.format(date);

    // 昨天
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_MONTH, -1);
    String yesterday = dfDate.format(c.getTime());

    // 邮件通知
    rabbitTemplate.convertAndSend(QueueConstant.EMAIL_REPORT_DAILY, new DailyReportModel(
        SUBJECT,
        trackMapper.getVisitNumByTime(yesterday, today),
        trackMapper.getVisitorNumByTime(yesterday, today),
        commentMapper.getCommentNumByTime(yesterday, today),
        commentMapper.getLeaveMsgNumByTime(yesterday, today)
    ));
  }
}
