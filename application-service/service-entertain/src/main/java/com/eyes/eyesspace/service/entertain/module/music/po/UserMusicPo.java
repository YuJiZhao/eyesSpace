package com.eyes.eyesspace.service.entertain.module.music.po;

import lombok.Data;

@Data
public class UserMusicPo {
    private Integer id;

    private String title;

    private String author;

    private String url;

    private String pic;

    private Integer views;

    private Integer likes;

    private Integer comments;
}
