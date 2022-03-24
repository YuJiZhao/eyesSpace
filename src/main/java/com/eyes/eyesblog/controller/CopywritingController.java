package com.eyes.eyesblog.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags={"文案接口"})
@RequestMapping("/copy")
public class CopywritingController {

    @GetMapping("copy")
    public String getCopywriting() {
        return "Copywriting";
    }
}
