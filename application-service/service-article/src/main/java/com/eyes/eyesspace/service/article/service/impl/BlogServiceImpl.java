package com.eyes.eyesspace.service.article.service.impl;

import com.eyes.eyesspace.common.feign.clients.SiteClients;
import com.eyes.eyesspace.common.service.service.FileService;
import com.eyes.eyesspace.common.tool.context.CommentContext;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.tool.context.HomeTypeContext;
import com.eyes.eyesspace.common.tool.context.StatusContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import com.eyes.eyesspace.common.service.utils.IdentityUtils;
import com.eyes.eyesspace.common.service.utils.WebUtils;
import com.eyes.eyesspace.service.article.convert.BlogConvert;
import com.eyes.eyesspace.service.article.mapper.BlogMapper;
import com.eyes.eyesspace.service.article.module.bo.BlogAddBo;
import com.eyes.eyesspace.service.article.module.dto.*;
import com.eyes.eyesspace.service.article.module.po.BlogDataPo;
import com.eyes.eyesspace.service.article.module.po.CommentDelInfoPo;
import com.eyes.eyesspace.service.article.module.vo.BlogAddVo;
import com.eyes.eyesspace.service.article.service.BlogService;
import com.eyes.eyesspace.service.article.module.dto.BlogInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;
    private final SiteClients siteClients;
    private final FileService fileService;

    private final static String ROLE_ADMIN = ConfigContext.getString("ROLE_ADMIN");
    private final static String ROLE_USER = ConfigContext.getString("ROLE_USER");
    private final static String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");
    private final static String ID_HEADER = ConfigContext.getString("ID_HEADER");
    private final static String BLOG_PATH = ConfigContext.getString("BLOG_PATH");

    public BlogServiceImpl(BlogMapper blogMapper, SiteClients siteClients, FileService fileService) {
        this.blogMapper = blogMapper;
        this.siteClients = siteClients;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public BlogAddDto addBlog(BlogAddVo blogAddVo) throws CustomException {
        BlogAddBo blogAddBo = BlogConvert.INSTANCE.BlogAddVo2Bo(blogAddVo);

        // 获得博客分类
        Integer categoryIndex = blogMapper.isExistCategory(blogAddVo.getCategory());
        if(Objects.nonNull(categoryIndex)) {
            blogAddBo.setCategory(categoryIndex);
        } else {
            BlogAddCategoryDto blogAddCategoryDto = new BlogAddCategoryDto();
            blogAddCategoryDto.setCategory(blogAddVo.getCategory());
            if(!blogMapper.addCategory(blogAddCategoryDto)) throw new CustomException("新增分类失败！");
            blogAddBo.setCategory(blogAddCategoryDto.getId());
        }

        // 插入博客
        if(!blogMapper.addBlog(blogAddBo)) throw new CustomException("新增博客失败！");

        // 插入标签
        List<String> labels = blogAddBo.getLabels();
        if(!labels.isEmpty()) {
            for(String label: labels) {
                Integer labelIndex = blogMapper.isExistLabel(label);
                if(Objects.nonNull(labelIndex)) {
                    if(!blogMapper.addBlogLabelId(blogAddBo.getId(), labelIndex)) {
                        throw new CustomException("博客插入标签 '" + label + "' 失败！");
                    }
                } else {
                    BlogAddLabelDto blogAddLabelDto = new BlogAddLabelDto();
                    blogAddLabelDto.setLabel(label);
                    Integer labelResult = blogMapper.addBlogLabel(blogAddLabelDto);
                    if(labelResult != 1) throw new CustomException("新增标签 '" + label + "' 失败！");
                    if(!blogMapper.addBlogLabelId(blogAddBo.getId(), blogAddLabelDto.getId())) {
                        throw new CustomException("博客插入标签 '" + label + "' 失败！");
                    }
                }
            }
        }

        // 插入home
        Boolean insertHomeResult = blogMapper.insertHome(HomeTypeContext.BLOG.getType(), blogAddBo.getId(), blogAddBo.getStatus());
        if (!insertHomeResult) throw new CustomException("插入home失败");

        return new BlogAddDto(blogAddBo.getId());
    }

    @Override
    public FileUploadDto addBlogPic(MultipartFile multipartFile) throws CustomException {
        return fileService.sUpload(multipartFile, BLOG_PATH);
    }

    @Override
    public BlogListInfoDto getBlogListInfo() throws CustomException {
        String role = IdentityUtils.getRequestRole();
        BlogDataPo blogDataPo = blogMapper.getBlogData(role);

        if (ROLE_ADMIN.equals(role)) {
            return new BlogListInfoDto(
                    blogMapper.getBlogListInfo(null),
                    blogMapper.getBlogListInfo(StatusContext.PUBLIC.getStatus()),
                    blogMapper.getBlogListInfo(StatusContext.PRIVATE.getStatus()),
                    blogMapper.getBlogListInfo(StatusContext.DELETE.getStatus()),
                    blogDataPo,
                    blogMapper.getBlogListWords(StatusContext.DELETE.getStatus())
            );
        } else {
            return new BlogListInfoDto(
                    blogMapper.getBlogListInfo(0),
                    blogDataPo,
                    blogMapper.getBlogListWords(null)
            );
        }
    }

    @Override
    public BlogListTotalDto getBlogList(Integer page, Integer pageSize, String category, String label) throws CustomException {
        String role = IdentityUtils.getRequestRole();

        List<BlogListDto> blogList;
        Integer status = ROLE_ADMIN.equals(role)
                       ? StatusContext.DELETE.getStatus()
                       : StatusContext.PUBLIC.getStatus();
        blogList = blogMapper.getBlogList((page - 1) * pageSize, pageSize, status, category, label);

        if (!ROLE_ADMIN.equals(role)) {
            blogList.forEach((v) -> v.setStatus(null));
        }

        return new BlogListTotalDto(blogMapper.getBlogListNum(status, category, label), blogList);
    }

    @Override
    public BlogInfoDto getBlogInfo(Integer id) throws CustomException {
        String role = IdentityUtils.getRequestRole();
        Integer uid = IdentityUtils.getRequestId();

        BlogInfoDto blogInfoDto = blogMapper.getBlogInfo(id);
        if (
            Objects.isNull(blogInfoDto) ||
            (!ROLE_ADMIN.equals(role) && blogInfoDto.getStatus() != 0)
        ) { throw new CustomException("暂无数据"); }

        if (!ROLE_ADMIN.equals(role)) blogInfoDto.setStatus(null);
        blogInfoDto.setLabels(blogMapper.getLabelsById(id));

        blogInfoDto.setExistLike(Objects.nonNull(uid) && Objects.nonNull(blogMapper.isUserLike(uid, id)));
        blogInfoDto.setExistCollect(Objects.nonNull(uid) && Objects.nonNull(blogMapper.isUserCollect(uid, id)));

        Boolean addViewResult = blogMapper.addView(id);
        if (!addViewResult) throw new CustomException("操作异常", "博客阅读量更新失败");

        return blogInfoDto;
    }

    @Override
    public List<BlogCategoryDto> getBlogCategory() throws CustomException {
        String role = IdentityUtils.getRequestRole();
        return ROLE_ADMIN.equals(role)
                ? blogMapper.getBlogCategory(StatusContext.DELETE.getStatus())
                : blogMapper.getBlogCategory(StatusContext.PUBLIC.getStatus());
    }

    @Override
    public List<BlogLabelDto> getBlogLabel() throws CustomException {
        String role = IdentityUtils.getRequestRole();
        return ROLE_ADMIN.equals(role)
                ? blogMapper.getBlogLabel(StatusContext.DELETE.getStatus())
                : blogMapper.getBlogLabel(StatusContext.PUBLIC.getStatus());
    }

    @Override
    @Transactional
    public void doBlogLike(Integer id) throws CustomException {
        Integer uid = checkDo(id);
        Integer resultId = blogMapper.isUserLike(uid, id);
        if (Objects.isNull(resultId)) {
            Boolean addResult = blogMapper.addUserLike(uid, id);
            if (!addResult) throw new CustomException("点赞操作失败");
        } else {
            Boolean delResult = blogMapper.delUserLike(uid, id);
            if (!delResult) throw new CustomException("取消点赞失败");
        }
        Boolean changeLikeNumResult = blogMapper.changeBlogLikeNum(id, Objects.isNull(resultId) ? 1 : -1);
        if (!changeLikeNumResult) throw new CustomException("点赞数据修改失败");
    }

    @Override
    public void doBlogCollect(Integer id) throws CustomException {
        Integer uid = checkDo(id);
        Integer resultId = blogMapper.isUserCollect(uid, id);
        if (Objects.isNull(resultId)) {
            Boolean addResult = blogMapper.addUserCollect(uid, id);
            if (!addResult) throw new CustomException("收藏操作失败");
        } else {
            Boolean delResult = blogMapper.delUserCollect(uid, id);
            if (!delResult) throw new CustomException("取消收藏失败");
        }
        Boolean changeCollectNumResult = blogMapper.changeBlogCollectNum(id, Objects.isNull(resultId) ? 1 : -1);
        if (!changeCollectNumResult) throw new CustomException("收藏数据修改失败");
    }

    @Override
    public BlogListDto getBlogSummaryInfo(Integer id) throws CustomException {
        BlogListDto blogListDto = blogMapper.getBlogSummaryInfo(id);
        if (Objects.isNull(blogListDto)) {
            throw new CustomException("暂无数据");
        }
        return blogListDto;
    }

    @Override
    @Transactional
    public void doBlogComment(CommentAddDto commentAddDto) throws CustomException {
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        Integer blogStatus = blogMapper.getBlogStatus(commentAddDto.getObjectId());
        if (
            Objects.isNull(blogStatus) ||
            (ROLE_USER.equals(role) && !blogStatus.equals(StatusContext.PUBLIC.getStatus())) ||
            (ROLE_ADMIN.equals(role) && blogStatus.equals(StatusContext.DELETE.getStatus()))
        ) { throw new CustomException("博客不存在"); }

        commentAddDto.setUid(uid);
        StandardResult<Void> publishCommentResult = siteClients.publishComment(commentAddDto, CommentContext.BLOG.getType());
        if (!ResultCode.SUCCESS.getCode().equals(publishCommentResult.getCode())) {
            throw new CustomException(publishCommentResult.getMsg());
        }

        if (!blogMapper.updateBlogComments(commentAddDto.getObjectId(), 1)) {
            throw new CustomException("评论数据更新失败");
        }
    }

    @Override
    public List<CommentListDto> getBlogCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
        String role = IdentityUtils.getRequestRole();
        Integer uid = IdentityUtils.getRequestId();

        Integer blogStatus = blogMapper.getBlogStatus(id);
        if (
            Objects.isNull(blogStatus) ||
            (ROLE_USER.equals(role) && !blogStatus.equals(StatusContext.PUBLIC.getStatus())) ||
            (ROLE_ADMIN.equals(role) && blogStatus.equals(StatusContext.DELETE.getStatus()))
        ) { throw new CustomException("博客不存在"); }

        StandardResult<List<CommentListDto>> commentListResult = siteClients.getCommentList(id, CommentContext.BLOG.getType(), uid, page, pageSize);
        if (!ResultCode.SUCCESS.getCode().equals(commentListResult.getCode())) {
            throw new CustomException(commentListResult.getMsg());
        }

        return commentListResult.getData();
    }

    @Override
    public void delBlogComment(Integer id) throws CustomException {
        String role = IdentityUtils.getRequestRoleNeed();
        Integer uid = IdentityUtils.getRequestIdNeed();

        // 可行性检查
        CommentDelInfoPo commentDelInfoPo = blogMapper.getBlogCommentInfo(id);
        if (
            Objects.isNull(commentDelInfoPo) ||
            !CommentContext.BLOG.getType().equals(commentDelInfoPo.getType()) ||
            (ROLE_USER.equals(role) && !StatusContext.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
            (ROLE_ADMIN.equals(role) && StatusContext.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
        ) { throw new CustomException("博客不存在"); }

        StandardResult<Void> delResult = siteClients.delComment(id, uid);
        if (!ResultCode.SUCCESS.getCode().equals(delResult.getCode())) {
            throw new CustomException(delResult.getMsg());
        }

        // 更新博客
        if (!blogMapper.updateBlogComments(commentDelInfoPo.getObjectId(), -1)) {
            throw new CustomException("评论数据更新失败");
        }
    }

    /**
     * 判断是否可以进行点赞或者收藏操作
     */
    private Integer checkDo(Integer id) throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role;
        if (
            Objects.isNull(request.getHeader(ID_HEADER)) ||
            Objects.isNull(role = request.getHeader(ROLE_HEADER))
        ) { throw new CustomException(ResultCode.GATEWAY_BYPASSED); }
        Integer uid = Integer.valueOf(request.getHeader(ID_HEADER));

        // 可行性检查
        Integer status = blogMapper.getBlogStatus(id);
        if (
            Objects.isNull(status) ||
            (ROLE_USER.equals(role) && !status.equals(StatusContext.PUBLIC.getStatus())) ||
            (ROLE_ADMIN.equals(role) && status.equals(StatusContext.DELETE.getStatus()))
        ) throw new CustomException("博客不存在");

        return uid;
    }
}