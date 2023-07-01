package com.eyes.eyesspace.async.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentNoticeModel implements Serializable {
    private String subject;

    private String url;

    private String content;
}
