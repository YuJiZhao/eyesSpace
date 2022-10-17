package com.eyes.eyesspace.common.feign.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.eyes.eyesspace.common.feign.module.bean.HomeListBean;

@Data
@AllArgsConstructor
public class HomeListDto {
    private Integer type;

    private HomeListBean homeList;
}
