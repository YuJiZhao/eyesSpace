package com.eyes.eyesblog.controller;

import com.eyes.eyesblog.api.Result;
import com.eyes.eyesblog.api.ResultCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags={"测试接口"})
@RequestMapping("/test")
public class TestController {

    @GetMapping("/success")
    public Result<Void> success(){
        return Result.success();
    }

    @GetMapping("/failure")
    public Result<Void> failure(){
        return Result.failure();
    }

    @GetMapping("/failure1")
    public Result<Void> failure1(){
        return Result.failure(ResultCode.REQUEST_PARAM_ERROR);
    }

    @GetMapping("/failure2")
    public Result<Void> failure2(){
        return Result.failure("测试自定义失败信息");
    }
}
