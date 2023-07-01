package com.eyes.eyesspace.sync.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.async.asynchronist.asyncRestrict.NoticeAsyncRestrict;
import com.eyes.eyesspace.async.model.CommentNoticeModel;
import com.eyes.eyesspace.async.model.ReplyNoticeModel;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.mapper.CommentMapper;
import com.eyes.eyesspace.persistent.po.CommentChildPO;
import com.eyes.eyesspace.persistent.po.CommentIdPO;
import com.eyes.eyesspace.persistent.po.CommentListPO;
import com.eyes.eyesspace.sync.convert.CommentConvert;
import com.eyes.eyesspace.sync.model.dto.CommentChildDTO;
import com.eyes.eyesspace.sync.model.dto.CommentReplyDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.service.CommentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RefreshScope
@Service
public class CommentServiceImpl implements CommentService {
    private static final String COMMENT_SUBJECT = " | 评论通知";

    @Value("${business.comment.delete-word}")
    private String commentDeleteWord;

    private final CommentMapper commentMapper;

    private final TTClientPool ttClientPool;

    @Resource
    private NoticeAsyncRestrict noticeActuator;

    public CommentServiceImpl(CommentMapper commentMapper, TTClientPool ttClientPool) {
        this.commentMapper = commentMapper;
        this.ttClientPool = ttClientPool;
    }

    @Override
    @Transactional
    public void publishComment(CommentAddRequest commentAddRequest, Integer type, Boolean noticeSwitch) throws CustomException {
        // 插入评论数据
        if (!commentMapper.addComment(commentAddRequest, JSON.toJSONString(new ArrayList<>()), type)) {
            throw new CustomException("发表评论失败");
        }

        // 如果是回复评论
        if (Objects.nonNull(commentAddRequest.getReplyId())) {
            CommentReplyDTO commentReplyDto = new CommentReplyDTO(commentAddRequest.getId(), commentAddRequest
                .getReplyId());
            updateReplyList(commentAddRequest.getLandlord(), commentReplyDto);
            if (!commentAddRequest.getReplyId().equals(commentAddRequest.getLandlord())) {
                updateReplyList(commentAddRequest.getReplyId(), commentReplyDto);
            }
            updateComments(commentAddRequest.getReplyId(), commentAddRequest.getLandlord(), 1);
        }

        // 给站长发邮件
        CommentNoticeModel commentNoticeModel = new CommentNoticeModel(
            COMMENT_SUBJECT,
            commentAddRequest.getUrl(),
            commentAddRequest.getOriginalComment()
        );
        noticeActuator.sendUserCommentNotice(commentNoticeModel);

        // 如果是回复评论，则给被回复人发邮件
        if (noticeSwitch && Objects.nonNull(commentAddRequest.getReplyId())) {
            noticeActuator.sendUserReplyCommentNotice(new ReplyNoticeModel(
                COMMENT_SUBJECT,
                commentAddRequest.getReplyId(),
                commentAddRequest.getUrl()
            ));
        }
    }

    /**
     * TODO: 效率太低了，得优化优化
     */
    @Override
    public List<CommentListVO> getCommentList(Integer id, Integer type, Long uid, Integer page, Integer pageSize) throws CustomException {
        List<CommentListVO> commentList = new ArrayList<>();
        List<CommentListPO> commentListPOS = commentMapper.getCommentList(id, type, (page - 1) * pageSize, pageSize);
        if (commentListPOS.isEmpty()) {
            return commentList;
        }
        List<Long> uidList = new ArrayList<>();

        // 组装评论数据
        commentListPOS.forEach(v -> {
            CommentListVO commentListVO = CommentConvert.INSTANCE.commentListPo2Dto(v);
            if (!StatusEnum.PUBLIC.getStatus().equals(v.getStatus())) {
                commentListVO.setComment(commentDeleteWord);
                commentListVO.setOwner(false);
            } else {
                commentListVO.setOwner(v.getUid().equals(uid));
            }
            if (!uidList.contains(v.getUid())) {
                uidList.add(v.getUid());
            }
            commentListVO.setChildren(getCommentChildren(v.getReplyList(), uid, uidList));
            commentList.add(commentListVO);
        });

        // 获取评论用户数据
        TTSocket ttSocket = null;
        List<UserInfoReturnee> batchUserInfoList;
        try {
            ttSocket = ttClientPool.getConnect();
            batchUserInfoList = ttSocket.getUserClient().getBatchUserInfo(uidList);
            ttClientPool.returnConnection(ttSocket);
        } catch (TTCustomException e) {
            throw new CustomException(e.getMsg());
        } catch (Exception e) {
            ttClientPool.invalidateObject(ttSocket);
            e.printStackTrace();
            throw new CustomException("Thrift call error");
        }

        // 组装评论用户数据
        Map<Long, UserInfoReturnee> uid2UserInfo = new HashMap<>();
        batchUserInfoList.forEach(v -> uid2UserInfo.put(v.getId(), v));
        for (CommentListVO item: commentList) {
            // 拿到用户id
            UserInfoReturnee userInfoReturnee = uid2UserInfo.get(item.getUid());
            item.setAvatar(userInfoReturnee.getAvatar());
            item.setName(userInfoReturnee.getUsername());
            if (!item.getChildren().isEmpty()) {
                setCommentChildrenUserInfo(item.getChildren(), uid2UserInfo);
            }
        }

        return commentList;
    }

    @Override
    @Transactional
    public void delComment(Integer id, Long uid) throws CustomException {
        if(!commentMapper.delComment(id, uid)) {
            throw new CustomException("评论不存在");
        }
        CommentIdPO commentIdPo = commentMapper.getObjectId(id);
        if (Objects.nonNull(commentIdPo)) {
            updateComments(commentIdPo.getReplyId(), commentIdPo.getLandlord(), -1);
        }
    }

    /**
     * 更新replyList
     */
    private void updateReplyList(Integer id, CommentReplyDTO commentReplyDto) throws CustomException {
        String commentReplyList = commentMapper.getCommentReplyList(id);
        if (Objects.isNull(commentReplyList)) throw new CustomException("程序异常");
        List<CommentReplyDTO> commentReplyDTOList = JSONArray.parseArray(commentReplyList, CommentReplyDTO.class);
        commentReplyDTOList.add(commentReplyDto);
        if (!commentMapper.updateCommentReplyList(id, JSON.toJSONString(commentReplyDTOList))) {
            throw new CustomException("更新评论关系失败");
        }
    }

    /**
     * 更新评论
     */
    private void updateComments(Integer replyId, Integer landlord, int i) throws CustomException {
        Boolean updateReplyCommentsResult = commentMapper.updateComments(replyId, i);
        Boolean updateLandlordComments = true;
        if (!replyId.equals(landlord)) {
            updateLandlordComments = commentMapper.updateComments(landlord, i);
        }
        if (!(updateReplyCommentsResult && updateLandlordComments)) {
            throw new CustomException("回复数据更新失败");
        }
    }

    /**
     * 构建评论回复
     */
    private List<CommentChildDTO> getCommentChildren(String replyList, Long uid, List<Long> uidList) {
        List<CommentChildDTO> commentChildDTOList = new ArrayList<>();
        List<CommentReplyDTO> commentReplyDTOList = JSONArray.parseArray(replyList, CommentReplyDTO.class);
        commentReplyDTOList.forEach(v -> {
            CommentChildPO commentChildPo = commentMapper.getCommentChild(v.getStart());
            CommentChildDTO commentChildDto = CommentConvert.INSTANCE.commentChildPo2Dto(commentChildPo);
            if (!StatusEnum.PUBLIC.getStatus().equals(commentChildPo.getStatus())) {
                commentChildDto.setComment(commentDeleteWord);
                commentChildDto.setOwner(false);
            } else {
                commentChildDto.setOwner(commentChildPo.getUid().equals(uid));
            }
            if (!uidList.contains(commentChildPo.getUid())) {
                uidList.add(commentChildPo.getUid());
            }
            commentChildDTOList.add(commentChildDto);
        });
        return commentChildDTOList;
    }

    /**
     * 填充评论回复的用户信息
     */
    private void setCommentChildrenUserInfo(List<CommentChildDTO> children, Map<Long, UserInfoReturnee> uid2UserInfo) {
        for (CommentChildDTO item: children) {
            UserInfoReturnee userInfoReturnee = uid2UserInfo.get(item.getUid());
            item.setAvatar(userInfoReturnee.getAvatar());
            item.setName(userInfoReturnee.getUsername());
            Long uidByReplyId = commentMapper.getUidByReplyId(item.getReplyId());
            item.setReplyName(uid2UserInfo.get(uidByReplyId).getUsername());
        }
    }
}