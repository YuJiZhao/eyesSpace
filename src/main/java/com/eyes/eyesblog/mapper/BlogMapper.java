package com.eyes.eyesblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesblog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 按id查询博客
     */
    Map<String, Object> getBlogById(Integer id);

    /**
     * 按时间查询简介
     */
    List<Blog> getBlogsByTime();

    /**
     * 按标签查询简介
     */
    List<Map<String, Object>> getBlogsByLabel(String label);

    /**
     * 按分类查询简介
     */
    List<Map<String, Object>> getBlogsByArchive(String archive);

    /**
     * 新增博客
     */
    Integer addBlog(Map<String, Object> map);

    /**
     * 添加标签
     */
    Integer addLabel(BigInteger id, List<String> labels);

    /**
     * 修改博客
     */
    Integer modifyBlog();

    /**
     * 修改博客标签
     */
    Integer modifyBlogLabels();

    /**
     * 删除博客
     */
    String deleteBlog();
}
