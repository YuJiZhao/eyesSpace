package com.eyes.eyesspace.common.feign.clients;

import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "service-site", path = "/site")
public interface SiteClients {
    /*
     ******************************************************************
     *                           评论
     ******************************************************************
     */
    @PostMapping("/comment/publishComment")
    Result.NoDataResult publishComment(@RequestBody CommentAddDto commentAddDto, @RequestParam Integer type);

    @GetMapping("/comment/getCommentList/{id}")
    Result.DataResult<List<CommentListDto>> getCommentList(
            @PathVariable("id") Integer id,
            @RequestParam("type") Integer type,
            @RequestParam(required = false) Integer uid,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @DeleteMapping("/comment/delComment/{id}")
    Result.NoDataResult delComment(@PathVariable("id") Integer id, @RequestParam(required = false) Integer uid);
}