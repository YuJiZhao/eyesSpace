package com.eyes.eyesspace.service.site.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.tool.context.StatusContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.site.convert.ContextConvert;
import com.eyes.eyesspace.service.site.mapper.ContextMapper;
import com.eyes.eyesspace.service.site.model.dto.ContextDto;
import com.eyes.eyesspace.service.site.model.dto.ContextPartDto;
import com.eyes.eyesspace.service.site.model.dto.FootprintDto;
import com.eyes.eyesspace.service.site.model.po.ContextPo;
import com.eyes.eyesspace.service.site.service.ContextService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class ContextServiceImpl implements ContextService {
    private final ContextMapper contextMapper;
    private static final String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");
    private static final String ROLE_ADMIN = ConfigContext.getString("ROLE_ADMIN");

    public ContextServiceImpl(ContextMapper contextMapper) {
        this.contextMapper = contextMapper;
    }

    @Override
    public ContextDto getContext() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = request.getHeader(ROLE_HEADER);

        // 获取配置信息数据
        List<ContextPo> contextPart = contextMapper.getContext();
        HashMap<String, Object> contextMap = new HashMap<>();
        for(ContextPo item: contextPart) {
            if(item.getName().equals("footprint")) {
                contextMap.put(item.getName(), JSONArray.parseArray(item.getValue(), FootprintDto.class));
                continue;
            }
            contextMap.put(item.getName(), item.getValue());
        }
        ContextPartDto contextPartDto = JSON.parseObject(JSON.toJSONString(contextMap), ContextPartDto.class);

        // 获取内容数据
        ContextDto contextDto = ContextConvert.INSTANCE.ContextPartDtoVo2Dto(contextPartDto);
        contextDto.setVisitNum(contextMapper.getVisitNum());
        contextDto.setVisitorNum(contextMapper.getVisitorNum());
        contextDto.setUserNum(contextMapper.getUserNum());
        Integer status = ROLE_ADMIN.equals(role) ? StatusContext.DELETE.getStatus() : StatusContext.PUBLIC.getStatus();
        contextDto.setBlogNum(contextMapper.getBlogNum(status));
        contextDto.setShuoNum(contextMapper.getShuoNum(status));

        return contextDto;
    }
}
