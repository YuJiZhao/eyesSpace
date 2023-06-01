package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.persistent.dto.FriendChainApplyDTO;
import com.eyes.eyesspace.sync.model.request.FriendChainApplyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author eyesYeager
 * @date 2023/6/1 9:36
 */
@Mapper
public interface FriendConvert {
  FriendConvert INSTANCE = Mappers.getMapper(FriendConvert.class);

  FriendChainApplyDTO friendChainApplyRequest2DTO(FriendChainApplyRequest friendChainApplyRequest);
}
