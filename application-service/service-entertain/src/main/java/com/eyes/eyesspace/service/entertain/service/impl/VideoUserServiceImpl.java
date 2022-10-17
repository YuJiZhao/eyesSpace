package com.eyes.eyesspace.service.entertain.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import com.eyes.eyesspace.common.service.utils.SecurityUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.entertain.convert.VideoUserConvert;
import com.eyes.eyesspace.service.entertain.mapper.VideoUserMapper;
import com.eyes.eyesspace.service.entertain.module.video.bean.UserVideoKeyBean;
import com.eyes.eyesspace.service.entertain.module.video.dto.UserVideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.po.UserVideoPo;
import com.eyes.eyesspace.service.entertain.service.VideoUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class VideoUserServiceImpl implements VideoUserService {

    private final VideoUserMapper videoUserMapper;
    private final String ID_HEADER = ConfigContext.getString("ID_HEADER");
    private final String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");
    private final Integer MAX_VIDEO_PLAY = ConfigContext.getInt("MAX_VIDEO_PLAY");
    private final Integer VIDEO_MEMORY_NUM = ConfigContext.getInt("VIDEO_MEMORY_NUM");

    public VideoUserServiceImpl(VideoUserMapper videoUserMapper) {
        this.videoUserMapper = videoUserMapper;
    }

    @Override
    public UserVideoInfoDto getVideoInfoByUser() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        // Key处理
        String userVideoKey = videoUserMapper.getUserVideoKey(uid);
        UserVideoPo userVideoPo;
        if(Objects.isNull(userVideoKey)) {
            userVideoPo = createUserVideoKey(uid, role, request);
        } else {
            userVideoPo = updateUserVideoKey(userVideoKey, uid, role, request);
        }

        // 构建Dto
        UserVideoInfoDto uservideoInfoDto = VideoUserConvert.INSTANCE.userVideoPo2InfoDto(userVideoPo);
        try {
            uservideoInfoDto.setIsLike(
                Objects.nonNull(videoUserMapper.existUserLike(uid, userVideoPo.getId()))
            );
            uservideoInfoDto.setId(SecurityUtils.symmetricEncrypt(userVideoPo.getId().toString()));
        } catch(Exception e) {
            throw new CustomException("生成数据失败");
        }

        // 更新播放量
        uservideoInfoDto.setViews(uservideoInfoDto.getViews() + 1);
        if(!videoUserMapper.updateViewsNum(userVideoPo.getId())) {
            LogUtils.error(LogUtils.logMap(
                    "msg", "视频播放量更新失败",
                    "video_id", userVideoPo.getId()
            ));
        }

        return uservideoInfoDto;
    }

    @Override
    public void doUserLike(String id) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String uid;
        if ( Objects.isNull(uid = request.getHeader(ID_HEADER))) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }

        // 解析视频id
        Integer videoId;
        try {
            videoId = Integer.valueOf(SecurityUtils.symmetricDecrypt(id));
        } catch (NumberFormatException e) {
            throw new CustomException("这就很神奇了，但是没有视频给你");
        } catch(Exception e) {
            throw new CustomException("视频不存在");
        }

        // 检查是否已经点赞
        try {
            Integer existUserLikeResult = videoUserMapper.existUserLike(Integer.valueOf(uid), videoId);
            if(Objects.nonNull(existUserLikeResult)) {
                Integer delUserLikeResult = videoUserMapper.delUserLike(videoId, Integer.valueOf(uid));
                if(delUserLikeResult != 1) throw new CustomException("数据库异常，取消点赞失败");
                Integer updateLikesNumResult = videoUserMapper.updateLikesNum(videoId, -1);
                if(updateLikesNumResult != 1) throw new CustomException("数据库异常，取消点赞失败");
            } else {
                Integer createUserLikeResult = videoUserMapper.createUserLike(videoId, Integer.valueOf(uid));
                if(createUserLikeResult != 1) throw new CustomException("数据库异常，点赞失败");
                Integer updateLikesNumResult = videoUserMapper.updateLikesNum(videoId, 1);
                if(updateLikesNumResult != 1) throw new CustomException("数据库异常，取消点赞失败");
            }
        } catch(Exception e) {
            videoUserMapper.delUserLike(videoId, Integer.valueOf(uid));
        }
    }

    /**
     * 创建UserVideoKey
     * @param uid
     * @return
     */
    private UserVideoPo createUserVideoKey(Integer uid, String role, HttpServletRequest request) {
        UserVideoPo randomVideoInfo = videoUserMapper.getRandomVideoList(role).get(0);

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
        Integer result = videoUserMapper.insertUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean));
        if(result != 1) {
            LogUtils.error(request, "UserVideoKey插入错误");
        }
        return randomVideoInfo;
    }

    /**
     * 更新UserVideoKey
     * @param userVideoKey
     * @return
     */
    private UserVideoPo updateUserVideoKey(String userVideoKey, Integer uid, String role, HttpServletRequest request) throws CustomException {
        UserVideoKeyBean userVideoKeyBean = JSON.parseObject(userVideoKey, UserVideoKeyBean.class);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        // 判断是否达到播放上限
        if(userVideoKeyBean.getTime().equals(time) && userVideoKeyBean.getNum() >= MAX_VIDEO_PLAY) {
            throw new CustomException(ResultCode.NO_TIMES);
        }
        // 随机获取视频
        UserVideoPo currentVideo = null;
        do {
            List<UserVideoPo> randomVideoList = videoUserMapper.getRandomVideoList(role);
            for (UserVideoPo item: randomVideoList) {
                if(userVideoKeyBean.getVideos().contains(item.getId())) continue;
                userVideoKeyBean.getVideos().add(item.getId());
                currentVideo = item;
                break;
            }
        } while(Objects.isNull(currentVideo));
        // 如果记忆视频数超过最大限度，则释放list头部元素
        if(userVideoKeyBean.getVideos().size() > VIDEO_MEMORY_NUM) {
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
        Integer result = videoUserMapper.updateUserVideoKey(uid, JSON.toJSONString(userVideoKeyBean));
        if(result != 1) {
            LogUtils.error(request, "UserVideoKey更新错误");
        }
        return currentVideo;
    }
}
