package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.sync.model.bo.BlogAddBO;
import com.eyes.eyesspace.sync.model.request.BlogAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogConvert {
    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);

    @Mapping(source = "category", target = "category", ignore = true)
    BlogAddBO BlogAddVo2Bo(BlogAddRequest blogAddRequest);
}
