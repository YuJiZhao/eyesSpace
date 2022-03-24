package com.eyes.eyesblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName(value = "blog")
public class Blog {

    @TableId
    private Integer id;

    private Integer status;

    private String title;

    private String cover;

    private String archive;

    private Integer word_num;

    private Date create_time;

    @TableLogic
    private Date update_time;

    private Integer priority;

    private String summary;

    private String content;
}
