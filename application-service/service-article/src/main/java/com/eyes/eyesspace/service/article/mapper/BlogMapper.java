package com.eyes.eyesspace.service.article.mapper;

import com.eyes.eyesspace.service.article.module.bo.BlogAddBo;
import com.eyes.eyesspace.service.article.module.dto.*;
import com.eyes.eyesspace.service.article.module.po.BlogDataPo;
import com.eyes.eyesspace.service.article.module.dto.BlogInfoDto;
import com.eyes.eyesspace.service.article.module.po.CommentDelInfoPo;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Select("select id from blog_category where category=#{category}")
    Integer isExistCategory(String category);

    @Insert("insert into blog_category (category) values (#{category})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addCategory(BlogAddCategoryDto blogAddCategoryDto);

    @Insert("insert into blog " +
            "(title, summary, content, category_id, status, words, create_time) " +
            "values " +
            "(#{title}, #{summary}, #{content}, #{category}, #{status}, #{words}, now())"
    )
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean addBlog(BlogAddBo blogAddBo);

    @Select("select id from blog_label where label=#{label}")
    Integer isExistLabel(String label);

    @Insert("insert into blog_label (label) values (#{label})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer addBlogLabel(BlogAddLabelDto blogAddLabelDto);

    @Insert("insert into blog_label_id (blog_id, label_id) values (#{id}, #{label})")
    Boolean addBlogLabelId(Integer id, Integer label);

    Integer getBlogListInfo(Integer status);

    BlogDataPo getBlogData(String role);

    List<BlogListDto> getBlogList(Integer start, Integer pageSize, Integer status, String category, String label);

    @Select("select title, summary, content, bc.category, views, likes, collections, comments, words, status, create_time" +
            " from blog b, blog_category bc " +
            " where b.id=#{id} and b.category_id = bc.id")
    BlogInfoDto getBlogInfo(Integer id);

    @Select("select bl.label from blog b, blog_label bl, blog_label_id bli where b.id = #{id} and bli.blog_id = b.id and bli.label_id = bl.id;")
    List<String> getLabelsById(Integer id);

    List<BlogCategoryDto> getBlogCategory(Integer status);

    List<BlogLabelDto> getBlogLabel(Integer status);

    Integer getBlogListWords(Integer status);

    @Select("select id from blog_like where user_id=#{uid} and blog_id=#{bid}")
    Integer isUserLike(Integer uid, Integer bid);

    @Select("select id from blog_collection where user_id=#{uid} and blog_id=#{bid}")
    Integer isUserCollect(Integer uid, Integer bid);

    @Insert("insert into blog_like (user_id, blog_id, create_time) values (#{uid}, #{id}, now())")
    Boolean addUserLike(Integer uid, Integer id);

    @Insert("delete from blog_like where user_id=#{uid} and blog_id=#{id}")
    Boolean delUserLike(Integer uid, Integer id);

    @Insert("insert into blog_collection (user_id, blog_id, create_time) values (#{uid}, #{id}, now())")
    Boolean addUserCollect(Integer uid, Integer id);

    @Insert("delete from blog_collection where user_id=#{uid} and blog_id=#{id}")
    Boolean delUserCollect(Integer uid, Integer id);

    @Select("select status from blog where id=#{id}")
    Integer getBlogStatus(Integer id);

    @Insert("insert into home (type, cid, create_time, status) values (#{type}, #{id}, now(), #{status})")
    Boolean insertHome(Integer type, Integer id, Integer status);

    @Select("select b.id, title, summary, category, views, likes, collections, words, create_time from blog b, blog_category bc where b.id=#{id} and b.category_id=bc.id")
    BlogListDto getBlogSummaryInfo(Integer id);

    @Update("update blog set likes = likes + #{i} where id=#{id}")
    Boolean changeBlogLikeNum(Integer id, int i);

    @Update("update blog set collections = collections + #{i} where id=#{id}")
    Boolean changeBlogCollectNum(Integer id, int i);

    Integer getBlogListNum(Integer status, String category, String label);

    @Update("update blog set views=views+1 where id=#{id}")
    Boolean addView(Integer id);

    @Update("update blog set comments = comments + #{i} where id=#{objectId}")
    Boolean updateBlogComments(Integer objectId, int i);

    @Select("select c.type, c.object_id objectId, b.status from comment c, blog b where c.id=#{id} and c.object_id=b.id")
    CommentDelInfoPo getBlogCommentInfo(Integer id);
}