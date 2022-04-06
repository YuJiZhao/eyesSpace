package com.eyes.eyesblog.controller;

import com.eyes.eyesblog.api.Result;
import com.eyes.eyesblog.entity.Copywriting;
import com.eyes.eyesblog.service.impl.CopywritingServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@Api(tags={"文案接口"})
@RequestMapping("/copywriting")
public class CopywritingController {
    @Autowired
    private CopywritingServiceImpl  copywritingService;

    @GetMapping
    public Result<List<Copywriting>> getCopywriting() {
        return Result.success(copywritingService.getCopyWriting());
    }
}