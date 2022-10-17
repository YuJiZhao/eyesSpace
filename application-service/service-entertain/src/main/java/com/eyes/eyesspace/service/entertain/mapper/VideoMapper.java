package com.eyes.eyesspace.service.entertain.mapper;

import com.eyes.eyesspace.service.entertain.module.video.po.VideoDataPo;
import com.eyes.eyesspace.service.entertain.module.video.vo.VideoAddVo;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.dto.VideoListDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper {
    @Insert(
            "insert into video " +
            "(video_url, picture_url, title, original_url, original_author, owner_comment, status, create_time) " +
            "values " +
            "(#{videoUrl}, #{pictureUrl}, #{title}, #{originalUrl}, #{originalAuthor}, #{ownerComment}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addVideo(VideoAddVo videoAddVo);

    @Select("select id, title, picture_url, views, likes, comments, status, create_time from video order by id DESC limit #{start}, #{pageSize}")
    List<VideoListDto> getVideoList(Integer start, Integer pageSize);

    Integer getVideoListInfo(Integer status);

    VideoInfoDto getVideoInfo(Integer id, String role);

    @Select("select SUM(views) viewsNum, SUM(likes) likesNum, SUM(comments) commentsNum from video")
    VideoDataPo getVideoData();
}
