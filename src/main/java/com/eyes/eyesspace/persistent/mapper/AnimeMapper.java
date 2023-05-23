package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.sync.model.dto.AnimeListDTO;
import com.eyes.eyesspace.sync.model.vo.AnimeInfoVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author eyesYeager
 * @date 2023/5/22 14:51
 */
@Mapper
public interface AnimeMapper {
  @Select("select status from anime")
  List<Integer> getAnimeStatusList();

  @Select("select SUM(view) from anime where ${statusCondition}")
  Integer getAnimeViewNum(String statusCondition);

  @Select("select SUM(comment) from anime where ${statusCondition}")
  Integer getAnimeCommentNum(String statusCondition);

  @Select("select COUNT(*) from anime where ${statusCondition}")
  Integer getAnimeNum(String statusCondition);

  @Select("select id, title, type, period, introduce, cover, view, comment, create_time from anime where ${statusCondition} order by create_time desc limit #{start}, #{pageSize}")
  List<AnimeListDTO> getAnimeList(Integer start, Integer pageSize, String statusCondition);

  @Select("select title, type, period, introduce, word, cover, view, comment, create_time from anime where id=#{id} and ${statusCondition}")
  AnimeInfoVO getAnimeInfo(Integer id, String statusCondition);

  @Update("update anime set comment=comment+#{i} where id=#{objectId}")
  Boolean updateAnimeComments(Integer objectId, int i);

  @Select("select c.type, c.object_id objectId, a.status from comment c, anime a where c.id=#{id} and c.object_id=a.id")
  CommentDelInfoPO getAnimeCommentInfo(Integer id);

  @Update("update anime set view=view+1 where id=#{id}")
  Boolean addView(Integer id);

  @Select("<script>"
      + "select id, title, type, period, introduce, cover, view, comment, create_time from anime "
      + "where id in "
      + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
      + "#{item}"
      + "</foreach>"
      + "</script>")
  List<AnimeListDTO> getAnimeListByIds(List<Integer> ids);
}
