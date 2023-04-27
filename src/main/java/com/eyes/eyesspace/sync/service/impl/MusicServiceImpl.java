package com.eyes.eyesspace.sync.service.impl;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.file.FileUtils;
import com.eyes.eyesTools.utils.SecurityUtils;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.dto.MusicInfoDTO;
import com.eyes.eyesspace.persistent.dto.MusicListDTO;
import com.eyes.eyesspace.persistent.mapper.MusicMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.MusicDataPO;
import com.eyes.eyesspace.persistent.po.UserMusicPO;
import com.eyes.eyesspace.sync.common.result.ResultCode;
import com.eyes.eyesspace.sync.convert.MusicConvert;
import com.eyes.eyesspace.sync.model.bean.UserMusicKeyBean;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.MusicAddVO;
import com.eyes.eyesspace.sync.model.vo.MusicListInfoVO;
import com.eyes.eyesspace.sync.model.vo.UserMusicInfoVO;
import com.eyes.eyesspace.sync.model.request.MusicAddRequest;
import com.eyes.eyesspace.sync.service.MusicService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RefreshScope
@Slf4j
@Service
public class MusicServiceImpl implements MusicService {
    @Value("${path.folder.music}")
    private String musicPath;

    @Value("${path.folder.music-cover}")
    private String musicCoverPath;

    @Value("${business.media.music-max-play:5}")
    private Integer musicMaxPlayNum;

    @Value("${business.media.music-memory-num:20}")
    private Integer musicMemoryNum;

    private final MusicMapper musicMapper;

    private final TrackMapper trackMapper;

    public MusicServiceImpl(MusicMapper musicMapper, TrackMapper trackMapper) {
        this.musicMapper = musicMapper;
        this.trackMapper = trackMapper;
    }

    @Override
    public MusicAddVO addMusic(MusicAddRequest musicAddRequest) throws CustomException {
        if (!musicMapper.addMusic(musicAddRequest)) {
            throw new CustomException("视频添加失败！");
        }
        return new MusicAddVO(musicAddRequest.getId());
    }

    @Override
    public FileUploadVO addMusicCover(MultipartFile multipartFile) throws CustomException {
        String url = FileUtils.sUpload(multipartFile, musicCoverPath);
        if (!trackMapper.addFileLog(
            FileOperationLogConstant.MUSIC_COVER,
            UserInfoHolder.getUid(),
            FileMethodEnum.UPLOAD.getMethod(),
            url
        )) { log.error("Failed to record music cover upload log"); }
        return new FileUploadVO(url);
    }

    @Override
    public FileUploadVO addMusicFile(MultipartFile multipartFile) throws CustomException {
        String url = FileUtils.bUpload(multipartFile, musicPath);
        // 记录文件上传日志
        if (!trackMapper.addFileLog(
            FileOperationLogConstant.MUSIC,
            UserInfoHolder.getUid(),
            FileMethodEnum.UPLOAD.getMethod(),
            url
        )) { log.error("Failed to record music upload log"); }
        return new FileUploadVO(url);
    }

    @Override
    public List<MusicListDTO> getMusicList(Integer page, Integer pageSize) {
        return musicMapper.getMusicList((page - 1) * pageSize, pageSize);
    }

    @Override
    public MusicListInfoVO getMusicListInfo() {
        MusicDataPO musicDataPo = musicMapper.getMusicData();
        return new MusicListInfoVO(
                musicMapper.getMusicListInfo(null),
                musicMapper.getMusicListInfo(StatusEnum.PUBLIC.getStatus()),
                musicMapper.getMusicListInfo(StatusEnum.PRIVATE.getStatus()),
                musicMapper.getMusicListInfo(StatusEnum.DELETE.getStatus()),
                musicDataPo.getViewsNum(),
                musicDataPo.getLikesNum(),
                musicDataPo.getCommentsNum()
        );
    }

    @Override
    public MusicInfoDTO getMusicInfo(Integer id) {
        return musicMapper.getMusicInfo(id);
    }

    @Override
    public String getMusicLrc(String id) throws CustomException {
        try {
            Integer musicId = Integer.valueOf(SecurityUtils.symmetricDecrypt(id));
            return musicMapper.getMusicLrc(musicId);
        } catch (NumberFormatException e) {
            throw new CustomException("这就很神奇了，但是没有音频给你");
        } catch(Exception e) {
            throw new CustomException("歌曲不存在");
        }
    }

    @Override
    public UserMusicInfoVO getMusicInfoByUser() throws CustomException {
        String role = UserInfoHolder.getRole();
        Long uid = UserInfoHolder.getUid();

        // Key处理
        String userMusicKey = musicMapper.getUserMusicKey(uid);
        UserMusicPO userMusicPo;
        if(Objects.isNull(userMusicKey)) {
            userMusicPo = createUserMusicKey(uid, role);
        } else {
            userMusicPo = updateUserMusicKey(userMusicKey, uid, role);
        }

        // 构建Dto
        UserMusicInfoVO userMusicInfoVO = MusicConvert.INSTANCE.userMusicPo2InfoDto(userMusicPo);
        try {
            userMusicInfoVO.setIsLike(
                Objects.nonNull(musicMapper.existUserLike(uid, userMusicPo.getId()))
            );
            userMusicInfoVO.setId(SecurityUtils.symmetricEncrypt(userMusicPo.getId().toString()));
        } catch(Exception e) {
            throw new CustomException("生成数据失败");
        }

        // 更新播放量
        userMusicInfoVO.setViews(userMusicInfoVO.getViews() + 1);
        if(!musicMapper.updateViewsNum(userMusicPo.getId())) {
            log.error("歌曲播放量更新失败,id:{}", userMusicPo.getId());
        }

        return userMusicInfoVO;
    }

    @Override
    @Transactional
    public void doUserLike(String id) throws CustomException {
        Long uid = UserInfoHolder.getUid();

        // 解析歌曲id
        Integer musicId;
        try {
            musicId = Integer.valueOf(SecurityUtils.symmetricDecrypt(id));
        } catch (NumberFormatException e) {
            throw new CustomException("这就很神奇了，但是没有音频给你点赞");
        } catch(Exception e) {
            throw new CustomException("歌曲不存在");
        }

        // 检查是否已经点赞
        Integer existUserLikeResult = musicMapper.existUserLike(uid, musicId);
        if(Objects.nonNull(existUserLikeResult)) {  // 已经点赞则取消点赞
            if(!musicMapper.delUserLike(musicId, uid)) throw new CustomException("数据库异常，取消点赞失败");
            if(!musicMapper.updateLikesNum(musicId, -1)) throw new CustomException("数据库异常，取消点赞失败");
        } else {  // 未点赞则执行点赞
            if(!musicMapper.createUserLike(musicId, uid)) throw new CustomException("数据库异常，点赞失败");
            if(!musicMapper.updateLikesNum(musicId, 1)) throw new CustomException("数据库异常，点赞失败");
        }
    }

    /**
     * 创建UserMusicKey
     */
    private UserMusicPO createUserMusicKey(Long uid, String role) {
        UserMusicPO randomMusicInfo = musicMapper.getRandomMusicList(role).get(0);

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
        if(!musicMapper.insertUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean))) {
            log.error("UserMusicKey插入错误");
        }
        return randomMusicInfo;
    }

    /**
     * 更新UserMusicKey
     */
    private UserMusicPO updateUserMusicKey(String userMusicKey, Long uid, String role) throws CustomException {
        UserMusicKeyBean userMusicKeyBean = JSON.parseObject(userMusicKey, UserMusicKeyBean.class);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        // 判断是否达到播放上限
        if(userMusicKeyBean.getTime().equals(time) && userMusicKeyBean.getNum() >= musicMaxPlayNum) {
            throw new CustomException(ResultCode.NO_TIMES);
        }
        // 随机获取音频
        UserMusicPO currentMusic = null;
        do {
            List<UserMusicPO> randomMusicList = musicMapper.getRandomMusicList(role);
            for (UserMusicPO item: randomMusicList) {
                if(userMusicKeyBean.getMusicList().contains(item.getId())) continue;
                userMusicKeyBean.getMusicList().add(item.getId());
                currentMusic = item;
                break;
            }
        } while(Objects.isNull(currentMusic));
        // 如果记忆音频数超过最大限度，则释放list头部元素
        if(userMusicKeyBean.getMusicList().size() > musicMemoryNum) {
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
        if(!musicMapper.updateUserMusicKey(uid, JSON.toJSONString(userMusicKeyBean))) {
            log.error("UserMusicKey更新错误");
        }
        return currentMusic;
    }
}