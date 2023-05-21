package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.VersionInfoVO;
import com.eyes.eyesspace.sync.model.vo.VersionListVO;
import com.eyes.eyesspace.sync.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 * @date 2023/5/20 16:28
 */
@Api(tags = "版本模块")
@RefreshScope
@Validated
@RestController
@RequestMapping("/version")
public class VersionController {

  @Value("${business.page-size.version:6}")
  private Integer versionPageSize;

  private final VersionService versionService;

  public VersionController(VersionService versionService) {
    this.versionService = versionService;
  }

  @ApiOperation("获取版本信息")
  @Limiter
  @Permission
  @GetMapping("/getVersionInfo")
  public Result<VersionInfoVO> getVersionInfo() throws CustomException {
    return Result.success(versionService.getVersionInfo());
  }

  @ApiOperation("获取版本列表")
  @Limiter
  @Permission
  @GetMapping("/getVersionList")
  public Result<VersionListVO> getVersionList(@RequestParam(required = false) Integer page) throws CustomException {
    page = (Objects.isNull(page) || page < 1) ? 1 : page;
    return Result.success(versionService.getVersionList(page, versionPageSize));
  }
}
