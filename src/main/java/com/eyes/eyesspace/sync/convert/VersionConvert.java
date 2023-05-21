package com.eyes.eyesspace.sync.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author eyesYeager
 * @date 2023/5/20 20:23
 */
@Mapper
public interface VersionConvert {
  VersionConvert INSTANCE = Mappers.getMapper(VersionConvert.class);
}
