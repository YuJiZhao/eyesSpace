package com.eyes.eyesspace.persistent.po;

import lombok.Data;

@Data
public class UserMusicPO {
    private Integer id;

    private String title;

    private String author;

    private String url;

    private String pic;

    private Integer views;

    private Integer likes;

    private Integer comments;
}
