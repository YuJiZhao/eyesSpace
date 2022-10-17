package com.eyes.eyesspace.service.site.convert;

import com.eyes.eyesspace.service.site.model.dto.ContextDto;
import com.eyes.eyesspace.service.site.model.dto.ContextPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContextConvert {
    ContextConvert INSTANCE = Mappers.getMapper(ContextConvert.class);

    ContextDto ContextPartDtoVo2Dto(ContextPartDto contextPartDto);
}
