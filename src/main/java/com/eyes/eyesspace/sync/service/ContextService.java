package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.ContextAboutContentVO;
import com.eyes.eyesspace.sync.model.vo.ContextVO;

public interface ContextService {

  ContextVO getContext() throws CustomException;

  ContextAboutContentVO getAboutContent();
}
