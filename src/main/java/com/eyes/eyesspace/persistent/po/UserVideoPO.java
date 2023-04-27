package com.eyes.eyesspace.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserVideoPO {
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
