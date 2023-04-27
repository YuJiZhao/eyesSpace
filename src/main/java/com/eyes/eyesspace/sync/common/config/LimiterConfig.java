package com.eyes.eyesspace.sync.common.config;

import com.eyes.eyesAuth.limiter.LimiterAdvice;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eyesYeager
 * @date 2023/1/28 15:29
 */
@RefreshScope
@Configuration
public class LimiterConfig {
  @Value("${limiter.qps:100}")
  private Double QPS;

  @Value("${limiter.message}")
  private String message;

  @Bean
  public LimiterAdvice limiterAdvice() {
    LimiterAdvice limiterAdvice = new LimiterAdvice();
    limiterAdvice.setQPS(QPS);
    if (Objects.nonNull(message)) {
      limiterAdvice.setMessage(message);
    }
    return limiterAdvice;
  }
}
