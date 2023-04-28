package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.persistent.po.UserVideoPO;
import com.eyes.eyesspace.persistent.po.VideoDataPO;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VideoMapper {
    @Insert(
            "insert into video " +
            "(video_url, picture_url, title, original_url, original_author, owner_comment, status, create_time) " +
            "values " +
            "(#{videoUrl}, #{pictureUrl}, #{title}, #{originalUrl}, #{originalAuthor}, #{ownerComment}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addVideo(VideoAddRequest videoAddRequest);

    @Select("select id, title, picture_url, views, likes, comments, status, create_time from video order by id DESC limit #{start}, #{pageSize}")
    List<VideoListDTO> getVideoList(Integer start, Integer pageSize);

    Integer getVideoListInfo(Integer status);

    @Select("select title, original_author, picture_url, original_url, owner_comment, video_url, views, likes, comments, create_time from video where id=#{id}")
    VideoInfoDTO getVideoInfo(Integer id);

    @Select("select SUM(views) viewsNum, SUM(likes) likesNum, SUM(comments) commentsNum from video")
    VideoDataPO getVideoData();

    @Select("select user_video_key from video_user where uid=#{uid}")
    String getUserVideoKey(Long uid);

    UserVideoPO getRandomVideo(String role, List<Integer> ids);

    @Insert("insert into video_user (uid, user_video_key) values (#{uid}, #{userVideoKey})")
    Boolean insertUserVideoKey(Long uid, String userVideoKey);

    @Update("update video_user set user_video_key=#{userVideoKey} where uid=#{uid}")
    Boolean updateUserVideoKey(Long uid, String userVideoKey);

    @Update("update video set views=views+1 where id=#{id}")
    Boolean updateViewsNum(Integer id);

    @Select("select id from video_like where uid=#{uid} and video_id=#{videoId}")
    Integer existUserLike(Long uid, Integer videoId);

    @Delete("delete from video_like where video_id=#{videoId} and uid=#{uid}")
    Boolean delUserLike(Integer videoId, Long uid);

    @Insert("insert into video_like (video_id, uid, create_time) values (#{videoId}, #{uid}, now())")
    Boolean createUserLike(Integer videoId, Long uid);

    @Update("update video set likes=likes+#{i} where id=#{videoId}")
    Boolean updateLikesNum(Integer videoId, int i);
}
