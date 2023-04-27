package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;

import java.util.List;

public interface MessageService {
    void doMessage(CommentAddRequest commentAddRequest) throws CustomException;

    List<CommentListVO> getMessageList(Integer id, Integer page, Integer pageSize) throws CustomException;

    void delMessage(Integer id) throws CustomException;
}
