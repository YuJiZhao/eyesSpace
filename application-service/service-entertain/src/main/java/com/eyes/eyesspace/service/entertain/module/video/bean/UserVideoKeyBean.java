package com.eyes.eyesspace.service.entertain.module.video.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserVideoKeyBean {
    private List<Integer> videos;

    private String time;

    private Integer num;
}
