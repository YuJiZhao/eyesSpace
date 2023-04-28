package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesspace.sync.model.vo.ContextAboutContentVO;
import com.eyes.eyesspace.sync.model.vo.ContextVO;
import com.eyes.eyesspace.sync.service.ContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eyesYeager
 */
@Api(tags = "配置模块")
@RestController
@RequestMapping("/context")
public class ContextController {
    private final ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @ApiOperation("获取网站配置")
    @Limiter
    @Permission
    @GetMapping("/getContext")
    public Result<ContextVO> getContext() throws CustomException {
        return Result.success(contextService.getContext());
    }

    @ApiOperation("获取关于页面内容")
    @Limiter
    @Permission
    @GetMapping("/getAboutContext")
    public Result<ContextAboutContentVO> getAboutContent() {
        return Result.success(contextService.getAboutContent());
    }
}