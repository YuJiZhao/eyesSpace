package com.eyes.eyesspace.service.entertain.mapper;

import com.eyes.eyesspace.service.entertain.module.video.po.UserVideoPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoUserMapper {
    @Select("select user_video_key from video_user where uid=#{id}")
    String getUserVideoKey(Integer id);

    List<UserVideoPo> getRandomVideoList(String role);

    @Insert("insert into video_user (uid, user_video_key) values (#{uid}, #{userVideoKey})")
    Integer insertUserVideoKey(Integer uid, String userVideoKey);

    @Update("update video_user set user_video_key=#{userVideoKey} where uid=#{uid}")
    Integer updateUserVideoKey(Integer uid, String userVideoKey);

    @Update("update video set views=views+1 where id=#{id}")
    Boolean updateViewsNum(Integer id);

    @Select("select id from video_like where uid=#{uid} and video_id=#{videoId}")
    Integer existUserLike(Integer uid, Integer videoId);

    @Delete("delete from video_like where video_id=#{videoId} and uid=#{uid}")
    Integer delUserLike(Integer videoId, Integer uid);

    @Insert("insert into video_like (video_id, uid, create_time) values (#{videoId}, #{uid}, now())")
    Integer createUserLike(Integer videoId, Integer uid);

    @Update("update video set likes=likes+#{i} where id=#{videoId}")
    Integer updateLikesNum(Integer videoId, int i);
}
