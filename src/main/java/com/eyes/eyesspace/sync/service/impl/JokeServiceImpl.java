package com.eyes.eyesspace.sync.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.file.FileUtils;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.persistent.dto.JokeAddDTO;
import com.eyes.eyesspace.persistent.mapper.ContextMapper;
import com.eyes.eyesspace.persistent.mapper.JokeMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.ContextPO;
import com.eyes.eyesspace.persistent.po.JokeAddCategoryPO;
import com.eyes.eyesspace.persistent.po.JokeListPO;
import com.eyes.eyesspace.sync.convert.JokeConvert;
import com.eyes.eyesspace.sync.model.dto.JokeListDTO;
import com.eyes.eyesspace.sync.model.request.JokeAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.JokeAddVO;
import com.eyes.eyesspace.sync.model.vo.JokeNoticeVO;
import com.eyes.eyesspace.sync.service.JokeService;
import com.eyes.eyesspace.utils.AuthUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/9/25 8:50
 */
@Slf4j
@Service
@RefreshScope
public class JokeServiceImpl implements JokeService {
  private static final List<Integer> JOKE_NOTICE_ID = Collections.singletonList(11);

  @Value("${path.url.site}")
  private String siteUrl;

  @Value("${path.folder.joke}")
  private String jokePath;

  @Value("${business.notice-switch.joke:false}")
  private Boolean jokeNoticeSwitch;

  private final TrackMapper trackMapper;

  private final ContextMapper contextMapper;

  private final JokeMapper jokeMapper;

  public JokeServiceImpl(TrackMapper trackMapper, ContextMapper contextMapper, JokeMapper jokeMapper) {
    this.trackMapper = trackMapper;
    this.contextMapper = contextMapper;
    this.jokeMapper = jokeMapper;
  }

  @Override
  @Transactional
  public JokeAddVO addJoke(JokeAddRequest jokeAddRequest) throws CustomException {
    JokeAddDTO jokeAddDTO = JokeConvert.INSTANCE.jokeAddRequest2DTO(jokeAddRequest);

    // 获得梗图分类
    Long categoryIndex = jokeMapper.getCategoryIdByName(jokeAddRequest.getCategory());
    if(Objects.nonNull(categoryIndex)) {
      jokeAddDTO.setCategoryId(categoryIndex);
    } else {
      JokeAddCategoryPO jokeAddCategoryPO = new JokeAddCategoryPO();
      jokeAddCategoryPO.setCategory(jokeAddRequest.getCategory());
      if(!jokeMapper.addJokeCategory(jokeAddCategoryPO)) {
        throw new CustomException("新增分类失败！");
      }
      jokeAddDTO.setCategoryId(jokeAddCategoryPO.getId());
    }

    jokeAddDTO.setUrlList(JSON.toJSONString(jokeAddRequest.getUrlList()));
    // 插入梗图
    if(!jokeMapper.addJoke(jokeAddDTO)) {
      throw new CustomException("新增梗图失败！");
    }
    return new JokeAddVO(jokeAddDTO.getId());
  }

  @Override
  public FileUploadVO uploadJokePic(MultipartFile multipartFile) throws CustomException {
    String originalFilename = multipartFile.getOriginalFilename();
    if (Objects.isNull(originalFilename)) {
      throw new CustomException("文件错误");
    }
    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
    if (!MediaConstant.imgContain(fileType)) {
      throw new CustomException("图片格式不支持");
    }

    String url = FileUtils.sUpload(multipartFile, jokePath);
    // 记录文件上传日志
    if (!trackMapper.addFileLog(
        FileOperationLogConstant.JOKE,
        UserInfoHolder.getUid(),
        FileMethodEnum.UPLOAD.getMethod(),
        url
    )) { log.error("Failed to record joke pic upload log"); }

    return new FileUploadVO(url);
  }

  @Override
  public JokeNoticeVO getJokeNotice() {
    List<ContextPO> context = contextMapper.getContext(JOKE_NOTICE_ID);
    return new JokeNoticeVO(context.get(0).getValue());
  }

  @Override
  public PageBind<JokeListDTO> getJokeList(Integer page, Integer pageSize) {
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);

    // 组装列表数据
    List<JokeListPO> jokeList = jokeMapper.getJokeList((page - 1) * pageSize, pageSize, statusCondition);
    List<JokeListDTO> jokeDTOList = jokeList.stream().map(v -> {
      JokeListDTO jokeListDTO = JokeConvert.INSTANCE.jokeListPO2DTO(v);
      jokeListDTO.setUrlList(JSON.parseArray(v.getUrlList(), String.class));
      return jokeListDTO;
    }).collect(Collectors.toList());

    // 获取梗图总数
    Integer jokeTotalNum = jokeMapper.getJokeTotalNum(statusCondition);

    return new PageBind<>(
        page,
        jokeTotalNum,
        jokeDTOList
    );
  }
}
