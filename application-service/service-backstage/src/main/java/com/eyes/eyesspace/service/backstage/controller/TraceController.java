package com.eyes.eyesspace.service.backstage.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.backstage.model.vo.AddSpaceVisitVo;
import com.eyes.eyesspace.service.backstage.service.TraceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "埋点模块")
@RestController
@RequestMapping("/backstage/trace")
@Validated
public class TraceController {
    private final TraceService traceService;

    public TraceController(TraceService traceService) {
        this.traceService = traceService;
    }

    @ApiOperation("添加网站访问数据")
    @PostMapping("/addSpaceVisit")
    public StandardResult<Void> spaceVisit(@Validated @RequestBody AddSpaceVisitVo addSpaceVisitVo) throws CustomException {
        traceService.addSpaceVisit(addSpaceVisitVo);
        return Result.success();
    }
}
