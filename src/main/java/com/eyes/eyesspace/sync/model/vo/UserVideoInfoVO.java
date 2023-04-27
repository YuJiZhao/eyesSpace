package com.eyes.eyesspace.sync.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserVideoInfoVO {
    private String id;

    private String title;

    private String originalAuthor;

    private String pictureUrl;

    private String originalUrl;

    private String ownerComment;

    private String videoUrl;

    private Integer views;

    private Integer likes;

    private Integer comments;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Boolean isLike;
}
