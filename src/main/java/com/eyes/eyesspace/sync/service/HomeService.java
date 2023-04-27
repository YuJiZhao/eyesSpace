package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.sync.model.vo.HomeListVO;
import com.eyes.eyesspace.sync.model.vo.SiteDataVO;

public interface HomeService {
  PageBind<HomeListVO> getHomeList(Integer page, Integer pageSize) throws CustomException;

  SiteDataVO getSiteData();
}
