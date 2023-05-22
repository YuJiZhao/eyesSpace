package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.po.HomeListPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HomeMapper {
    @Select("select type, cid from home where ${statusCondition} order by create_time desc, id desc limit #{start}, #{size}")
    List<HomeListPO> getHomeList(Integer start, Integer size, String statusCondition);

    @Select("select count(*) from home where ${statusCondition}")
    Integer getHomeListTotal(String statusCondition);

    @Insert("insert into home (type, cid, create_time, status) values (#{type}, #{id}, now(), #{status})")
    Boolean insertHome(Integer type, Integer id, Integer status);
}
