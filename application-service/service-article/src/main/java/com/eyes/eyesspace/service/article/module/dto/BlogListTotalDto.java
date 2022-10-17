package com.eyes.eyesspace.service.article.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.eyes.eyesspace.common.feign.module.dto.BlogListDto;

import java.util.List;

@Data
@AllArgsConstructor
public class BlogListTotalDto {
    private Integer total;

    private List<BlogListDto> data;
}
