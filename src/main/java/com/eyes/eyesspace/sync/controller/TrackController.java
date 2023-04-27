package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.TrackVisitAddRequest;
import com.eyes.eyesspace.sync.service.TrackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:49
 */
@Api(tags = "特有埋点模块")
@RestController
@RequestMapping("/track")
@Validated
public class TrackController {
  private final TrackService trackService;

  public TrackController(TrackService trackService) {
    this.trackService = trackService;
  }

  @ApiOperation("进站埋点")
  @Permission
  @PostMapping("/visitAdd")
  public void spaceVisit(@Validated @RequestBody TrackVisitAddRequest trackVisitAddRequest) throws CustomException {
    trackService.addSpaceVisit(trackVisitAddRequest);
  }
}
