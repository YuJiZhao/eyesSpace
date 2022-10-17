package com.eyes.eyesspace.service.site.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.feign.module.dto.HomeListDto;

import java.util.List;

public interface HomeService {
    List<HomeListDto> getHomeList(Integer page, Integer pageSize) throws CustomException;
}
