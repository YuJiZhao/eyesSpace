package com.eyes.eyesspace.service.article.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoCommentAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoListInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShuoshuoService {
    void addShuoshuo(ShuoshuoAddDto shuoshuoAddDto) throws CustomException;

    ShuoshuoListInfoDto getShuoshuoListInfo() throws CustomException;

    List<ShuoshuoListDto> getShuoshuoList(Integer page, Integer pageSize) throws CustomException;

    ShuoshuoListDto getShuoshuoInfo(Integer id) throws CustomException;

    ShuoshuoListDto getShuoshuoInfoByString(String id) throws CustomException;

    void doShuoComment(ShuoshuoCommentAddDto shuoshuoCommentAddDto) throws CustomException;

    List<CommentListDto> getShuoCommentList(String id, Integer page, Integer pageSize) throws CustomException;

    void delShuoComment(Integer id) throws CustomException;

    FileUploadDto uploadShuoshuoPic(MultipartFile multipartFile) throws CustomException;
}
