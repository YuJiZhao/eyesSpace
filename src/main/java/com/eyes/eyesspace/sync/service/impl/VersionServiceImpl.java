package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.constant.VersionTypeConstant;
import com.eyes.eyesspace.persistent.mapper.VersionMapper;
import com.eyes.eyesspace.persistent.po.VersionInfoPO;
import com.eyes.eyesspace.persistent.po.VersionPicPO;
import com.eyes.eyesspace.sync.model.dto.VersionListDTO;
import com.eyes.eyesspace.sync.model.vo.VersionInfoVO;
import com.eyes.eyesspace.sync.model.vo.VersionListVO;
import com.eyes.eyesspace.sync.service.VersionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author eyesYeager
 * @date 2023/5/20 16:30
 */
@Service
public class VersionServiceImpl implements VersionService {

  private final VersionMapper versionMapper;

  public VersionServiceImpl(VersionMapper versionMapper) {
    this.versionMapper = versionMapper;
  }

  @Override
  public VersionInfoVO getVersionInfo() throws CustomException {
    VersionInfoVO result = new VersionInfoVO();

    // 组装版本信息
    List<VersionInfoPO> versionInfo = versionMapper.getVersionInfo();
    for (VersionInfoPO item: versionInfo) {
      switch (item.getType()) {
        case VersionTypeConstant.SITE:
          result.setSiteVersion(item.getVersion());
          break;
        case VersionTypeConstant.FRONTEND:
          result.setFrontendVersion(item.getVersion());
          break;
        case VersionTypeConstant.BACKEND:
          result.setBackendVersion(item.getVersion());
          break;
        default:
          throw new CustomException("unknown version type: " + item.getType());
      }
    }

    // 获取版本总数
    result.setVersionNum(versionMapper.getVersionNum());

    return result;
  }

  @Override
  public VersionListVO getVersionList(Integer page, Integer pageSize) {
    List<Integer> versionIdList = versionMapper.getVersionIdList((page - 1) * pageSize, pageSize);
    List<VersionListDTO> versionList = getVersionListByIds(versionIdList);
    // in查询后会按照id从小到大排序，所以结果需要反转一次
    Collections.reverse(versionList);
    return new VersionListVO(
        versionMapper.getVersionNum(),
        versionList
    );
  }

  @Override
  public List<VersionListDTO> getVersionListByIds(List<Integer> ids) {
    // 批量获取数据
    List<VersionListDTO> versionListDTOs = versionMapper.getVersionListByIds(ids);
    List<VersionPicPO> versionPicPOList = versionMapper.getBatchVersionPics(ids);

    // 组装数据
    Map<Integer, List<String>> versionPicMap = versionPicPOList.stream().collect(Collectors.groupingBy(
        VersionPicPO::getVersionId,
        Collectors.mapping(VersionPicPO::getUrl, Collectors.toList())));
    versionListDTOs.forEach(v -> v.setPicList(versionPicMap.getOrDefault(v.getId(), new ArrayList<>())));
    return versionListDTOs;
  }
}
