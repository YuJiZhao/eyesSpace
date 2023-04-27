package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.persistent.po.UserVideoPO;
import com.eyes.eyesspace.sync.model.vo.UserVideoInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoConvert {
    VideoConvert INSTANCE = Mappers.getMapper(VideoConvert.class);

    @Mapping(target = "id", ignore = true)
    UserVideoInfoVO userVideoPo2InfoDto(UserVideoPO userVideoPo);
}
