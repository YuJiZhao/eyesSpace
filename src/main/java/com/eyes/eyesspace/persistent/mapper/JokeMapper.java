package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.JokeAddDTO;
import com.eyes.eyesspace.persistent.po.JokeAddCategoryPO;
import com.eyes.eyesspace.persistent.po.JokeListPO;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author eyesYeager
 * @date 2023/9/25 9:47
 */
@Mapper
public interface JokeMapper {
  @Select("select id from joke_category where category=#{name}")
  Long getCategoryIdByName(String name);

  @Insert("insert into joke_category (category, create_time) values (#{category}, now())")
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  Boolean addJokeCategory(JokeAddCategoryPO jokeAddCategoryPO);

  @Insert("insert into joke (url_list, category_id, status, create_time) values (#{urlList}, #{categoryId}, #{status}, now())")
  @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
  Boolean addJoke(JokeAddDTO jokeAddDTO);

  @Select("select j.id, j.url_list, jc.category "
      + "from joke j, joke_category jc "
      + "where j.category_id=jc.id and" + " j." + "${statusCondition} "
      + "order by j.create_time desc "
      + "limit #{start}, #{pageSize}")
  List<JokeListPO> getJokeList(int start, int pageSize, String statusCondition);

  @Select("select count(*) from joke where ${statusCondition}")
  Integer getJokeTotalNum(String statusCondition);
}
