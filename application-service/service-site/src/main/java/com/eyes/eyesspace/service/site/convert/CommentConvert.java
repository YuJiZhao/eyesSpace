package com.eyes.eyesspace.service.site.convert;

import com.eyes.eyesspace.service.site.model.po.CommentChildPo;
import com.eyes.eyesspace.service.site.model.po.CommentListPo;
import com.eyes.eyesspace.common.feign.module.dto.CommentChildDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    CommentListDto commentListPo2Dto(CommentListPo commentListPo);

    CommentChildDto commentChildPo2Dto(CommentChildPo commentChildPo);
}
