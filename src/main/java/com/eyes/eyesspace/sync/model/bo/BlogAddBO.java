package com.eyes.eyesspace.sync.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class BlogAddBO {
    private Integer id;

    private String title;

    private String content;

    private String summary;

    private Integer words;

    private Integer status;

    private List<String> labels;

    private Integer category;
}
