package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.persistent.dto.JokeAddDTO;
import com.eyes.eyesspace.persistent.po.JokeListPO;
import com.eyes.eyesspace.sync.model.dto.JokeListDTO;
import com.eyes.eyesspace.sync.model.request.JokeAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author eyesYeager
 * @date 2023/9/25 9:48
 */
@Mapper
public interface JokeConvert {
  JokeConvert INSTANCE = Mappers.getMapper(JokeConvert.class);

  @Mapping(source = "urlList", target = "urlList", ignore = true)
  JokeAddDTO jokeAddRequest2DTO(JokeAddRequest jokeAddRequest);

  @Mapping(source = "urlList", target = "urlList", ignore = true)
  JokeListDTO jokeListPO2DTO(JokeListPO jokeListPO);
}
