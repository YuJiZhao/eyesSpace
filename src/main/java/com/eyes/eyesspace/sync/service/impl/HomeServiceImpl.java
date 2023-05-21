package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.persistent.mapper.HomeMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.HomeListPO;
import com.eyes.eyesspace.sync.model.dto.ShuoListDTO;
import com.eyes.eyesspace.sync.model.dto.VersionListDTO;
import com.eyes.eyesspace.sync.model.vo.HomeListVO;
import com.eyes.eyesspace.sync.model.vo.SiteDataVO;
import com.eyes.eyesspace.sync.service.BlogService;
import com.eyes.eyesspace.sync.service.HomeService;
import com.eyes.eyesspace.sync.service.ShuoService;
import com.eyes.eyesspace.sync.service.VersionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

  private final HomeMapper homeMapper;

  private final TrackMapper trackMapper;

  private final BlogService blogService;

  private final ShuoService shuoService;

  private final VersionService versionService;

  public HomeServiceImpl(HomeMapper homeMapper, TrackMapper trackMapper, BlogService blogService, ShuoService shuoService, VersionService versionService) {
    this.homeMapper = homeMapper;
    this.trackMapper = trackMapper;
    this.blogService = blogService;
    this.shuoService = shuoService;
    this.versionService = versionService;
  }

  @Override
  public PageBind<HomeListVO> getHomeList(Integer page, Integer pageSize) throws CustomException {
    String role = UserInfoHolder.getRole();
    List<HomeListVO> homeListBeanList = new ArrayList<>();

    // 获取home列表
    int status = AuthConfigConstant.ROLE_ADMIN.equals(role) ? StatusEnum.DELETE.getStatus() : StatusEnum.PUBLIC.getStatus();
    List<HomeListPO> homeList = homeMapper.getHomeList((page - 1) * pageSize, pageSize, status);

    // 整合相同类型内容
    Map<Integer, List<Integer>> homeMap = new HashMap<>();
    for (HomeListPO item: homeList) {
      if (homeMap.containsKey(item.getType())) {
        homeMap.get(item.getType()).add(item.getCid());
      } else {
        List<Integer> list = new ArrayList<>();
        list.add(item.getCid());
        homeMap.put(item.getType(), list);
      }
    }

    // 执行查询
    Map<Integer, BlogListDTO> blogMap = new HashMap<>();
    Map<Integer, ShuoListDTO> shuoMap = new HashMap<>();
    Map<Integer, VersionListDTO> versionMap = new HashMap<>();
    for (Map.Entry<Integer, List<Integer>> entry: homeMap.entrySet()) {
      if (HomeTypeEnum.BLOG.getType().equals(entry.getKey())) {  // 博客列表
        List<BlogListDTO> blogList = blogService.getBlogListByIds(entry.getValue());
        blogMap = blogList.stream().collect(Collectors.toMap(BlogListDTO::getId, o -> o, (front, behind) -> front));
      } else if (HomeTypeEnum.SHUOSHUO.getType().equals(entry.getKey())) {  // 说说列表
        List<ShuoListDTO> shuoList = shuoService.getShuoListByIds(entry.getValue());
        shuoMap = shuoList.stream().collect(Collectors.toMap(ShuoListDTO::getOriginalId, o -> o, (front, behind) -> front));
      } else if (HomeTypeEnum.VERSION.getType().equals(entry.getKey())) {
        List<VersionListDTO> versionList = versionService.getVersionListByIds(entry.getValue());
        versionMap = versionList.stream().collect(Collectors.toMap(VersionListDTO::getId, o -> o, (front, behind) -> front));
      } else {
        log.error("wrong content type: " + entry.getKey());
      }
    }

    // 组装数据
    for (HomeListPO item: homeList) {
      HomeListVO homeItem = new HomeListVO();
      if (HomeTypeEnum.BLOG.getType().equals(item.getType())) {  // 博客列表
        homeItem.setType(HomeTypeEnum.BLOG.getType());
        homeItem.setHomeList(blogMap.get(item.getCid()));
        homeListBeanList.add(homeItem);
      } else if (HomeTypeEnum.SHUOSHUO.getType().equals(item.getType())) {  // 说说列表
        homeItem.setType(HomeTypeEnum.SHUOSHUO.getType());
        homeItem.setHomeList(shuoMap.get(item.getCid()));
        homeListBeanList.add(homeItem);
      } else if (HomeTypeEnum.VERSION.getType().equals(item.getType())) {
        homeItem.setType(HomeTypeEnum.VERSION.getType());
        homeItem.setHomeList(versionMap.get(item.getCid()));
        homeListBeanList.add(homeItem);
      } else {
        log.error("wrong content type: " + item.getType());
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
