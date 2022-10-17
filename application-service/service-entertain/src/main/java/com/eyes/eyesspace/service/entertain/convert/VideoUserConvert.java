package com.eyes.eyesspace.service.entertain.convert;

import com.eyes.eyesspace.service.entertain.module.video.dto.UserVideoInfoDto;
import com.eyes.eyesspace.service.entertain.module.video.po.UserVideoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoUserConvert {
    VideoUserConvert INSTANCE = Mappers.getMapper(VideoUserConvert.class);

    @Mapping(target = "id", ignore = true)
    UserVideoInfoDto userVideoPo2InfoDto(UserVideoPo userVideoPo);
}
