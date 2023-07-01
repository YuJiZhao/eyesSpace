package com.eyes.eyesspace.async.asynchronist.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author eyesYeager
 * @date 2023/7/1 17:38
 */

public class SingleThreadPool {
  private final static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

  public static ExecutorService getExecutor() {
    return singleThreadExecutor;
  }
}
