package com.eyes.eyesspace.service.article.mapper;

import com.eyes.eyesspace.service.article.module.dto.ShuoshuoAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoAddPicDto;
import com.eyes.eyesspace.service.article.module.po.CommentDelInfoPo;
import com.eyes.eyesspace.service.article.module.po.ShuoshuoDataPo;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoInfoDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShuoshuoMapper {
    @Insert("insert into shuoshuo (content, status, create_time) values (#{content}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addShuoshuo(ShuoshuoAddDto shuoshuoAddDto);

    Integer addShuoshuoPics(List<ShuoshuoAddPicDto> picList, Integer id);

    Integer getShuoshuoListInfo(Integer status);

    ShuoshuoDataPo getShuoshuoData(String role);

    List<ShuoshuoInfoDto> getShuoshuoList(Integer start, Integer pageSize, String role);

    @Select("select url from shuoshuo_pic where shuoshuo_id=#{id}")
    List<String> getShuoshuoPics(Integer id);

    @Insert("insert into home (type, cid, create_time, status) values (#{type}, #{id}, now(), #{status})")
    Boolean insertHome(Integer type, Integer id, Integer status);

    @Select("select id, content, views, comments, status, create_time from shuoshuo where id=#{id}")
    ShuoshuoInfoDto getShuoshuoInfo(Integer id);

    @Update("update shuoshuo set views=views+1 where id=#{id}")
    Boolean addView(Integer id);

    @Select("select status from shuoshuo where id=#{objectId}")
    Integer getShuoshuoStatus(Integer objectId);

    @Update("update shuoshuo set comments = comments + #{i} where id=#{objectId}")
    boolean updateShuoshuoComments(Integer objectId, int i);

    @Select("select c.type, c.object_id objectId, s.status from comment c, shuoshuo s where c.id=#{id} and c.object_id=s.id")
    CommentDelInfoPo getShuoshuoCommentInfo(Integer id);
}
