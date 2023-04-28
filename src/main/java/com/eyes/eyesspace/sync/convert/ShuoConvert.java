package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.persistent.dto.ShuoInfoDTO;
import com.eyes.eyesspace.sync.model.dto.ShuoListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.request.ShuoCommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.ShuoListVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShuoConvert {
    ShuoConvert INSTANCE = Mappers.getMapper(ShuoConvert.class);

    @Mapping(source = "id", target = "id", ignore = true)
    ShuoListVO shuoInfoDTO2ListVO(ShuoInfoDTO shuoInfoDTO);

    @Mapping(source = "id", target = "originalId")
    ShuoListDTO shuoInfoDTO2ListDTO(ShuoInfoDTO shuoInfoDTO);

    @Mapping(source = "objectId", target = "objectId", ignore = true)
    CommentAddRequest shuoshuo2Comment(ShuoCommentAddRequest shuoCommentAddRequest);
}
