package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.sync.model.dto.JokeListDTO;
import com.eyes.eyesspace.sync.model.request.JokeAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.JokeAddVO;
import com.eyes.eyesspace.sync.model.vo.JokeNoticeVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/9/25 8:50
 */
public interface JokeService {
  JokeAddVO addJoke(JokeAddRequest jokeAddRequest) throws CustomException;

  FileUploadVO uploadJokePic(MultipartFile multipartFile) throws CustomException;

  JokeNoticeVO getJokeNotice();

  PageBind<JokeListDTO> getJokeList(Integer page, Integer jokePageSize);
}
