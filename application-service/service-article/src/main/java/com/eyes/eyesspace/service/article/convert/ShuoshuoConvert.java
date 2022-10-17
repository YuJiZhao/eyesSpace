package com.eyes.eyesspace.service.article.convert;

import com.eyes.eyesspace.service.article.module.dto.ShuoshuoCommentAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShuoshuoConvert {
    ShuoshuoConvert INSTANCE = Mappers.getMapper(ShuoshuoConvert.class);

    @Mapping(source = "id", target = "id", ignore = true)
    ShuoshuoListDto shuoshuoListPo2Dto(ShuoshuoInfoDto shuoshuoListPo);

    @Mapping(source = "objectId", target = "objectId", ignore = true)
    CommentAddDto shuoshuo2Comment(ShuoshuoCommentAddDto shuoshuoCommentAddDto);
}
