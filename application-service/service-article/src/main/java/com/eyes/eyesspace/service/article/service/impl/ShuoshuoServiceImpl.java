package com.eyes.eyesspace.service.article.service.impl;

import com.eyes.eyesspace.common.feign.clients.SiteClients;
import com.eyes.eyesspace.common.service.service.FileService;
import com.eyes.eyesspace.common.tool.context.*;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.common.service.utils.SecurityUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.article.convert.ShuoshuoConvert;
import com.eyes.eyesspace.service.article.mapper.ShuoshuoMapper;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoCommentAddDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoInfoDto;
import com.eyes.eyesspace.service.article.module.dto.ShuoshuoListInfoDto;
import com.eyes.eyesspace.service.article.module.po.CommentDelInfoPo;
import com.eyes.eyesspace.service.article.module.po.ShuoshuoDataPo;
import com.eyes.eyesspace.service.article.service.ShuoshuoService;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import com.eyes.eyesspace.common.feign.module.dto.ShuoshuoListDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShuoshuoServiceImpl implements ShuoshuoService {
    private final ShuoshuoMapper shuoshuoMapper;
    private final SiteClients siteClients;
    private final FileService fileService;

    private final static String ROLE_ADMIN = ConfigContext.getString("ROLE_ADMIN");
    private final static String ROLE_USER = ConfigContext.getString("ROLE_USER");
    private final static String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");

    public ShuoshuoServiceImpl(ShuoshuoMapper shuoshuoMapper, SiteClients siteClients, FileService fileService) {
        this.shuoshuoMapper = shuoshuoMapper;
        this.siteClients = siteClients;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public void addShuoshuo(ShuoshuoAddDto shuoshuoAddDto) throws CustomException {
        if (!shuoshuoMapper.addShuoshuo(shuoshuoAddDto)) {
            throw new CustomException("插入说说失败！");
        }

        if(!shuoshuoAddDto.getPicList().isEmpty()) {
            Integer insertShuoshuoPics = shuoshuoMapper.addShuoshuoPics(shuoshuoAddDto.getPicList(), shuoshuoAddDto.getId());
            if (insertShuoshuoPics != shuoshuoAddDto.getPicList().size()) throw new CustomException("插入说说图片失败！");
        }

        Boolean insertHomeResult = shuoshuoMapper.insertHome(HomeTypeContext.SHUOSHUO.getType(), shuoshuoAddDto.getId(), shuoshuoAddDto.getStatus());
        if (!insertHomeResult) throw new CustomException("插入home失败！");
    }

    @Override
    public ShuoshuoListInfoDto getShuoshuoListInfo() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = request.getHeader(ROLE_HEADER);

        ShuoshuoDataPo shuoshuoDataPo = shuoshuoMapper.getShuoshuoData(role);
        if (Objects.isNull(shuoshuoDataPo)) {
            return new ShuoshuoListInfoDto();
        }
        if (ROLE_ADMIN.equals(role)) {
            return new ShuoshuoListInfoDto(
                shuoshuoMapper.getShuoshuoListInfo(null),
                shuoshuoMapper.getShuoshuoListInfo(0),
                shuoshuoMapper.getShuoshuoListInfo(1),
                shuoshuoMapper.getShuoshuoListInfo(2),
                shuoshuoDataPo.getViewsNum(),
                shuoshuoDataPo.getCommentsNum()
            );
        } else {
            return new ShuoshuoListInfoDto(
                shuoshuoMapper.getShuoshuoListInfo(0),
                shuoshuoDataPo.getViewsNum(),
                shuoshuoDataPo.getCommentsNum()
            );
        }
    }

    @Override
    @Transactional
    public List<ShuoshuoListDto> getShuoshuoList(Integer page, Integer pageSize) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = request.getHeader(ROLE_HEADER);

        List<ShuoshuoInfoDto> shuoshuoInfoDtos = shuoshuoMapper.getShuoshuoList((page - 1) * pageSize, pageSize, role);
        List<ShuoshuoListDto> shuoshuoListDtos = new ArrayList<>();
        try {
            for (ShuoshuoInfoDto item: shuoshuoInfoDtos) {
                item.setPicList(shuoshuoMapper.getShuoshuoPics(item.getId()));
                ShuoshuoListDto shuoshuoListDto = ShuoshuoConvert.INSTANCE.shuoshuoListPo2Dto(item);
                shuoshuoListDto.setId(SecurityUtils.symmetricEncrypt(item.getId().toString()));
                shuoshuoListDtos.add(shuoshuoListDto);

                Boolean addViewResult = shuoshuoMapper.addView(item.getId());
                if (!addViewResult) throw new CustomException("操作异常", "博客阅读量更新失败");
            }
        } catch (Exception e) {
            throw new CustomException("程序异常");
        }
        return shuoshuoListDtos;
    }

    @Override
    public ShuoshuoListDto getShuoshuoInfo(Integer id) throws CustomException {
        ShuoshuoInfoDto shuoshuoInfoDto = shuoshuoMapper.getShuoshuoInfo(id);
        if (Objects.isNull(shuoshuoInfoDto)) throw new CustomException("说说不存在");
        shuoshuoInfoDto.setPicList(shuoshuoMapper.getShuoshuoPics(id));
        ShuoshuoListDto shuoshuoListDto = ShuoshuoConvert.INSTANCE.shuoshuoListPo2Dto(shuoshuoInfoDto);
        try {
            shuoshuoListDto.setId(SecurityUtils.symmetricEncrypt(shuoshuoInfoDto.getId().toString()));
        } catch (Exception e) {
            throw new CustomException("程序异常");
        }

        Boolean addViewResult = shuoshuoMapper.addView(id);
        if (!addViewResult) throw new CustomException("操作异常", "博客阅读量更新失败");

        return shuoshuoListDto;
    }

    @Override
    public ShuoshuoListDto getShuoshuoInfoByString(String id) throws CustomException {
        try {
            String originalId = SecurityUtils.symmetricDecrypt(id);
            return getShuoshuoInfo(Integer.valueOf(originalId));
        } catch (Exception e) {
            throw new CustomException("程序错误");
        }
    }

    @Override
    @Transactional
    public void doShuoComment(ShuoshuoCommentAddDto shuoshuoCommentAddDto) throws CustomException {
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        CommentAddDto commentAddDto = ShuoshuoConvert.INSTANCE.shuoshuo2Comment(shuoshuoCommentAddDto);
        try {
            String sid = SecurityUtils.symmetricDecrypt(shuoshuoCommentAddDto.getObjectId());
            commentAddDto.setObjectId(Integer.valueOf(sid));
        } catch (Exception e) {
            throw new CustomException("程序错误");
        }

        Integer shuoshuoStatus = shuoshuoMapper.getShuoshuoStatus(commentAddDto.getObjectId());
        if (
            Objects.isNull(shuoshuoStatus) ||
            (ROLE_USER.equals(role) && !shuoshuoStatus.equals(StatusContext.PUBLIC.getStatus())) ||
            (ROLE_ADMIN.equals(role) && shuoshuoStatus.equals(StatusContext.DELETE.getStatus()))
        ) { throw new CustomException("说说不存在"); }

        commentAddDto.setUid(uid);
        StandardResult<Void> publishCommentResult = siteClients.publishComment(commentAddDto, CommentContext.SHUOSHUO.getType());
        if (!ResultCode.SUCCESS.getCode().equals(publishCommentResult.getCode())) {
            throw new CustomException(publishCommentResult.getMsg());
        }

        if (!shuoshuoMapper.updateShuoshuoComments(commentAddDto.getObjectId(), 1)) {
            throw new CustomException("评论数据更新失败");
        }
    }

    @Override
    public List<CommentListDto> getShuoCommentList(String id, Integer page, Integer pageSize) throws CustomException {
        String role = IdentityUtils.getRequestRole();
        Integer uid = IdentityUtils.getRequestId();

        Integer originalId;
        try {
            String s = SecurityUtils.symmetricDecrypt(id);
            originalId = Integer.valueOf(s);
        } catch(Exception e) {
            throw new CustomException("程序错误");
        }

        Integer shuoshuoStatus = shuoshuoMapper.getShuoshuoStatus(originalId);
        if (
            Objects.isNull(shuoshuoStatus) ||
            (ROLE_USER.equals(role) && !shuoshuoStatus.equals(StatusContext.PUBLIC.getStatus())) ||
            (ROLE_ADMIN.equals(role) && shuoshuoStatus.equals(StatusContext.DELETE.getStatus()))
        ) { throw new CustomException("说说不存在"); }

        StandardResult<List<CommentListDto>> commentListResult = siteClients.getCommentList(originalId, CommentContext.SHUOSHUO.getType(), uid, page, pageSize);
        if (!ResultCode.SUCCESS.getCode().equals(commentListResult.getCode())) {
            throw new CustomException(commentListResult.getMsg());
        }

        return commentListResult.getData();
    }

    @Override
    public void delShuoComment(Integer id) throws CustomException {
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        // 可行性检查
        CommentDelInfoPo commentDelInfoPo = shuoshuoMapper.getShuoshuoCommentInfo(id);
        if (
            Objects.isNull(commentDelInfoPo) ||
            !CommentContext.SHUOSHUO.getType().equals(commentDelInfoPo.getType()) ||
            (ROLE_USER.equals(role) && !StatusContext.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
            (ROLE_ADMIN.equals(role) && StatusContext.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
        ) { throw new CustomException("说说不存在"); }

        StandardResult<Void> delResult = siteClients.delComment(id, uid);
        if (!ResultCode.SUCCESS.getCode().equals(delResult.getCode())) {
            throw new CustomException(delResult.getMsg());
        }

        // 更新说说
        if (!shuoshuoMapper.updateShuoshuoComments(commentDelInfoPo.getObjectId(), -1)) {
            throw new CustomException("评论数据更新失败");
        }
    }

    @Override
    public FileUploadDto uploadShuoshuoPic(MultipartFile multipartFile) throws CustomException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (Objects.isNull(originalFilename)) {
            throw new CustomException("文件错误");
        }
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!MediaContext.image.contains(fileType)) {
            throw new CustomException("图片格式不支持");
        }

        return fileService.sUpload(multipartFile, "shuoshuo");
    }
}
