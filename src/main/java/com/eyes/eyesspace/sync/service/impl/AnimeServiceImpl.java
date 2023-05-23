package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.file.FileUtils;
import com.eyes.eyesspace.constant.CommentEnum;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.mapper.AnimeMapper;
import com.eyes.eyesspace.persistent.mapper.ContextMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.persistent.po.ContextPO;
import com.eyes.eyesspace.sync.model.dto.AnimeListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.AnimeInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListInfoVO;
import com.eyes.eyesspace.sync.model.vo.AnimeListVO;
import com.eyes.eyesspace.sync.model.vo.AnimeNoticeVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.service.AnimeService;
import com.eyes.eyesspace.sync.service.CommentService;
import com.eyes.eyesspace.utils.AuthUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/5/21 19:47
 */
@Slf4j
@Service
@RefreshScope
public class AnimeServiceImpl implements AnimeService {

  private static final List<Integer> ANIME_NOTICE_ID = Collections.singletonList(9);

  @Value("${path.url.site}")
  private String siteUrl;

  @Value("${path.folder.anime}")
  private String animePath;

  @Value("${path.url.anime-details}")
  private String animeDetailsPath;

  @Value("${business.notice-switch.anime:false}")
  private Boolean animeNoticeSwitch;

  private final TrackMapper trackMapper;

  private final ContextMapper contextMapper;

  private final AnimeMapper animeMapper;

  private final CommentService commentService;

  public AnimeServiceImpl(TrackMapper trackMapper, ContextMapper contextMapper, AnimeMapper animeMapper, CommentService commentService) {
    this.trackMapper = trackMapper;
    this.contextMapper = contextMapper;
    this.animeMapper = animeMapper;
    this.commentService = commentService;
  }

  @Override
  public FileUploadVO uploadAnimePic(MultipartFile multipartFile) throws CustomException {
    String originalFilename = multipartFile.getOriginalFilename();
    if (Objects.isNull(originalFilename)) {
      throw new CustomException("文件错误");
    }
    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
    if (!MediaConstant.imgContain(fileType)) {
      throw new CustomException("图片格式不支持");
    }

    String url = FileUtils.sUpload(multipartFile, animePath);
    // 记录文件上传日志
    if (!trackMapper.addFileLog(
        FileOperationLogConstant.ANIME,
        UserInfoHolder.getUid(),
        FileMethodEnum.UPLOAD.getMethod(),
        url
    )) { log.error("Failed to record anime pic upload log"); }

    return new FileUploadVO(url);
  }

  @Override
  public AnimeNoticeVO getAnimeNotice() {
    List<ContextPO> context = contextMapper.getContext(ANIME_NOTICE_ID);
    return new AnimeNoticeVO(context.get(0).getValue());
  }

  @Override
  public AnimeListInfoVO getAnimeListInfo() {
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);
    List<Integer> statusList = animeMapper.getAnimeStatusList();
    Map<Integer, Long> statusMap = statusList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    AnimeListInfoVO result = new AnimeListInfoVO(
        animeMapper.getAnimeViewNum(statusCondition),
        animeMapper.getAnimeCommentNum(statusCondition)
    );
    // 游客与普通用户只返回三个字段
    if (!AuthConfigConstant.ROLE_ADMIN.equals(role)) {
      result.setTotalNum(statusMap.getOrDefault(StatusEnum.PUBLIC.getStatus(), 0L));
      return result;
    }
    // 对于管理员，还需要返回公开、私有、删除数据
    result.setTotalNum((long) statusList.size());
    result.setPublicNum(statusMap.getOrDefault(StatusEnum.PUBLIC.getStatus(), 0L));
    result.setPrivateNum(statusMap.getOrDefault(StatusEnum.PRIVATE.getStatus(), 0L));
    result.setDeleteNum(statusMap.getOrDefault(StatusEnum.DELETE.getStatus(), 0L));
    return result;
  }

  @Override
  public AnimeListVO getAnimeList(Integer page, Integer pageSize) {
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);
    List<AnimeListDTO> animeDTOList = animeMapper.getAnimeList((page - 1) * pageSize, pageSize, statusCondition);
    return new AnimeListVO(
        animeMapper.getAnimeNum(statusCondition),
        animeDTOList
    );
  }

  @Override
  public AnimeInfoVO getAnimeInfo(Integer id) throws CustomException {
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);
    AnimeInfoVO result = animeMapper.getAnimeInfo(id, statusCondition);
    if (Objects.isNull(result)) {
      throw new CustomException("该动漫不存在");
    }
    // 更新点击量
    if (!animeMapper.addView(id)) {
      log.error("动漫点击量更新失败");
    }
    return result;
  }

  @Override
  public List<AnimeListDTO> getAnimeListByIds(List<Integer> ids) {
    return animeMapper.getAnimeListByIds(ids);
  }

  @Override
  @Transactional
  public void doAnimeComment(CommentAddRequest commentAddRequest) throws CustomException {
    // 校验可行性
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);
    AnimeInfoVO animeInfoVO = animeMapper.getAnimeInfo(commentAddRequest.getObjectId(), statusCondition);
    if (Objects.isNull(animeInfoVO)) {
      throw new CustomException("动漫不存在");
    }

    // 执行评论业务
    Long uid = UserInfoHolder.getUid();
    commentAddRequest.setUid(uid);
    commentAddRequest.setUrl(siteUrl + animeDetailsPath + commentAddRequest.getObjectId());
    commentService.publishComment(commentAddRequest, CommentEnum.ANIME.getType(), animeNoticeSwitch);
    if (!animeMapper.updateAnimeComments(commentAddRequest.getObjectId(), 1)) {
      throw new CustomException("评论数据更新失败");
    }
  }

  @Override
  public List<CommentListVO> getAnimeCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
    // 校验可行性
    String role = UserInfoHolder.getRole();
    String statusCondition = AuthUtils.statusSqlCondition(role);
    AnimeInfoVO animeInfoVO = animeMapper.getAnimeInfo(id, statusCondition);
    if (Objects.isNull(animeInfoVO)) {
      throw new CustomException("动漫不存在");
    }

    // 执行业务
    Long uid = UserInfoHolder.getUid();
    return commentService.getCommentList(id, CommentEnum.ANIME.getType(), uid, page, pageSize);
  }

  @Override
  public void delAnimeComment(Integer id) throws CustomException {
    String role = UserInfoHolder.getRole();
    // 可行性检查
    CommentDelInfoPO commentDelInfoPo = animeMapper.getAnimeCommentInfo(id);
    if (
        Objects.isNull(commentDelInfoPo) ||
            !CommentEnum.ANIME.getType().equals(commentDelInfoPo.getType()) ||
            (AuthConfigConstant.ROLE_USER.equals(role) && !StatusEnum.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
            (AuthConfigConstant.ROLE_ADMIN.equals(role) && StatusEnum.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
    ) { throw new CustomException("动漫不存在"); }

    // 执行业务
    Long uid = UserInfoHolder.getUid();
    commentService.delComment(id, uid);
    if (!animeMapper.updateAnimeComments(commentDelInfoPo.getObjectId(), -1)) {
      throw new CustomException("评论数据更新失败");
    }
  }
}
