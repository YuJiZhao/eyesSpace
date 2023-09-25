package com.eyes.eyesspace.persistent.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author eyesYeager
 * @date 2023/2/9 15:55
 */
@Mapper
public interface TrackMapper {
  @Insert("insert into log_visit " +
      "(uid, ip, os, browser, path, create_time)" +
      "values" +
      "(#{uid}, #{ipAddr}, #{osName}, #{browserName}, #{path}, now())")
  Boolean addVisitLog(Long uid, String ipAddr, String osName, String browserName, String path);

  Integer getVisitNumByTime(String start, String end);

  Integer getVisitorNumByTime(String start, String end);

  @Insert("insert into log_file_operation "
      + "(type, uid, method, remarks, create_time) "
      + "values "
      + "(#{type}, #{uid}, #{method}, #{remarks}, now())")
  Boolean addFileLog(int type, long uid, int method, String remarks);

  @Insert("insert into log_joke " +
      "(joke_id, uid, ip, os, browser, create_time)" +
      "values" +
      "(#{jokeId}, #{uid}, #{ipAddr}, #{osName}, #{browserName}, now())")
  Boolean addJokeLog(Long jokeId, Long uid, String ipAddr, String osName, String browserName);
}
