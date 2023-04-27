package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.TrackVisitAddRequest;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:53
 */

public interface TrackService {
  void addSpaceVisit(TrackVisitAddRequest trackVisitAddRequest) throws CustomException;
}
