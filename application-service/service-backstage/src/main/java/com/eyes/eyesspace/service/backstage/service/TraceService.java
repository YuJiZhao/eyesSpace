package com.eyes.eyesspace.service.backstage.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.backstage.model.vo.AddSpaceVisitVo;

public interface TraceService {
    /**
     * 添加网站访问数据
     * @param addSpaceVisitVo
     */
    void addSpaceVisit(AddSpaceVisitVo addSpaceVisitVo) throws CustomException;
}
