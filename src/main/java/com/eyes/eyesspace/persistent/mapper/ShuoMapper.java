package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.sync.model.request.ShuoAddRequest;
import com.eyes.eyesspace.sync.model.dto.ShuoAddPicDTO;
import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.persistent.po.ShuoDataPO;
import com.eyes.eyesspace.persistent.dto.ShuoshuoInfoDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShuoMapper {
    @Insert("insert into shuoshuo (content, status, create_time) values (#{content}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addShuoshuo(ShuoAddRequest shuoAddRequest);

    Integer addShuoshuoPics(List<ShuoAddPicDTO> picList, Integer id);

    Integer getShuoshuoListInfo(Integer status);

    ShuoDataPO getShuoshuoData(String role);

    List<ShuoshuoInfoDTO> getShuoshuoList(Integer start, Integer pageSize, String role);

    @Select("select url from shuoshuo_pic where shuoshuo_id=#{id}")
    List<String> getShuoshuoPics(Integer id);

    @Select("select id, content, views, comments, status, create_time from shuoshuo where id=#{id}")
    ShuoshuoInfoDTO getShuoshuoInfo(Integer id);

    @Update("update shuoshuo set views=views+1 where id=#{id}")
    Boolean addView(Integer id);

    @Select("select status from shuoshuo where id=#{objectId}")
    Integer getShuoshuoStatus(Integer objectId);

    @Update("update shuoshuo set comments = comments + #{i} where id=#{objectId}")
    boolean updateShuoshuoComments(Integer objectId, int i);

    @Select("select c.type, c.object_id objectId, s.status from comment c, shuoshuo s where c.id=#{id} and c.object_id=s.id")
    CommentDelInfoPO getShuoshuoCommentInfo(Integer id);
}
