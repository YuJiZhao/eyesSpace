package com.eyes.eyesspace.service.entertain.mapper;

import com.eyes.eyesspace.service.entertain.module.music.po.UserMusicPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MusicUserMapper {
    @Select("select user_music_key from music_user where uid=#{uid}")
    String getUserMusicKey(Integer uid);

    List<UserMusicPo> getRandomMusicList(String role);

    @Insert("insert into music_user (uid, user_music_key) values (#{uid}, #{userMusicKey})")
    Integer insertUserMusicKey(Integer uid, String userMusicKey);

    @Update("update music_user set user_music_key=#{userMusicKey} where uid=#{uid}")
    Boolean updateUserMusicKey(Integer uid, String userMusicKey);

    @Update("update music set views=views+1 where id=#{id}")
    Boolean updateViewsNum(Integer id);

    @Select("select id from music_like where uid=#{uid} and music_id=#{musicId}")
    Integer existUserLike(Integer uid, Integer musicId);

    @Delete("delete from music_like where music_id=#{musicId} and uid=#{uid}")
    Integer delUserLike(Integer musicId, Integer uid);

    @Insert("insert into music_like (music_id, uid, create_time) values (#{musicId}, #{uid}, now())")
    Integer createUserLike(Integer musicId, Integer uid);

    @Update("update music set likes=likes+#{i} where id=#{musicId}")
    Integer updateLikesNum(Integer musicId, int i);
}
