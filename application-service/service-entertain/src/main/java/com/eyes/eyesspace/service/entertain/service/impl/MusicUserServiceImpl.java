package com.eyes.eyesspace.service.entertain.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import com.eyes.eyesspace.common.service.utils.SecurityUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.entertain.convert.MusicUserConvert;
import com.eyes.eyesspace.service.entertain.mapper.MusicUserMapper;
import com.eyes.eyesspace.service.entertain.module.music.bean.UserMusicKeyBean;
import com.eyes.eyesspace.service.entertain.module.music.dto.UserMusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.po.UserMusicPo;
import com.eyes.eyesspace.service.entertain.service.MusicUserService;
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
public class MusicUserServiceImpl implements MusicUserService {

    private final MusicUserMapper musicUserMapper;
    private final String ID_HEADER = ConfigContext.getString("ID_HEADER");
    private final String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");
    private final Integer MAX_MUSIC_PLAY = ConfigContext.getInt("MAX_MUSIC_PLAY");
    private final Integer MUSIC_MEMORY_NUM = ConfigContext.getInt("MUSIC_MEMORY_NUM");

    public MusicUserServiceImpl(MusicUserMapper musicUserMapper) {
        this.musicUserMapper = musicUserMapper;
    }

    @Override
    public UserMusicInfoDto getMusicInfoByUser() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        // Key处理
        String userMusicKey = musicUserMapper.getUserMusicKey(uid);
        UserMusicPo userMusicPo;
        if(Objects.isNull(userMusicKey)) {
            userMusicPo = createUserMusicKey(uid, role, request);
        } else {
            userMusicPo = updateUserMusicKey(userMusicKey, uid, role, request);
        }

        // 构建Dto
        UserMusicInfoDto userMusicInfoDto = MusicUserConvert.INSTANCE.userMusicPo2InfoDto(userMusicPo);
        try {
            userMusicInfoDto.setIsLike(
                Objects.nonNull(musicUserMapper.existUserLike(uid, userMusicPo.getId()))
            );
            userMusicInfoDto.setId(SecurityUtils.symmetricEncrypt(userMusicPo.getId().toString()));
        } catch(Exception e) {
            throw new CustomException("生成数据失败");
        }

        // 更新播放量
        userMusicInfoDto.setViews(userMusicInfoDto.getViews() + 1);
        if(!musicUserMapper.updateViewsNum(userMusicPo.getId())) {
            LogUtils.error(LogUtils.logMap(
                    "msg", "歌曲播放量更新失败",
                    "music_id", userMusicPo.getId()
            ));
        }

        return userMusicInfoDto;
    }

    @Override
    public void doUserLike(String id) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String uid;
        if ( Objects.isNull(uid = request.getHeader(ID_HEADER))) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }

        // 解析歌曲id
        Integer musicId;
        try {
            musicId = Integer.valueOf(SecurityUtils.symmetricDecrypt(id));
        } catch (NumberFormatException e) {
            throw new CustomException("这就很神奇了，但是没有音频给你");
        } catch(Exception e) {
            throw new CustomException("歌曲不存在");
        }

        // 检查是否已经点赞
        try {
            Integer existUserLikeResult = musicUserMapper.existUserLike(Integer.valueOf(uid), musicId);
            if(Objects.nonNull(existUserLikeResult)) {
                Integer delUserLikeResult = musicUserMapper.delUserLike(musicId, Integer.valueOf(uid));
                if(delUserLikeResult != 1) throw new CustomException("数据库异常，取消点赞失败");
                Integer updateLikesNumResult = musicUserMapper.updateLikesNum(musicId, -1);
                if(updateLikesNumResult != 1) throw new CustomException("数据库异常，取消点赞失败");
            } else {
                Integer createUserLikeResult = musicUserMapper.createUserLike(musicId, Integer.valueOf(uid));
                if(createUserLikeResult != 1) throw new CustomException("数据库异常，点赞失败");
                Integer updateLikesNumResult = musicUserMapper.updateLikesNum(musicId, 1);
                if(updateLikesNumResult != 1) throw new CustomException("数据库异常，点赞失败");
            }
        } catch(Exception e) {
            // TODO: 清除所有点赞记录，然后插入点赞数据
            musicUserMapper.delUserLike(musicId, Integer.valueOf(uid));
        }
    }

    /**
     * 创建UserMusicKey
     * @param uid
     * @return
     */
    private UserMusicPo createUserMusicKey(Integer uid, String role, HttpServletRequest request) {
        UserMusicPo randomMusicInfo = musicUserMapper.getRandomMusicList(role).get(0);

        // 获取id列表
        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(randomMusicInfo.getId());

        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // 封装UserMusicKey
        UserMusicKeyBean userMusicKeyBean = new UserMusicKeyBean(
                idList,
                formatter.format(date),
                1
        );
        // 插入UserMusicKey
        Integer result = musicUserMapper.insertUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean));
        if(result != 1) {
            LogUtils.error(request, "UserMusicKey插入错误");
        }
        return randomMusicInfo;
    }

    /**
     * 更新UserMusicKey
     * @param userMusicKey
     * @return
     */
    private UserMusicPo updateUserMusicKey(String userMusicKey, Integer uid, String role, HttpServletRequest request) throws CustomException {
        UserMusicKeyBean userMusicKeyBean = JSON.parseObject(userMusicKey, UserMusicKeyBean.class);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        // 判断是否达到播放上限
        if(userMusicKeyBean.getTime().equals(time) && userMusicKeyBean.getNum() >= MAX_MUSIC_PLAY) {
            throw new CustomException(ResultCode.NO_TIMES);
        }
        // 随机获取音频
        UserMusicPo currentMusic = null;
        do {
            List<UserMusicPo> randomMusicList = musicUserMapper.getRandomMusicList(role);
            for (UserMusicPo item: randomMusicList) {
                if(userMusicKeyBean.getMusicList().contains(item.getId())) continue;
                userMusicKeyBean.getMusicList().add(item.getId());
                currentMusic = item;
                break;
            }
        } while(Objects.isNull(currentMusic));
        // 如果记忆音频数超过最大限度，则释放list头部元素
        if(userMusicKeyBean.getMusicList().size() > MUSIC_MEMORY_NUM) {
            userMusicKeyBean.getMusicList().subList(0, 5).clear();
        }
        // 更新time与num
        if(userMusicKeyBean.getTime().equals(time)) {
            userMusicKeyBean.setNum(userMusicKeyBean.getNum() + 1);
        } else {
            userMusicKeyBean.setTime(time);
            userMusicKeyBean.setNum(1);
        }
        // 更新数据库key
        if(!musicUserMapper.updateUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean))) {
            LogUtils.error(request, "UserMusicKey更新错误");
        }
        return currentMusic;
    }
}
