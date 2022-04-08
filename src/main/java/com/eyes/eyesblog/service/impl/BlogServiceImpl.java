package com.eyes.eyesblog.service.impl;

import com.eyes.eyesblog.entity.Blog;
import com.eyes.eyesblog.mapper.BlogMapper;
import com.eyes.eyesblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map<String, Object> getBlogById(Integer id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> getBlogsByTime() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBlogsByLabel(String label) {
        return blogMapper.getBlogsByLabel(label);
    }

    @Override
    public List<Map<String, Object>> getBlogsByArchive(String archive) {
        return blogMapper.getBlogsByArchive(archive);
    }

    @Override
    public void addBlog(Map<String, Object> map) {
        // TODO： spring事务操作, 以及标签数组功能的验证
        System.out.println(map);
        blogMapper.addBlog(map);
        blogMapper.addLabel((BigInteger)map.get("id"), (List<String>)map.get("label"));
    }

    @Override
    public String modifyBlog() {
        return null;
    }

    @Override
    public String deleteBlog() {
        return null;
    }
}
