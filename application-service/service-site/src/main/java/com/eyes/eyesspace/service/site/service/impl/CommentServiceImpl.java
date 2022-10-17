package com.eyes.eyesspace.service.site.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.tool.context.StatusContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.site.convert.CommentConvert;
import com.eyes.eyesspace.service.site.mapper.CommentMapper;
import com.eyes.eyesspace.service.site.model.po.CommentChildPo;
import com.eyes.eyesspace.service.site.model.po.CommentIdPo;
import com.eyes.eyesspace.service.site.model.po.CommentListPo;
import com.eyes.eyesspace.service.site.model.po.CommentUserInfo;
import com.eyes.eyesspace.service.site.service.CommentService;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentChildDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentReplyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private static final String COMMENT_DELETE = ConfigContext.getString("COMMENT_DELETE");

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public void publishComment(CommentAddDto commentAddDto, Integer type) throws CustomException {
        Boolean addCommentResult = commentMapper.addComment(commentAddDto, JSON.toJSONString(new ArrayList<>()), type);
        if (!addCommentResult) throw new CustomException("发表评论失败");

        if (Objects.isNull(commentAddDto.getReplyId())) return;
        CommentReplyDto commentReplyDto = new CommentReplyDto(commentAddDto.getId(), commentAddDto.getReplyId());
        updateReplyList(commentAddDto.getLandlord(), commentReplyDto);
        if (!commentAddDto.getReplyId().equals(commentAddDto.getLandlord())) {
            updateReplyList(commentAddDto.getReplyId(), commentReplyDto);
        }

        updateComments(commentAddDto.getReplyId(), commentAddDto.getLandlord(), 1);
    }

    @Override
    public List<CommentListDto> getCommentList(Integer id, Integer type, Integer uid, Integer page, Integer pageSize) {
        List<CommentListDto> commentList = new ArrayList<>();
        List<CommentListPo> commentListPos = commentMapper.getCommentList(id, type, (page - 1) * pageSize, pageSize);
        commentListPos.forEach((v) -> {
            CommentListDto commentListDto = CommentConvert.INSTANCE.commentListPo2Dto(v);
            if (!StatusContext.PUBLIC.getStatus().equals(v.getStatus())) {
                commentListDto.setComment(COMMENT_DELETE);
                commentListDto.setOwner(false);
            } else {
                commentListDto.setOwner(v.getUid().equals(uid));
            }
            CommentUserInfo commentUserInfo = commentMapper.getCommentUserInfo(v.getUid());
            commentListDto.setAvatar(commentUserInfo.getAvatar());
            commentListDto.setName(commentUserInfo.getName());
            commentListDto.setChildren(getCommentChildren(v.getReplyList(), uid));
            commentList.add(commentListDto);
        });
        return commentList;
    }

    @Override
    @Transactional
    public void delComment(Integer id, Integer uid) throws CustomException {
        if(!commentMapper.delComment(id, uid)) {
            throw new CustomException("评论不存在");
        }
        CommentIdPo commentIdPo = commentMapper.getObjectId(id);
        if (Objects.nonNull(commentIdPo)) {
            updateComments(commentIdPo.getReplyId(), commentIdPo.getLandlord(), -1);
        }
    }

    /**
     * 更新replyList
     * @param id
     * @param commentReplyDto
     * @throws CustomException
     */
    private void updateReplyList(Integer id, CommentReplyDto commentReplyDto) throws CustomException {
        String commentReplyList = commentMapper.getCommentReplyList(id);
        if (Objects.isNull(commentReplyList)) throw new CustomException("程序异常");
        List<CommentReplyDto> commentReplyDtoList = JSONArray.parseArray(commentReplyList, CommentReplyDto.class);
        commentReplyDtoList.add(commentReplyDto);
        if (
            !commentMapper.updateCommentReplyList(id, JSON.toJSONString(commentReplyDtoList))
        ) throw new CustomException("更新评论关系失败");
    }

    private void updateComments(Integer replyId, Integer landlord, int i) throws CustomException {
        Boolean updateReplyCommentsResult = commentMapper.updateComments(replyId, i);
        Boolean updateLandlordComments = true;
        if (!replyId.equals(landlord)) {
            updateLandlordComments = commentMapper.updateComments(landlord, i);
        }
        if (!(updateReplyCommentsResult && updateLandlordComments)) throw new CustomException("回复数据更新失败");
    }

    /**
     * 构建评论回复
     * @param replyList
     * @return
     */
    private List<CommentChildDto> getCommentChildren(String replyList, Integer uid) {
        List<CommentChildDto> commentChildDtoList = new ArrayList<>();
        List<CommentReplyDto> commentReplyDtoList = JSONArray.parseArray(replyList, CommentReplyDto.class);
        commentReplyDtoList.forEach((v) -> {
            CommentChildPo commentChildPo = commentMapper.getCommentChild(v.getStart());
            CommentChildDto commentChildDto = CommentConvert.INSTANCE.commentChildPo2Dto(commentChildPo);
            if (!StatusContext.PUBLIC.getStatus().equals(commentChildPo.getStatus())) {
                commentChildDto.setComment(COMMENT_DELETE);
                commentChildDto.setOwner(false);
            } else {
                commentChildDto.setOwner(commentChildPo.getUid().equals(uid));
            }
            commentChildDto.setReplyName(commentMapper.getReplyNameById(commentChildPo.getReplyId()));
            CommentUserInfo commentUserInfo = commentMapper.getCommentUserInfo(commentChildPo.getUid());
            commentChildDto.setName(commentUserInfo.getName());
            commentChildDto.setAvatar(commentUserInfo.getAvatar());
            commentChildDtoList.add(commentChildDto);
        });
        return commentChildDtoList;
    }
}
