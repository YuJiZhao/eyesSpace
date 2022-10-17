package com.eyes.eyesspace.service.backstage.service.impl;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.utils.BrowserUtils;
import com.eyes.eyesspace.common.tool.utils.IpUtils;
import com.eyes.eyesspace.common.tool.utils.OSUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.backstage.mapper.TraceMapper;
import com.eyes.eyesspace.service.backstage.model.vo.AddSpaceVisitVo;
import com.eyes.eyesspace.service.backstage.service.TraceService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TraceServiceImpl implements TraceService {
    private final TraceMapper traceMapper;
    private static final String ID_HEADER = ConfigContext.getString("ID_HEADER");

    public TraceServiceImpl(TraceMapper traceMapper) {
        this.traceMapper = traceMapper;
    }

    @Override
    public void addSpaceVisit(AddSpaceVisitVo addSpaceVisitVo) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        Boolean addSpaceVisitResult = traceMapper.addSpaceVisit(
            request.getHeader(ID_HEADER),
            IpUtils.getIpAddr(request),
            OSUtils.osName(request),
            IpUtils.getHostName(),
            BrowserUtils.browserName(request),
            addSpaceVisitVo.getPath()
        );
        if (!addSpaceVisitResult) throw new CustomException("error", "网站访问数据添加异常");
    }
}
