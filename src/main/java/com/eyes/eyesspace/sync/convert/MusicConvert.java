package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.sync.model.vo.UserMusicInfoVO;
import com.eyes.eyesspace.persistent.po.UserMusicPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicConvert {
    MusicConvert INSTANCE = Mappers.getMapper(MusicConvert.class);

    @Mapping(target = "id", ignore = true)
    UserMusicInfoVO userMusicPo2InfoDto(UserMusicPO userMusicPo);
}
