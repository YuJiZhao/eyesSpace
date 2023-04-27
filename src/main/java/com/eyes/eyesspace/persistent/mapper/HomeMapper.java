package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.po.HomeListPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {
    List<HomeListPO> getHomeList(Integer start, Integer size, int status);

    Integer getHomeListTotal(int status);

    @Insert("insert into home (type, cid, create_time, status) values (#{type}, #{id}, now(), #{status})")
    Boolean insertHome(Integer type, Integer id, Integer status);
}
