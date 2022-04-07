package com.eyes.eyesblog.controller;

import com.eyes.eyesblog.api.Result;
import com.eyes.eyesblog.entity.Blog;
import com.eyes.eyesblog.service.BlogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags={"博客接口组"})
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    /*
     * -----------------
     * ---  博客接口 ----
     * -----------------
     */

    /**
     * 按id查询博客
     *
     * @return Blog
     */
    @GetMapping("/getblog")
    public Result<Map<String, Object>> getBlogById(@RequestParam("id") Integer id) {
        return Result.success(blogService.getBlogById(id));
    }

    /**
     * 按时间查询简介
     */
    @GetMapping("/getblogsbytime")
    public Result<List<Map<String, Object>>> getBlogsByTime() {
        return null;
    }

    /**
     * 按标签查询简介
     */
    @GetMapping("/getblogsbylabel")
    public Result<List<Map<String, Object>>> getBlogsByLabel(@RequestParam("label") String label) {
        return Result.success(blogService.getBlogsByLabel(label));
    }

    /**
     * 按分类查询简介
     */
    @GetMapping("/getblogsbyarchive")
    public Result<List<Map<String, Object>>> getBlogsByArchive(@RequestParam("archive") String archive) {
        return Result.success(blogService.getBlogsByArchive(archive));
    }

    /*
     * -------------------
     * ---  管理系统接口 ---
     * -------------------
     */

    /**
     * 新增博客
     */
    @PostMapping("/addblog")
    public String addBlog(@RequestParam Map<String, String> map) {
        return null;
    }

    /**
     * 修改博客
     */
    @PutMapping("/modifyBLog")
    public String modifyBlog() {
        return null;
    }

    /**
     * 删除博客
     */
    @DeleteMapping("/deleteblog")
    public String deleteBlog() {
        return null;
    }
}