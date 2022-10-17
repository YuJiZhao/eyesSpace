package com.eyes.eyesspace.service.site.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.site.model.dto.ContextDto;

public interface ContextService {
    /**
     * 获取配置文案
     * @return ContextDto
     */
    ContextDto getContext() throws CustomException;
}
