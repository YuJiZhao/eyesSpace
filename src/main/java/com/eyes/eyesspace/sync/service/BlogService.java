package com.eyes.eyesspace.sync.service;

import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.persistent.dto.BlogCategoryDTO;
import com.eyes.eyesspace.persistent.dto.BlogInfoDTO;
import com.eyes.eyesspace.persistent.dto.BlogLabelDTO;
import com.eyes.eyesspace.sync.model.request.BlogAddRequest;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.BlogAddVO;
import com.eyes.eyesspace.sync.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    BlogAddVO addBlog(BlogAddRequest blogAddRequest) throws CustomException;

    FileUploadVO addBlogPic(MultipartFile multipartFile) throws CustomException;

    BlogListInfoVO getBlogListInfo() throws CustomException;

    PageBind<BlogListDTO> getBlogList(Integer page, Integer pageSize, String category, String label) throws CustomException;

    List<BlogListDTO> getBlogListByIds(List<Integer> ids);

    BlogInfoDTO getBlogInfo(Integer id) throws CustomException;

    List<BlogCategoryDTO> getBlogCategory() throws CustomException;

    List<BlogLabelDTO> getBlogLabel() throws CustomException;

    void doBlogComment(CommentAddRequest commentAddRequest) throws CustomException;

    List<CommentListVO> getBlogCommentList(Integer id, Integer page, Integer pageSize) throws CustomException;

    void delBlogComment(Integer id) throws CustomException;
}