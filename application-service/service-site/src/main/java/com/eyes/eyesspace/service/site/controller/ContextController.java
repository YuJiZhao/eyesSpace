package com.eyes.eyesspace.service.site.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.site.model.dto.ContextDto;
import com.eyes.eyesspace.service.site.service.ContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/site/context")
@Api(tags = "配置文案模块")
public class ContextController {
    private final ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @ApiOperation("获取所有配置文案")
    @GetMapping("/getContext")
    public StandardResult<ContextDto> getContext() throws CustomException {
        return Result.success(contextService.getContext());
    }
}