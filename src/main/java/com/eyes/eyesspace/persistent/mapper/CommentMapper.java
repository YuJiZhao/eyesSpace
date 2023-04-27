package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.po.CommentChildPO;
import com.eyes.eyesspace.persistent.po.ReplyInfoPO;
import com.eyes.eyesspace.persistent.po.CommentIdPO;
import com.eyes.eyesspace.persistent.po.CommentListPO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment " +
            "(type, user_id, object_id, landlord, reply_id, original_comment, comment, reply_list, create_time) " +
            "values " +
            "(#{type}, #{commentAddRequest.uid}, #{commentAddRequest.objectId}, #{commentAddRequest.landlord}, #{commentAddRequest.replyId}, #{commentAddRequest.originalComment}, #{commentAddRequest.comment}, #{replyList}, now())")
    @Options(useGeneratedKeys=true, keyProperty="commentAddRequest.id", keyColumn="id")
    Boolean addComment(CommentAddRequest commentAddRequest, String replyList, Integer type);

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
    List<CommentListPO> getCommentList(Integer id, Integer type, int start, Integer pageSize);

    @Select("select id, user_id uid, landlord, reply_id, comment, comments, create_time, status from comment where id=#{id}")
    CommentChildPO getCommentChild(Integer id);

    @Update("update comment set status=1 where id=#{id} and user_id=#{uid}")
    Boolean delComment(Integer id, Long uid);

    @Select("select reply_id, landlord from comment where id=#{id}")
    CommentIdPO getObjectId(Integer id);

    @Select("select user_id uid, original_comment content from comment where id=#{replyId}")
    ReplyInfoPO getReplyInfoByReplyId(Integer replyId);

    @Select("select user_id from comment where id=#{replyId}")
    Long getUidByReplyId(Integer replyId);

    @Select("select count(*) from comment where create_time>#{start} and create_time<#{end} and type!=4")
    Integer getCommentNumByTime(String start, String end);

    @Select("select count(*) from comment where create_time>#{start} and create_time<#{end} and type=4 and object_id=0")
    Integer getLeaveMsgNumByTime(String start, String end);
}
