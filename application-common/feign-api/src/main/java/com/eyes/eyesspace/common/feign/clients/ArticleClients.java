package com.eyes.eyesspace.common.feign.clients;

import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-article", path = "/article")
public interface ArticleClients {
    @GetMapping("/blog/getBlogSummaryInfo/{id}")
    Result.DataResult<BlogListDto> getBlogSummaryInfo(@PathVariable("id") Integer id);

    @GetMapping("/shuoshuo/getSingleShuoshuo/{id}")
    Result.DataResult<ShuoshuoListDto> getShuoshuoInfo(@PathVariable("id") Integer id);
}
