package com.eyes.eyesspace.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicDataPO {
    private Integer viewsNum;

    private Integer likesNum;

    private Integer commentsNum;
}
