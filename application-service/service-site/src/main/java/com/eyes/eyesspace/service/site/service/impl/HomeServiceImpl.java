package com.eyes.eyesspace.service.site.service.impl;

import com.eyes.eyesspace.common.feign.clients.ArticleClients;
import com.eyes.eyesspace.common.tool.context.HomeTypeContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.service.site.mapper.HomeMapper;
import com.eyes.eyesspace.service.site.model.po.HomeListPo;
import com.eyes.eyesspace.service.site.service.HomeService;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;
import com.eyes.eyesspace.common.feign.module.dto.HomeListDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    private final ArticleClients articleClients;
    private final HomeMapper homeMapper;

    public HomeServiceImpl(ArticleClients articleClients, HomeMapper homeMapper) {
        this.articleClients = articleClients;
        this.homeMapper = homeMapper;
    }

    @Override
    public List<HomeListDto> getHomeList(Integer page, Integer pageSize) throws CustomException {
        List<HomeListDto> homeListBeanList = new ArrayList<>();
        List<HomeListPo> homeList = homeMapper.getHomeList((page - 1) * pageSize, pageSize);

        for (HomeListPo item: homeList) {
            if (item.getType().equals(HomeTypeContext.BLOG.getType())) {
                StandardResult<BlogListDto> blogInfo = articleClients.getBlogSummaryInfo(item.getCid());
                if (ResultCode.SUCCESS.getCode().equals(blogInfo.getCode())) {
                    homeListBeanList.add(new HomeListDto(HomeTypeContext.BLOG.getType(), blogInfo.getData()));
                } else {
                    throw new CustomException(blogInfo.getMsg());
                }
            }
            if (item.getType().equals(HomeTypeContext.SHUOSHUO.getType())) {
                StandardResult<ShuoshuoListDto> shuoshuoInfo = articleClients.getShuoshuoInfo(item.getCid());
                if (ResultCode.SUCCESS.getCode().equals(shuoshuoInfo.getCode())) {
                    homeListBeanList.add(new HomeListDto(HomeTypeContext.SHUOSHUO.getType(), shuoshuoInfo.getData()));
                } else {
                    throw new CustomException(shuoshuoInfo.getMsg());
                }
            }
        }
        return homeListBeanList;
    }
}
