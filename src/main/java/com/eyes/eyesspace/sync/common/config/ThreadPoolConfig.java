package com.eyes.eyesspace.sync.common.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author eyesYeager
 * @date 2023/6/1 10:17
 */
@Configuration
public class ThreadPoolConfig {
  @Bean
  public ExecutorService singleThreadPool() {
    return Executors.newSingleThreadExecutor();
  }

  @Bean
  public ScheduledExecutorService scheduledThreadPool() {
    return Executors.newSingleThreadScheduledExecutor();
  }
}
