package com.eyes.eyesspace.service.backstage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TraceMapper {
    @Insert("insert into log_visit " +
            "(uid, ip, os, host, browser, path, create_time)" +
            "values" +
            "(#{uid}, #{ipAddr}, #{osName}, #{hostName}, #{browserName}, #{path}, now())")
    Boolean addSpaceVisit(String uid, String ipAddr, String osName, String hostName, String browserName, String path);
}
