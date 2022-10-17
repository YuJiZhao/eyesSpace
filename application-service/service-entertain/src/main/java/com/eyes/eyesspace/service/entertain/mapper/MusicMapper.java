package com.eyes.eyesspace.service.entertain.mapper;

import com.eyes.eyesspace.service.entertain.module.music.Vo.MusicAddVo;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListDto;
import com.eyes.eyesspace.service.entertain.module.music.po.MusicDataPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MusicMapper {
    @Insert("insert into music " +
                    "(url, pic, title, author, lrc, owner_comment, status, create_time) " +
                    "values " +
                    "(#{url}, #{pic}, #{title}, #{author}, #{lrc}, #{ownerComment}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addMusic(MusicAddVo musicAddVo);

    @Select("select id, title, pic, author, views, likes, comments, status, create_time from music order by id desc limit #{start}, #{pageSize}")
    List<MusicListDto> getMusicList(Integer start, Integer pageSize);

    Integer getMusicListInfo(Integer status);

    MusicInfoDto getMusicInfo(Integer id, String role);

    @Select("select sum(views) viewsNum, sum(likes) likesNum, sum(comments) commentsNum from music")
    MusicDataPo getMusicData();

    @Select("select lrc from music where id=#{id}")
    String getMusicLrc(Integer id);
}
