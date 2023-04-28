package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.dto.ShuoListDTO;
import com.eyes.eyesspace.sync.model.request.ShuoAddRequest;
import com.eyes.eyesspace.sync.model.request.ShuoCommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.sync.model.vo.ShuoListVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ShuoService {
    void addShuo(ShuoAddRequest shuoAddRequest) throws CustomException;

    FileUploadVO uploadShuoPic(MultipartFile multipartFile) throws CustomException;

    ShuoListInfoVO getShuoListInfo() throws CustomException;

    List<ShuoListVO> getShuoList(Integer page, Integer pageSize) throws CustomException;

    List<ShuoListDTO> getShuoListByIds(List<Integer> ids);

    ShuoListVO getShuoInfo(String id) throws CustomException;

    void doShuoComment(ShuoCommentAddRequest shuoCommentAddRequest) throws CustomException;

    List<CommentListVO> getShuoCommentList(String id, Integer page, Integer pageSize) throws CustomException;

    void delShuoComment(Integer id) throws CustomException;
}
