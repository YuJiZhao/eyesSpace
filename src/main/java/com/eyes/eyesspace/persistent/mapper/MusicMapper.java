package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.MusicInfoDTO;
import com.eyes.eyesspace.persistent.dto.MusicListDTO;
import com.eyes.eyesspace.persistent.po.UserMusicPO;
import com.eyes.eyesspace.sync.model.request.MusicAddRequest;
import com.eyes.eyesspace.persistent.po.MusicDataPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MusicMapper {
    @Insert("insert into music " +
                    "(url, pic, title, author, lrc, owner_comment, status, create_time) " +
                    "values " +
                    "(#{url}, #{pic}, #{title}, #{author}, #{lrc}, #{ownerComment}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addMusic(MusicAddRequest musicAddRequest);

    @Select("select id, title, pic, author, views, likes, comments, status, create_time from music order by id desc limit #{start}, #{pageSize}")
    List<MusicListDTO> getMusicList(Integer start, Integer pageSize);

    Integer getMusicListInfo(Integer status);

    @Select("select title, author, pic, owner_comment, url, views, likes, comments, create_time from music where id=#{id}")
    MusicInfoDTO getMusicInfo(Integer id);

    @Select("select sum(views) viewsNum, sum(likes) likesNum, sum(comments) commentsNum from music")
    MusicDataPO getMusicData();

    @Select("select lrc from music where id=#{id}")
    String getMusicLrc(Integer id);

    @Select("select user_music_key from music_user where uid=#{uid}")
    String getUserMusicKey(Long uid);

    UserMusicPO getRandomMusicList(String role, List<Integer> ids);

    @Insert("insert into music_user (uid, user_music_key) values (#{uid}, #{userMusicKey})")
    Boolean insertUserMusicKey(Long uid, String userMusicKey);

    @Update("update music_user set user_music_key=#{userMusicKey} where uid=#{uid}")
    Boolean updateUserMusicKey(Long uid, String userMusicKey);

    @Update("update music set views=views+1 where id=#{id}")
    Boolean updateViewsNum(Integer id);

    @Select("select id from music_like where uid=#{uid} and music_id=#{musicId}")
    Integer existUserLike(Long uid, Integer musicId);

    @Delete("delete from music_like where music_id=#{musicId} and uid=#{uid}")
    Boolean delUserLike(Integer musicId, Long uid);

    @Insert("insert into music_like (music_id, uid, create_time) values (#{musicId}, #{uid}, now())")
    Boolean createUserLike(Integer musicId, Long uid);

    @Update("update music set likes=likes+#{i} where id=#{musicId}")
    Boolean updateLikesNum(Integer musicId, int i);
}
