package com.eyes.eyesspace.service.entertain.service.impl;

import com.eyes.eyesspace.common.service.service.FileService;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.service.utils.SecurityUtils;
import com.eyes.eyesspace.service.entertain.mapper.MusicMapper;
import com.eyes.eyesspace.service.entertain.module.music.Vo.MusicAddVo;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicAddDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListDto;
import com.eyes.eyesspace.service.entertain.module.music.dto.MusicListInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.po.MusicDataPo;
import com.eyes.eyesspace.service.entertain.service.MusicService;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    private final MusicMapper musicMapper;
    private final FileService fileService;
    private static final String MUSIC_PATH = ConfigContext.getString("MUSIC_PATH");
    private static final String MUSIC_COVER_PATH = ConfigContext.getString("MUSIC_COVER_PATH");

    public MusicServiceImpl(MusicMapper musicMapper, FileService fileService) {
        this.musicMapper = musicMapper;
        this.fileService = fileService;
    }

    @Override
    public MusicAddDto addMusic(MusicAddVo musicAddVo) throws CustomException {
        if (!musicMapper.addMusic(musicAddVo)) {
            throw new CustomException("视频添加失败！");
        }
        return new MusicAddDto(musicAddVo.getId());
    }

    @Override
    public FileUploadDto addMusicCover(MultipartFile multipartFile) throws CustomException {
        return fileService.sUpload(multipartFile, MUSIC_COVER_PATH);
    }

    @Override
    public FileUploadDto addMusicFile(MultipartFile multipartFile) throws CustomException {
        return fileService.bUpload(multipartFile, MUSIC_PATH);
    }

    @Override
    public List<MusicListDto> getMusicList(Integer page, Integer pageSize) {
        return musicMapper.getMusicList((page - 1) * pageSize, pageSize);
    }

    @Override
    public MusicListInfoDto getMusicListInfo() {
        MusicDataPo musicDataPo = musicMapper.getMusicData();
        return new MusicListInfoDto(
                musicMapper.getMusicListInfo(null),
                musicMapper.getMusicListInfo(0),
                musicMapper.getMusicListInfo(1),
                musicMapper.getMusicListInfo(2),
                musicDataPo.getViewsNum(),
                musicDataPo.getLikesNum(),
                musicDataPo.getCommentsNum()
        );
    }

    @Override
    public MusicInfoDto getMusicInfo(Integer id) {
        return musicMapper.getMusicInfo(id, "ROLE_admin");
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
}