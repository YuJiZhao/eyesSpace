package com.eyes.eyesspace.common.feign.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentReplyDto {
    private Integer start;

    private Integer target;
}
