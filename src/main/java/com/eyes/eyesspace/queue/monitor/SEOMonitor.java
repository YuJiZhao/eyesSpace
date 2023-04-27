package com.eyes.eyesspace.queue.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * SEO优化相关任务监听
 * @author eyesYeager
 * @date 2023/2/9 13:45
 */

@Slf4j
@RefreshScope
@Component
public class SEOMonitor {

  /**
   * TODO: 等前端用nuxt重构后再做
   */
//  @RabbitListener(queues = QueueConstant.SEO_URL_SUBMIT)
  public void sendUserCommentNotice() {

  }

  /**
   * 百度
   */
  private void submitBaiDu(String url) {

  }

  /**
   * Bing
   */
  private void submitBing(String url) {

  }

  /**
   * Google
   */
  private void submitGoogle(String url) {

  }

  /**
   * 360
   */
  private void submit360(String url) {

  }

  /**
   * 搜狗
   */
  private void submitSouGou(String url) {

  }
}
