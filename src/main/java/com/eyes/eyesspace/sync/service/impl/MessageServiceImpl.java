package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.constant.CommentEnum;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.service.MessageService;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@RefreshScope
@Service
public class MessageServiceImpl implements MessageService {
    @Value("${path.url.site}")
    private String siteUrl;

    @Value("${path.url.message}")
    private String msgPagePath;

    @Value("${business.notice-switch.message:false}")
    private Boolean msgNoticeSwitch;

    @Value("${program.message-object-id}")
    private Integer msgObjectId;

    private final CommentService commentService;

    public MessageServiceImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void doMessage(CommentAddRequest commentAddRequest) throws CustomException {
        Long uid = UserInfoHolder.getUid();
        if (!commentAddRequest.getObjectId().equals(msgObjectId)) {
            throw new CustomException("非法留言操作");
        }

        commentAddRequest.setUid(uid);
        commentAddRequest.setUrl(siteUrl + msgPagePath);
        commentService.publishComment(commentAddRequest, CommentEnum.MESSAGE.getType(), msgNoticeSwitch);
    }

    @Override
    public List<CommentListVO> getMessageList(Integer id, Integer page, Integer pageSize) throws CustomException {
        Long uid = UserInfoHolder.getUid();
        if (!id.equals(msgObjectId)) {
            throw new CustomException("非法留言操作");
        }

        return commentService.getCommentList(id, CommentEnum.MESSAGE.getType(), uid, page, pageSize);
    }

    @Override
    public void delMessage(Integer id) throws CustomException {
        Long uid = UserInfoHolder.getUid();
        commentService.delComment(id, uid);
    }
}
