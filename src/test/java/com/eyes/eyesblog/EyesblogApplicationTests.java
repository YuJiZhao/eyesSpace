package com.eyes.eyesblog;

import com.eyes.eyesblog.entity.Blog;
import com.eyes.eyesblog.entity.Copywriting;
import com.eyes.eyesblog.mapper.BlogMapper;
import com.eyes.eyesblog.mapper.CopywritingMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class EyesblogApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("a3", 123);
        System.out.println(redisTemplate.opsForValue().get("a3"));
    }

    @Autowired
    private CopywritingMapper copywritingMapper;

    @Test
    void testCopywriting() {
        List<Copywriting> list = copywritingMapper.findAll();
        System.out.println(list);
    }

    @Autowired
    private BlogMapper blogmapper;

    @Test
    void testBlog() {
        List<Blog> list = blogmapper.findBlog();
        System.out.println(list);
    }
}
