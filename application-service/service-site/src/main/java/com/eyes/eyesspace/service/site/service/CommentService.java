package com.eyes.eyesspace.service.site.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;

import java.util.List;

public interface CommentService {

    void publishComment(CommentAddDto commentAddDto, Integer type) throws CustomException;

    List<CommentListDto> getCommentList(Integer id, Integer type, Integer uid, Integer page, Integer pageSize);

    void delComment(Integer id, Integer uid) throws CustomException;
}
