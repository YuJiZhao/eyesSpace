package com.eyes.eyesspace.service.site.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;

import java.util.List;

public interface AboutService {
    void doAboutComment(CommentAddDto commentAddDto) throws CustomException;

    List<CommentListDto> getAboutCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

    void delAboutComment(Integer id) throws CustomException;
}
