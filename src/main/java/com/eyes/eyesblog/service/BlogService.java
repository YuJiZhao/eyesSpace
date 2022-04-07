package com.eyes.eyesblog.service;

import com.eyes.eyesblog.entity.Blog;
import java.util.List;
import java.util.Map;

public interface BlogService {
    /*
     * -----------------
     * ---  博客接口 ----
     * -----------------
     */

    /**
     * 按id查询博客
     * @param id Integer
     * @return Map<String, Object>
     */
    Map<String, Object> getBlogById(Integer id);

    /**
     * 按时间查询简介
     *
     * @return List<Blog>
     */
    List<Blog> getBlogsByTime();

    /**
     * 按标签查询简介
     *
     * @param label String
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getBlogsByLabel(String label);

    /**
     * 按分类查询简介
     *
     * @param archive String
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getBlogsByArchive(String archive);

    /*
     * -------------------
     * ---  管理系统接口 ---
     * -------------------
     */

    /**
     * 新增博客
     *
     * @return String
     */
    String addBlog();

    /**
     * 修改博客
     *
     * @return String
     */
    String modifyBlog();

    /**
     * 删除博客
     *
     * @return String
     */
    String deleteBlog();
}
