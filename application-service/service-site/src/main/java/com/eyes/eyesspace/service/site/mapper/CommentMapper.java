package com.eyes.eyesspace.service.site.mapper;

import com.eyes.eyesspace.service.site.model.po.CommentChildPo;
import com.eyes.eyesspace.service.site.model.po.CommentIdPo;
import com.eyes.eyesspace.service.site.model.po.CommentListPo;
import com.eyes.eyesspace.service.site.model.po.CommentUserInfo;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment " +
            "(type, user_id, object_id, landlord, reply_id, original_comment, comment, reply_list, create_time) " +
            "values " +
            "(#{type}, #{commentAddDto.uid}, #{commentAddDto.objectId}, #{commentAddDto.landlord}, #{commentAddDto.replyId}, #{commentAddDto.originalComment}, #{commentAddDto.comment}, #{replyList}, now())")
    @Options(useGeneratedKeys=true, keyProperty="commentAddDto.id", keyColumn="id")
    Boolean addComment(CommentAddDto commentAddDto, String replyList, Integer type);

    @Select("select reply_list from comment where id=#{landlord}")
    String getCommentReplyList(Integer landlord);

    @Update("update comment set reply_list=#{replyList} where id=#{landlord}")
    Boolean updateCommentReplyList(Integer landlord, String replyList);

    @Update("update comment set comments = comments + #{i} where id=#{id}")
    Boolean updateComments(Integer id, int i);

    @Select("select id, user_id uid, comment, create_time, comments, reply_list, status " +
            "from comment " +
            "where type=#{type} and object_id=#{id} and reply_id is null and landlord is null and status!=2 " +
            "order by create_time " +
            "limit #{start}, #{pageSize}")
    List<CommentListPo> getCommentList(Integer id, Integer type, int start, Integer pageSize);

    @Select("select username name, avatar from user where id=#{uid}")
    CommentUserInfo getCommentUserInfo(Integer uid);

    @Select("select id, user_id uid, landlord, reply_id, comment, comments, create_time, status from comment where id=#{id}")
    CommentChildPo getCommentChild(Integer id);

    @Select("select u.username name from comment c, user u where c.id=#{replyId} and c.user_id=u.id")
    String getReplyNameById(Integer replyId);

    @Update("update comment set status=1 where id=#{id} and user_id=#{uid}")
    Boolean delComment(Integer id, Integer uid);

    @Select("select reply_id, landlord from comment where id=#{id}")
    CommentIdPo getObjectId(Integer id);
}
