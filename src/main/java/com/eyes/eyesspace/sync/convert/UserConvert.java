package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesspace.sync.model.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author eyesYeager
 * @date 2023/2/10 15:26
 */
@Mapper
public interface UserConvert {
  UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

  UserInfoVO returnee2Dto(UserInfoReturnee userInfoReturnee);
}
