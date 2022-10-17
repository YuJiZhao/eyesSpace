package com.eyes.eyesspace.service.site.controller;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.site.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.eyes.eyesspace.common.feign.module.dto.HomeListDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/site/home")
@Api(tags = "home模块")
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ApiOperation("获取首页综合列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", defaultValue="1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue="10")
    })
    @GetMapping("/getHomeList")
    public StandardResult<List<HomeListDto>> getHomeList (
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) throws CustomException {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        pageSize = (Objects.isNull(pageSize) || pageSize > 10) ? 6 : pageSize;
        return Result.success(homeService.getHomeList(page, pageSize));
    }
}
