package com.eyes.eyesspace.service.site.service.impl;

import com.eyes.eyesspace.common.feign.clients.SiteClients;
import com.eyes.eyesspace.common.tool.context.CommentContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.service.site.service.AboutService;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutServiceImpl implements AboutService {
    private final SiteClients siteClients;

    public AboutServiceImpl(SiteClients siteClients) {
        this.siteClients = siteClients;
    }

    @Override
    public void doAboutComment(CommentAddDto commentAddDto) throws CustomException {
        Integer uid = IdentityUtils.getRequestIdNeed();
        if (!commentAddDto.getObjectId().equals(0)) throw new CustomException("你以为");
        commentAddDto.setUid(uid);
        StandardResult<Void> publishCommentResult = siteClients.publishComment(commentAddDto, CommentContext.SITE.getType());
        if (!ResultCode.SUCCESS.getCode().equals(publishCommentResult.getCode())) {
            throw new CustomException(publishCommentResult.getMsg());
        }
    }

    @Override
    public List<CommentListDto> getAboutCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
        Integer uid = IdentityUtils.getRequestId();
        if (!id.equals(0)) {
            throw new CustomException("你以为");
        }

        StandardResult<List<CommentListDto>> commentListResult = siteClients.getCommentList(id, CommentContext.SITE.getType(), uid, page, pageSize);
        if (!ResultCode.SUCCESS.getCode().equals(commentListResult.getCode())) {
            throw new CustomException(commentListResult.getMsg());
        }
        return commentListResult.getData();
    }

    @Override
    public void delAboutComment(Integer id) throws CustomException {
        Integer uid = IdentityUtils.getRequestIdNeed();

        StandardResult<Void> delResult = siteClients.delComment(id, uid);
        if (!ResultCode.SUCCESS.getCode().equals(delResult.getCode())) {
            throw new CustomException(delResult.getMsg());
        }
    }
}
