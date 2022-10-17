package com.eyes.eyesspace.service.entertain.convert;

import com.eyes.eyesspace.service.entertain.module.music.dto.UserMusicInfoDto;
import com.eyes.eyesspace.service.entertain.module.music.po.UserMusicPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicUserConvert {
    MusicUserConvert INSTANCE = Mappers.getMapper(MusicUserConvert.class);

    @Mapping(target = "id", ignore = true)
    UserMusicInfoDto userMusicPo2InfoDto(UserMusicPo userMusicPo);
}
