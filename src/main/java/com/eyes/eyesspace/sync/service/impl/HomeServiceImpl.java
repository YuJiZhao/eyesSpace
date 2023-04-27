package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.mapper.HomeMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.HomeListPO;
import com.eyes.eyesspace.sync.model.vo.HomeListVO;
import com.eyes.eyesspace.sync.model.vo.SiteDataVO;
import com.eyes.eyesspace.sync.service.BlogService;
import com.eyes.eyesspace.sync.service.HomeService;
import com.eyes.eyesspace.sync.service.ShuoService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

  private final HomeMapper homeMapper;

  private final TrackMapper trackMapper;

  private final BlogService blogService;

  private final ShuoService shuoService;

  public HomeServiceImpl(HomeMapper homeMapper, TrackMapper trackMapper, BlogService blogService, ShuoService shuoService) {
    this.homeMapper = homeMapper;
    this.trackMapper = trackMapper;
    this.blogService = blogService;
    this.shuoService = shuoService;
  }

  @Override
  public PageBind<HomeListVO> getHomeList(Integer page, Integer pageSize) throws CustomException {
    String role = UserInfoHolder.getRole();

    List<HomeListVO> homeListBeanList = new ArrayList<>();
    int status = AuthConfigConstant.ROLE_ADMIN.equals(role) ? StatusEnum.DELETE.getStatus()
        : StatusEnum.PUBLIC.getStatus();
    List<HomeListPO> homeList = homeMapper.getHomeList((page - 1) * pageSize, pageSize, status);

    for (HomeListPO item : homeList) {
      if (item.getType().equals(HomeTypeEnum.BLOG.getType())) {
        homeListBeanList.add(new HomeListVO(
            HomeTypeEnum.BLOG.getType(),
            blogService.getBlogSummaryInfo(item.getCid())
        ));
      }
      if (item.getType().equals(HomeTypeEnum.SHUOSHUO.getType())) {
        homeListBeanList.add(new HomeListVO(
            HomeTypeEnum.SHUOSHUO.getType(),
            shuoService.getShuoshuoInfo(item.getCid())
        ));
      }
    }

    PageBind<HomeListVO> result = new PageBind<>();
    result.setData(homeListBeanList);
    result.setPage(page);
    result.setTotal(homeMapper.getHomeListTotal(status));
    return result;
  }

  @Override
  public SiteDataVO getSiteData() {
    return new SiteDataVO(
        trackMapper.getVisitNumByTime(null, null),
        trackMapper.getVisitorNumByTime(null, null)
    );
  }
}
