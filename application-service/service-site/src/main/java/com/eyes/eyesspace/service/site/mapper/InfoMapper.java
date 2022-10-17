package com.eyes.eyesspace.service.site.mapper;

import com.eyes.eyesspace.service.site.model.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InfoMapper {
    @Select("select id, email, username, avatar, create_time from user where id=#{uid}")
    UserInfoDto getUserInfo(Integer uid);

    @Update("update user set username=#{name} where id=#{uid}")
    Boolean updateUserInfo(Integer uid, String name);

    @Update("update user set avatar=#{url} where id=#{id}")
    Boolean updateAvatar(String url, Integer id);
}
