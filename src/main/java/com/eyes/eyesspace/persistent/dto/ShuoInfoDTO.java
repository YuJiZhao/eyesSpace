package com.eyes.eyesspace.persistent.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShuoInfoDTO {
    private Integer id;

    private String content;

    private List<String> picList;

    private Integer views;

    private Integer comments;

    private Integer status;

    private String createTime;
}