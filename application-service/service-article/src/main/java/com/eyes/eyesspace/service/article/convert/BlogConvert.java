package com.eyes.eyesspace.service.article.convert;

import com.eyes.eyesspace.service.article.module.bo.BlogAddBo;
import com.eyes.eyesspace.service.article.module.vo.BlogAddVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogConvert {
    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);

    @Mapping(source = "category", target = "category", ignore = true)
    BlogAddBo BlogAddVo2Bo(BlogAddVo blogAddVo);
}
