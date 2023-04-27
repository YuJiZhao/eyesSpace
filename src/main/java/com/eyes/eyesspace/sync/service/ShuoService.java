package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.request.ShuoCommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.sync.model.vo.ShuoListVO;
import com.eyes.eyesspace.sync.model.request.ShuoAddRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShuoService {
    void addShuoshuo(ShuoAddRequest shuoAddRequest) throws CustomException;

    ShuoListInfoVO getShuoshuoListInfo() throws CustomException;

    List<ShuoListVO> getShuoshuoList(Integer page, Integer pageSize) throws CustomException;

    ShuoListVO getShuoshuoInfo(Integer id) throws CustomException;

    ShuoListVO getShuoshuoInfoByString(String id) throws CustomException;

    void doShuoComment(ShuoCommentAddRequest shuoCommentAddRequest) throws CustomException;

    List<CommentListVO> getShuoCommentList(String id, Integer page, Integer pageSize) throws CustomException;

    void delShuoComment(Integer id) throws CustomException;

    FileUploadVO uploadShuoshuoPic(MultipartFile multipartFile) throws CustomException;
}
