package com.eyes.eyesspace.service.site.mapper;

import com.eyes.eyesspace.service.site.model.po.ContextPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContextMapper {
    @Select("select * from context")
    List<ContextPo> getContext();

    @Select("select count(*) from log_visit")
    Integer getVisitNum();

    @Select("select count(*) from (select distinct ip from log_visit) as ip_table")
    Integer getVisitorNum();

    @Select("select count(*) from user where enabled=0")
    Integer getUserNum();

    Integer getBlogNum(Integer status);

    Integer getShuoNum(Integer status);
}
