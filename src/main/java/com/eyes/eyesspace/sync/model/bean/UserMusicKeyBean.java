package com.eyes.eyesspace.sync.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserMusicKeyBean {
    private List<Integer> musicList;

    private String time;

    private Integer num;
}
