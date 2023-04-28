package com.eyes.eyesspace.sync.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.file.FileUtils;
import com.eyes.eyesTools.utils.SecurityUtils;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.dto.VideoInfoDTO;
import com.eyes.eyesspace.persistent.dto.VideoListDTO;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.mapper.VideoMapper;
import com.eyes.eyesspace.persistent.po.UserVideoPO;
import com.eyes.eyesspace.persistent.po.VideoDataPO;
import com.eyes.eyesspace.sync.common.result.ResultCode;
import com.eyes.eyesspace.sync.convert.VideoConvert;
import com.eyes.eyesspace.sync.model.bean.UserVideoKeyBean;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import com.eyes.eyesspace.sync.model.vo.VideoAddVO;
import com.eyes.eyesspace.sync.model.vo.VideoListInfoVO;
import com.eyes.eyesspace.sync.model.request.VideoAddRequest;
import com.eyes.eyesspace.sync.service.VideoService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RefreshScope
@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Value("${path.folder.video}")
    private String videoPath;

    @Value("${path.folder.video-cover}")
    private String videoCoverPath;

    @Value("${business.media.video-max-play}")
    private Integer videoMaxPlayNum;

    @Value("${business.media.video-memory-num}")
    private Integer videoMemoryNum;

    private final VideoMapper videoMapper;

    private final TrackMapper trackMapper;

    public VideoServiceImpl(VideoMapper videoMapper, TrackMapper trackMapper) {
        this.videoMapper = videoMapper;
        this.trackMapper = trackMapper;
    }

    @Override
    public VideoAddVO addVideo(VideoAddRequest videoAddRequest) throws CustomException {
        if (!videoMapper.addVideo(videoAddRequest)) {
            throw new CustomException("视频添加失败！");
        }
        return new VideoAddVO(videoAddRequest.getId());
    }

    @Override
    public FileUploadVO addVideoCover(MultipartFile multipartFile) throws CustomException {
        String url = FileUtils.sUpload(multipartFile, videoCoverPath);
        if (!trackMapper.addFileLog(
            FileOperationLogConstant.VIDEO_COVER,
            UserInfoHolder.getUid(),
            FileMethodEnum.UPLOAD.getMethod(),
            url
        )) { log.error("Failed to record video cover upload log"); }
        return new FileUploadVO(url);
    }

    @Override
    public FileUploadVO addVideoFile(MultipartFile multipartFile) throws CustomException {
        String url = FileUtils.bUpload(multipartFile, videoPath);
        if (!trackMapper.addFileLog(
            FileOperationLogConstant.VIDEO,
            UserInfoHolder.getUid(),
            FileMethodEnum.UPLOAD.getMethod(),
            url
        )) { log.error("Failed to record video upload log"); }
        return new FileUploadVO(url);
    }

    @Override
    public List<VideoListDTO> getVideoList(Integer page, Integer pageSize) {
        return videoMapper.getVideoList((page - 1) * pageSize, pageSize);
    }

    @Override
    public VideoListInfoVO getVideoListInfo() {
        VideoDataPO videoDataPo = videoMapper.getVideoData();
        return new VideoListInfoVO(
                videoMapper.getVideoListInfo(null),
                videoMapper.getVideoListInfo(StatusEnum.PUBLIC.getStatus()),
                videoMapper.getVideoListInfo(StatusEnum.PRIVATE.getStatus()),
                videoMapper.getVideoListInfo(StatusEnum.DELETE.getStatus()),
                videoDataPo.getViewsNum(),
                videoDataPo.getLikesNum(),
                videoDataPo.getCommentsNum()
        );
    }

    @Override
    public VideoInfoDTO getVideoInfo(Integer id) {
        return videoMapper.getVideoInfo(id);
    }

    @Override
    public UserVideoInfoVO getVideoInfoByUser() throws CustomException {
        String role = UserInfoHolder.getRole();
        Long uid = UserInfoHolder.getUid();

        // Key处理
        String userVideoKey = videoMapper.getUserVideoKey(uid);
        UserVideoPO userVideoPo;
        if(Objects.isNull(userVideoKey)) {
            userVideoPo = createUserVideoKey(uid, role);
        } else {
            userVideoPo = updateUserVideoKey(userVideoKey, uid, role);
        }

        // 构建Dto
        UserVideoInfoVO uservideoInfoVO = VideoConvert.INSTANCE.userVideoPo2InfoDto(userVideoPo);
        try {
            uservideoInfoVO.setIsLike(
                Objects.nonNull(videoMapper.existUserLike(uid, userVideoPo.getId()))
            );
            uservideoInfoVO.setId(SecurityUtils.symmetricEncrypt(userVideoPo.getId().toString()));
        } catch(Exception e) {
            throw new CustomException("生成数据失败");
        }

        // 更新播放量
        uservideoInfoVO.setViews(uservideoInfoVO.getViews() + 1);
        if(!videoMapper.updateViewsNum(userVideoPo.getId())) {
            log.error("视频播放量更新失败,id:{}", userVideoPo.getId());
        }

        return uservideoInfoVO;
    }

    @Override
    public void doUserLike(String id) throws CustomException {
        Long uid = UserInfoHolder.getUid();

        // 解析视频id
        Integer videoId;
        try {
            videoId = Integer.valueOf(SecurityUtils.symmetricDecrypt(id));
        } catch (NumberFormatException e) {
            throw new CustomException("这就很神奇了，但是没有视频给你点赞");
        } catch(Exception e) {
            throw new CustomException("视频不存在");
        }

        // 检查是否已经点赞
        Integer existUserLikeResult = videoMapper.existUserLike(uid, videoId);
        if(Objects.nonNull(existUserLikeResult)) {  // 已经点赞则取消点赞
            if(!videoMapper.delUserLike(videoId, uid)) throw new CustomException("数据库异常，取消点赞失败");
            if(!videoMapper.updateLikesNum(videoId, -1)) throw new CustomException("数据库异常，取消点赞失败");
        } else {  // 未点赞则执行点赞
            if(!videoMapper.createUserLike(videoId, uid)) throw new CustomException("数据库异常，点赞失败");
            if(!videoMapper.updateLikesNum(videoId, 1)) throw new CustomException("数据库异常，取消点赞失败");
        }
    }

    /**
     * 创建UserVideoKey
     */
    private UserVideoPO createUserVideoKey(Long uid, String role) {
        UserVideoPO randomVideoInfo = videoMapper.getRandomVideo(role, null);

        // 获取id列表
        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(randomVideoInfo.getId());

        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 封装UserVideoKey
        UserVideoKeyBean userVideoKeyBean = new UserVideoKeyBean(
            idList,
            formatter.format(date),
            1
        );
        // 插入UserVideoKey
        if(!videoMapper.insertUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean))) {
            log.error("UserVideoKey插入错误");
        }
        return randomVideoInfo;
    }

    /**
     * 更新UserVideoKey
     */
    private UserVideoPO updateUserVideoKey(String userVideoKey, Long uid, String role) throws CustomException {
        UserVideoKeyBean userVideoKeyBean = JSON.parseObject(userVideoKey, UserVideoKeyBean.class);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        // 判断是否达到播放上限
        if(userVideoKeyBean.getTime().equals(time) && userVideoKeyBean.getNum() >= videoMaxPlayNum) {
            throw new CustomException(ResultCode.NO_TIMES);
        }
        // 随机获取视频
        UserVideoPO currentVideo = videoMapper.getRandomVideo(role, userVideoKeyBean.getVideos());
        if (Objects.isNull(currentVideo)) {
            throw new CustomException("暂无有效视频");
        }
        userVideoKeyBean.getVideos().add(currentVideo.getId());
        // 如果记忆视频数超过最大限度，则释放list头部元素
        if(userVideoKeyBean.getVideos().size() > videoMemoryNum) {
            userVideoKeyBean.getVideos().subList(0, 5).clear();
        }
        // 更新time与num
        if(userVideoKeyBean.getTime().equals(time)) {
            userVideoKeyBean.setNum(userVideoKeyBean.getNum() + 1);
        } else {
            userVideoKeyBean.setTime(time);
            userVideoKeyBean.setNum(1);
        }
        // 更新数据库key
        if(!videoMapper.updateUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean))) {
            log.error("UserVideoKey更新错误");
        }
        return currentVideo;
    }
}