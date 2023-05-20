package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.ShuoInfoDTO;
import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.persistent.po.ShuoDataPO;
import com.eyes.eyesspace.sync.model.request.ShuoAddRequest;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShuoMapper {
    @Insert("insert into shuoshuo (content, status, create_time) values (#{content}, #{status}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addShuo(ShuoAddRequest shuoAddRequest);

    Integer addShuoPics(List<String> picList, Integer id);

    Integer getShuoListInfo(Integer status);

    ShuoDataPO getShuoData(String role);

    List<ShuoInfoDTO> getShuoList(Integer start, Integer pageSize, String role);

    @Select("select url from shuoshuo_pic where shuoshuo_id=#{id}")
    List<String> getShuoPics(Integer id);

    @Select("select id, content, views, comments, status, create_time from shuoshuo where id=#{id}")
    ShuoInfoDTO getShuoInfo(Integer id);

    @Select("<script>"
        + "select id, content, views, comments, status, create_time "
        + "from shuoshuo "
        + "where id in "
        + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
        + "#{item}"
        + "</foreach>"
        + "</script>")
    List<ShuoInfoDTO> getShuoListByIds(List<Integer> ids);

    @Update("update shuoshuo set views=views+1 where id=#{id}")
    Boolean addView(Integer id);

    @Select("select status from shuoshuo where id=#{objectId}")
    Integer getShuoStatus(Integer objectId);

    @Update("update shuoshuo set comments = comments + #{i} where id=#{objectId}")
    boolean updateShuoComments(Integer objectId, int i);

    @Select("select c.type, c.object_id objectId, s.status from comment c, shuoshuo s where c.id=#{id} and c.object_id=s.id")
    CommentDelInfoPO getShuoCommentInfo(Integer id);
}
