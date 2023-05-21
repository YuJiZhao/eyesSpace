package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.dto.VersionListDTO;
import com.eyes.eyesspace.sync.model.vo.VersionInfoVO;
import com.eyes.eyesspace.sync.model.vo.VersionListVO;
import java.util.List;

/**
 * @author eyesYeager
 * @date 2023/5/20 16:30
 */

public interface VersionService {

  VersionInfoVO getVersionInfo() throws CustomException;

  VersionListVO getVersionList(Integer page, Integer pageSize);

  List<VersionListDTO> getVersionListByIds(List<Integer> ids);
}
