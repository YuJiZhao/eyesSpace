package com.eyes.eyesspace.async.asynchronist.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:38
 */

public class ScheduledThreadPool {
  private final static ScheduledExecutorService scheduledThreadExecutor = Executors.newSingleThreadScheduledExecutor();

  public static ScheduledExecutorService getExecutor() {
    return scheduledThreadExecutor;
  }
}
