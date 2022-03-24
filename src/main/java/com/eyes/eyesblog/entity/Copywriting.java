package com.eyes.eyesblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "copywriting")
public class Copywriting {

    @TableId
    private String key;

    private String value;
}
