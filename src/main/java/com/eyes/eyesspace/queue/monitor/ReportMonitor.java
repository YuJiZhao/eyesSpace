package com.eyes.eyesspace.queue.monitor;

import com.eyes.eyesTools.service.email.EmailSender;
import com.eyes.eyesspace.queue.constant.QueueConstant;
import com.eyes.eyesspace.queue.model.DailyReportModel;
import com.eyes.eyesspace.queue.model.MonthlyReportModel;
import com.eyes.eyesspace.utils.email.EmailDailyReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 数据报告相关任务监听
 * @author eyesYeager
 * @date 2023/2/9 15:01
 */

@Slf4j
@RefreshScope
@Component
public class ReportMonitor {
  @Value("${app.name-cn}")
  private String appName;

  @Value("${app.author-cn}")
  private String authorCN;

  @Value("${app.author-email}")
  private String authorEmail;

  private final EmailSender emailSender;

  public ReportMonitor(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  @RabbitListener(queues = QueueConstant.EMAIL_REPORT_DAILY)
  public void sendDailyReport(DailyReportModel dailyReportModel) {
    emailSender.sendMail(
        authorEmail,
        appName + dailyReportModel.getSubject(),
        new EmailDailyReport(
            appName + dailyReportModel.getSubject(),
            authorCN,
            dailyReportModel.getVisitNum(),
            dailyReportModel.getVisitorNum(),
            dailyReportModel.getCommentNum(),
            dailyReportModel.getLeaveMsgNum()
        ).getTemplate()
    );
  }

  @RabbitListener(queues = QueueConstant.EMAIL_REPORT_MONTHLY)
  public void sendMonthlyReport(MonthlyReportModel monthlyReportModel) {

  }
}
