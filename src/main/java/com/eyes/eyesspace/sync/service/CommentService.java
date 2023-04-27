package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;

import java.util.List;

public interface CommentService {

    void publishComment(CommentAddRequest commentAddRequest, Integer type, Boolean noticeSwitch) throws CustomException;

    List<CommentListVO> getCommentList(Integer id, Integer type, Long uid, Integer page, Integer pageSize) throws CustomException;

    void delComment(Integer id, Long uid) throws CustomException;
}
