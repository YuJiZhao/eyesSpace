package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.sync.model.vo.HomeListVO;
import com.eyes.eyesspace.sync.model.vo.SiteDataVO;
import com.eyes.eyesspace.sync.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 */
@Api(tags = "首页模块")
@RefreshScope
@RestController
@RequestMapping("/home")
@Validated
public class HomeController {
    @Value("${business.page-size.home:6}")
    private Integer homePageSize;

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ApiOperation("获取首页内容列表")
    @Limiter
    @Permission
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", defaultValue="1")})
    @GetMapping("/getHomeList")
    public Result<PageBind<HomeListVO>> getHomeList(@RequestParam(required = false) Integer page) throws CustomException {
        page = (Objects.isNull(page) || page < 1) ? 1 : page;
        return Result.success(homeService.getHomeList(page, homePageSize));
    }

    @ApiOperation("获取网站数据")
    @Limiter
    @Permission
    @GetMapping("/getSiteData")
    public Result<SiteDataVO> getSiteData() throws CustomException {
        return Result.success(homeService.getSiteData());
    }
}
