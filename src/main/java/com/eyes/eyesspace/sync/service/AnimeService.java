package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.dto.AnimeListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListVO;
import com.eyes.eyesspace.sync.model.vo.AnimeNoticeVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/5/21 19:47
 */

public interface AnimeService {

  FileUploadVO uploadAnimePic(MultipartFile multipartFile) throws CustomException;

  AnimeNoticeVO getAnimeNotice();

  AnimeListInfoVO getAnimeListInfo();

  AnimeListVO getAnimeList(Integer page, Integer pageSize);

  AnimeInfoVO getAnimeInfo(Integer id) throws CustomException;

  List<AnimeListDTO> getAnimeListByIds(List<Integer> ids);

  void doAnimeComment(CommentAddRequest commentAddRequest) throws CustomException;

  List<CommentListVO> getAnimeCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

  void delAnimeComment(Integer id) throws CustomException;
}
