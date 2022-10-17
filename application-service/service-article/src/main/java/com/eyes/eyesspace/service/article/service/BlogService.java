package com.eyes.eyesspace.service.article.service;

import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.service.article.module.dto.*;
import com.eyes.eyesspace.service.article.module.vo.BlogAddVo;
import com.eyes.eyesspace.service.article.module.dto.BlogInfoDto;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentAddDto;
import com.eyes.eyesspace.common.feign.module.dto.CommentListDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    BlogAddDto addBlog(BlogAddVo blogAddVo) throws CustomException;

    FileUploadDto addBlogPic(MultipartFile multipartFile) throws CustomException;

    BlogListInfoDto getBlogListInfo() throws CustomException;

    BlogListTotalDto getBlogList(Integer page, Integer pageSize, String category, String label) throws CustomException;

    BlogInfoDto getBlogInfo(Integer id) throws CustomException;

    List<BlogCategoryDto> getBlogCategory() throws CustomException;

    List<BlogLabelDto> getBlogLabel() throws CustomException;

    void doBlogLike(Integer id) throws CustomException;

    void doBlogCollect(Integer id) throws CustomException;

    BlogListDto getBlogSummaryInfo(Integer id) throws CustomException;

    void doBlogComment(CommentAddDto commentAddDto) throws CustomException;

    List<CommentListDto> getBlogCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

    void delBlogComment(Integer id) throws CustomException;
}