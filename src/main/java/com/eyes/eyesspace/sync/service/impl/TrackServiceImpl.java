package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.utils.BrowserUtils;
import com.eyes.eyesTools.utils.IpUtils;
import com.eyes.eyesTools.utils.OSUtils;
import com.eyes.eyesTools.utils.WebUtils;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.sync.service.TrackService;
import com.eyes.eyesspace.sync.model.request.TrackVisitAddRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:53
 */
@Slf4j
@Service
public class TrackServiceImpl implements TrackService {
  private final TrackMapper trackMapper;

  public TrackServiceImpl(TrackMapper trackMapper) {
    this.trackMapper = trackMapper;
  }

  @Override
  public void addSpaceVisit(TrackVisitAddRequest trackVisitAddRequest) {
    HttpServletRequest request = WebUtils.getRequest();

    if (!trackMapper.addVisitLog(
        UserInfoHolder.getUid(),
        IpUtils.getIpAddr(request),
        OSUtils.osName(request),
        BrowserUtils.browserName(request),
        trackVisitAddRequest.getPath()
    )) { log.error("Website access data addition error"); }
  }
}
