package com.eyes.eyesspace.service.article.module.bo;

import lombok.Data;

import java.util.List;

@Data
public class BlogAddBo {
    private Integer id;

    private String title;

    private String content;

    private String summary;

    private Integer status;

    private Integer words;

    private List<String> labels;

    private Integer category;
}
