package com.eyes.eyesspace.sync.convert;

import com.eyes.eyesspace.persistent.po.CommentChildPO;
import com.eyes.eyesspace.persistent.po.CommentListPO;
import com.eyes.eyesspace.sync.model.dto.CommentChildDTO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    CommentListVO commentListPo2Dto(CommentListPO commentListPo);

    CommentChildDTO commentChildPo2Dto(CommentChildPO commentChildPo);

}
