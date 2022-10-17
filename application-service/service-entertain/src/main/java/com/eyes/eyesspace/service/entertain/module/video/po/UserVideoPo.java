package com.eyes.eyesspace.service.entertain.module.video.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserVideoPo {
    private Integer id;

    private String title;

    private String originalAuthor;

    private String pictureUrl;

    private String originalUrl;

    private String ownerComment;

    private String videoUrl;

    private Integer views;

    private Integer likes;

    private Integer comments;

    private Date createTime;
}
