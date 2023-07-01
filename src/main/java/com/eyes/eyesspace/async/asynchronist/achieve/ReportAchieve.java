package com.eyes.eyesspace.async.asynchronist.achieve;

import com.eyes.eyesTools.service.email.EmailSender;
import com.eyes.eyesspace.async.asynchronist.asyncRestrict.ReportAsyncRestrict;
import com.eyes.eyesspace.async.model.DailyReportModel;
import com.eyes.eyesspace.async.model.MonthlyReportModel;
import com.eyes.eyesspace.async.template.EmailDailyReportTemplate;
import lombok.extern.slf4j.Slf4j;
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
public class ReportAchieve implements ReportAsyncRestrict {
  @Value("${app.name-cn}")
  private String appName;

  @Value("${app.author-cn}")
  private String authorCN;

  @Value("${app.author-email}")
  private String authorEmail;

  private final EmailSender emailSender;

  public ReportAchieve(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendDailyReport(DailyReportModel dailyReportModel) {
    emailSender.sendMail(
        authorEmail,
        appName + dailyReportModel.getSubject(),
        new EmailDailyReportTemplate(
            appName + dailyReportModel.getSubject(),
            authorCN,
            dailyReportModel.getVisitNum(),
            dailyReportModel.getVisitorNum(),
            dailyReportModel.getCommentNum(),
            dailyReportModel.getLeaveMsgNum()
        ).getTemplate()
    );
  }

  @Override
  public void sendMonthlyReport(MonthlyReportModel monthlyReportModel) {

  }
}
