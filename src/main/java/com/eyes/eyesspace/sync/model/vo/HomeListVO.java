package com.eyes.eyesspace.sync.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.eyes.eyesspace.sync.model.bean.HomeListBean;

@Data
@AllArgsConstructor
public class HomeListVO {
    private Integer type;

    private HomeListBean homeList;
}
