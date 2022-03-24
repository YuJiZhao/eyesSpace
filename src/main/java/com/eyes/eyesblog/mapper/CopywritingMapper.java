package com.eyes.eyesblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesblog.entity.Copywriting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CopywritingMapper extends BaseMapper<Copywriting> {

    @Select("select * from base_info union select * from copywriting")
    List<Copywriting> findAll();
}
