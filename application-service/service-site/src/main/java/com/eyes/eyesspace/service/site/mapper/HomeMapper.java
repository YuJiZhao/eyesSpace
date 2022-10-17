package com.eyes.eyesspace.service.site.mapper;

import com.eyes.eyesspace.service.site.model.po.HomeListPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HomeMapper {
    @Select("select type, cid from home where status=0 order by create_time desc limit #{start}, #{size}")
    List<HomeListPo> getHomeList(Integer start, Integer size);
}
