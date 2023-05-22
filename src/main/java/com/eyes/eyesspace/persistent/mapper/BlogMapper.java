package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.dto.BlogCategoryDTO;
import com.eyes.eyesspace.persistent.dto.BlogInfoDTO;
import com.eyes.eyesspace.persistent.dto.BlogLabelDTO;
import com.eyes.eyesspace.persistent.po.BlogAddLabelPO;
import com.eyes.eyesspace.sync.model.bo.BlogAddBO;
import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.persistent.po.BlogDataPO;
import com.eyes.eyesspace.persistent.po.BlogAddCategoryPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Select("select id from blog_category where category=#{category}")
    Integer getCategoryIdByName(String category);

    @Insert("insert into blog_category (category) values (#{category})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addCategory(BlogAddCategoryPO blogAddCategoryDto);

    @Insert("insert into blog " +
            "(title, summary, content, category_id, status, words, create_time) " +
            "values " +
            "(#{title}, #{summary}, #{content}, #{category}, #{status}, #{words}, now())"
    )
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addBlog(BlogAddBO blogAddBo);

    @Select("select id from blog_label where label=#{label}")
    Integer getLabelIdByName(String label);

    @Insert("insert into blog_label (label) values (#{label})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addBlogLabel(BlogAddLabelPO blogAddLabelDto);

    @Insert("insert into blog_label_id (blog_id, label_id) values (#{id}, #{label})")
    Boolean addBlogLabelId(Integer id, Integer label);

    Integer getBlogListInfo(Integer status);

    BlogDataPO getBlogData(String role);

    List<BlogListDTO> getBlogList(Integer start, Integer pageSize, String statusCondition, String category, String label);

    @Select("select title, summary, content, bc.category, views, comments, words, status, create_time" +
            " from blog b, blog_category bc " +
            " where ${statusCondition} and b.id=#{id} and b.category_id = bc.id")
    BlogInfoDTO getBlogInfo(Integer id, String statusCondition);

    @Select("select bl.label from blog b, blog_label bl, blog_label_id bli where b.id = #{id} and bli.blog_id = b.id and bli.label_id = bl.id;")
    List<String> getLabelsById(Integer id);

    @Select("select bc.category, count(*) num from blog b, blog_category bc where ${statusCondition} and bc.id = b.category_id group by bc.category")
    List<BlogCategoryDTO> getBlogCategory(String statusCondition);

    @Select("select bl.label, count(*) num from blog b, blog_label bl, blog_label_id bli "
        + "where ${statusCondition} and b.id = bli.blog_id and bli.label_id = bl.id "
        + "group by bl.label having num>2 order by num desc limit #{size}")
    List<BlogLabelDTO> getBlogLabel(Integer size, String statusCondition);

    Integer getBlogListWords(Integer status);

    @Select("<script>"
        + "select b.id, b.title, b.summary, bc.category, b.views, b.words, b.create_time "
        + "from blog b, blog_category bc "
        + "where b.category_id=bc.id and b.id in "
        + "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
        + "#{item}"
        + "</foreach>"
        + "</script>")
    List<BlogListDTO> getBlogListByIds(List<Integer> ids);

    Integer getBlogListNum(String statusCondition, String category, String label);

    @Update("update blog set views=views+1 where id=#{id}")
    Boolean addView(Integer id);

    @Update("update blog set comments = comments + #{i} where id=#{objectId}")
    Boolean updateBlogComments(Integer objectId, int i);

    @Select("select c.type, c.object_id objectId, b.status from comment c, blog b where c.id=#{id} and c.object_id=b.id")
    CommentDelInfoPO getBlogCommentInfo(Integer id);
}