---
title: bilibili弹幕列表随视频播放滚动
date: 2021-09-18 10:51:09
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/97.png
tags:
  - 脚本
categories: 脚本
---

以下是一些写脚本时获得的数据记录，最后面是脚本代码。

抓包加上查资料拿到的弹幕数据接口：
+ `"https://api.bilibili.com/x/v1/dm/list.so?oid=" + cid` 返回数据类型为XML(旧接口)。
+ `https://api.bilibili.com/x/v2/dm/web/seg.so?oid=" + cid + "&type=1&segment_index=1` 返回二进制数据(新接口)

二进制数据处理比较麻烦，我就用了XML的接口。

cid是视频的id，也叫oid，不是AV号，获取方式有两种：
+ 在控制台输入cid获取当前视频的cid
+ 通过方法：`window.__INITIAL_STATE__.videoData.pages`

接口测试时获取的数据如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/0c98e0b2f37640ca8e490a3cb6253fbb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
弹幕的返回数据含义如下：

```
<d p="1611.18000,1,25,16777215,1631635549,0,ab0d3321,54811347826209280,10">来看八爷</d>
```
有9个参数，经过查询并且测试获得了部分参数的含义：
+ 参数1： `1611.18000` 弹幕出现的时间，以秒数为单位
+ 参数2： `1` 弹幕的模式，1-3 滚动弹幕，4 底端弹幕，5顶端弹幕，6 逆向弹幕，7 精准定位，8 高级弹幕
+ 参数3： `25` 字号 （12非常小，16特小，18小，25中，36大，45很大，64特别大）
+ 参数4： `16777215` 字体的颜色,这串数字是十进制表示
+ 参数5： `1631635549` unix时间戳
+ 参数6： `0` 弹幕池类型，0普通池，1字幕池，2特殊池 【目前特殊池为高级弹幕专用】
+ 参数7： `ab0d3321` 发送者的Hash，用于“屏蔽此弹幕的发送者”功能
+ 参数8： `54811347826209280` 弹幕在弹幕数据库中rowID 用于“历史弹幕”功能。
+ 参数9： `10` 未知，貌似是新增的，查阅的资料里都没有这个参数

这里只有第一个参数用得到，剩下的参数会在我的另一个修改视频弹幕的脚ben里用到。

最终效果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/a1f518a955c248bd9947aeeccccdaf3c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

但由于该代码效率不高，对于上千或上万弹幕的视频加载较慢，因此暂时没有封装成脚本上传，后续有时间的话我会优化逻辑上传的。

代码如下：
```js
// 允许跨域请求，防止浏览器拦截
var meta = document.createElement("meta");
meta.httpEquiv = "Access-Control-Allow-Origin";
meta.content = "*";
$("head")[0].appendChild(meta);

// 初始化若干变量
var page_loc = location.href;
var page_start = page_loc.indexOf("?p=");
var page_p;

// 获取视频cid
if (page_start == -1 || page_start == 1) page_p = 0;
else page_p = page_loc.slice(page_start + 3) - 1;
var page_cid = window.__INITIAL_STATE__.videoData.pages[page_p].cid;

// 发起请求，拿到弹幕数据
$.ajax({
    url: "https://api.bilibili.com/x/v1/dm/list.so?oid=" + page_cid,
    type: "GET",
    dataType: "XML",
    success: function (xml) {
        // 处理返回数据
        data_deal(xml.all);
    },
    error: function (err) {
        console.log(err);
        alert("弹幕列表滚动脚本失效，可尝试更新最新版本脚本解决问题！\n如果有一定编程基础可以打开控制台查看报错信息。");
    }
});

// 存储数据
function data_deal(data) {
    var data_list = new Array(); // 存信息
    var data_arr = new Array(); // 存内容
    var data_length = data.length;
    for (var i = 8; i < data_length; i++) {
        var data_array = data[i].attributes.p.value.split(",");
        $.each(data_array, function (i) {
            data_array[i] = parseFloat(data_array[i]); // 转换类型，便于排序
        })
        data_list.push(data_array);
        data_arr.push(data[i].innerHTML);
    }

    // 执行排序
    data_sort(data_list, 0, data_list.length - 1, data_arr);

    // 修改弹幕列表
    $(".bui-collapse-header").bind("click", function () {
        var clearInter = setInterval(function () {
            if ($(".player-auxiliary-danmaku-load-status").css("display") == "none") {
                list_change(data_list, data_arr);
                clearInterval(clearInter);
            }
        }, 100);
    });
}

// 数据排序(快排算法)
function data_sort(data_list, l, r, data_arr) {
    if (l < r) {
        var i = l,
            j = r,
            temp = data_list[l],
            t1, t2, t3;
        while (i < j) {
            while (i < j && data_list[j][0] >= temp[0]) j--;
            while (i < j && data_list[i][0] <= temp[0]) i++;
            if (i < j) {
                t1 = data_list[i];
                data_list[i] = data_list[j];
                data_list[j] = t1;
                t2 = data_arr[i];
                data_arr[i] = data_arr[j];
                data_arr[j] = t2;
            }
        }

        data_list[l] = data_list[i];
        data_list[i] = temp;
        t3 = data_arr[l];
        data_arr[l] = data_arr[i];
        data_arr[i] = t3;
        data_sort(data_list, l, i - 1, data_arr);
        data_sort(data_list, i + 1, r, data_arr);
    }
}

// 时间格式转换(分转秒)
function time_m_s(a) {
    if (a.length == 2) return a[0] * 60 + a[1];
    else return a[0] * 3600 + a[1] * 60 + a[2];
}

// 时间格式转换(秒转分)
function time_s_m(a) {
    a = Math.floor(a);
    var m = Math.floor(a / 60);
    var s = (a - Math.floor(a / 60) * 60);
    if (m < 0) m = "00";
    else if (m < 10) m = "0" + String(m);
    if (s < 10) s = "0" + String(s);
    return m + ":" + s;
}

// 发送日期格式转换
function data_s_d(a) {
    var date = new Date(a * 1000);
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = date.getDate();
    if (D < 10) D = "0" + String(D) + ' ';
    else D = D + ' ';
    var h = date.getHours();
    if (h < 10) h = "0" + String(h) + ":";
    else if (h == 0) h = "00" + ":";
    else h = h + ":";
    var m = date.getMinutes();
    if (m < 10) m = "0" + String(m);
    else if (m == 0) m = "00";
    return M + D + h + m;
}

// 获取当前视频播放秒数
function get_now_s() {
    var time_now = $('.bilibili-player-video-time-now')[0].innerHTML.split(":");
    $.each(time_now, function (i) {
        time_now[i] = parseInt(time_now[i]);
    })
    return time_m_s(time_now);
}

function list_change(data_list, data_arr) {
    // 修改页面控制
    $(".player-auxiliary-danmaku-btn-time").removeAttr("orderby");
    $(".player-auxiliary-danmaku-btn-danmaku").removeAttr("orderby");
    $(".player-auxiliary-danmaku-btn-date").removeAttr("orderby");
    $(".player-auxiliary-danmaku-function > .player-auxiliary-danmaku-btn-danmaku")[0].innerHTML = "滚动弹幕(共" + data_arr.length + "条)";

    // 弹幕排序
    list_sort(data_list, data_arr);
}

function list_sort(data_list, data_arr) {
    // 破坏初始列表结构
    $(".player-auxiliary-danmaku-wrap > div > ul").empty();
    $(".player-auxiliary-danmaku-wrap > .player-auxiliary-danmaku-contaner").removeClass("player-auxiliary-danmaku-contaner player-auxiliary-bscrollbar");
    $(".player-auxiliary-danmaku-wrap").off();
    $(".player-auxiliary-danmaku-wrap > div > ul").off();

    // 清除BFC
    var div = document.createElement("div");
    var nothing = document.createTextNode(".");
    div.appendChild(nothing);
    $(".player-auxiliary-danmaku-wrap > div > ul")[0].appendChild(div);
    $(div).addClass("BFC-nothing");
    $(".BFC-nothing").css("opacity", "0");

    // 构造有序列表
    $(data_arr).each(function (i, v) {
        var li = document.createElement("li");
        var span1 = document.createElement("span");
        var span2 = document.createElement("span");
        var span3 = document.createElement("span");
        var span4 = document.createElement("span");
        var text1 = document.createTextNode(time_s_m(data_list[i][0]));
        var text2 = document.createTextNode(v);
        var text3 = document.createTextNode(data_s_d(data_list[i][4]));
        var text4 = document.createTextNode(">");
        span1.appendChild(text1);
        span2.appendChild(text2);
        span3.appendChild(text3);
        span4.appendChild(text4);
        $(li).attr("dmno", i);
        $(span2).attr("title", v);
        $(li).addClass("danmaku-info-row");
        $(span1).addClass("danmaku-info-time");
        $(span2).addClass("danmaku-info-danmaku");
        $(span3).addClass("danmaku-info-date");
        $(span4).addClass("danmaku-info-animate");
        li.appendChild(span1);
        li.appendChild(span2);
        li.appendChild(span3);
        li.appendChild(span4);
        $(".player-auxiliary-danmaku-wrap > div > ul")[0].appendChild(li);
        $('.danmaku-info-animate').css({
            'position': 'absolute',
            'width': '15px',
            'height': '100%',
            'padding': '0',
            'left': '0px',
            'font-size': '15px',
            'color': 'rgb(1,185,245)',
            'transition': '1s ease-in-out'
        });
    });

    // 播放动画(监听视频播放进度)
    var isChange = $(".bilibili-player-video-time-now")[0].innerHTML;
    player_animate(data_list, get_now_s());
    setInterval(function () {
        if (isChange != $(".bilibili-player-video-time-now")[0].innerHTML) {
            player_animate(data_list);  // 动画播放
            isChange = $(".bilibili-player-video-time-now")[0].innerHTML;
        }
    }, 1000);
}

// 播放动画
function player_animate(data_list) {
    var time_now_s = get_now_s();
    var length = data_list.length;
    var begin = -1, end = -1;
    for (var i = 0; i < length; i++) {
        if (Math.floor(data_list[i][0]) < time_now_s) continue;
        if (Math.floor(data_list[i][0]) > time_now_s) break;
        if (begin != -1) end++;
        if (begin == -1) begin = end = i;
    }
    if (end == -1) return;
    var li_height = parseFloat($(".player-auxiliary-danmaku-wrap > div > ul > li").css("height").replace("px", ""));
    var li_width = parseFloat($(".player-auxiliary-danmaku-wrap > div > ul > li").css("width").replace("px", ""));
    for (var i = begin; i <= end; i++) {
        if (length > 15 && i > 11) $(".player-auxiliary-danmaku-wrap > div > ul > li:first").css("margin-top", "-" + (i - 11) * li_height + "px");  // 列表滚动
        $($('.player-auxiliary-danmaku-wrap > div > ul > li')[i]).find(".danmaku-info-animate").css("left", li_width);  // 播放动画
    }
}
```

如果对该类脚本感兴趣的话，欢迎来到我的github看看，我开源了一个script仓库，里面有很多我写的和正在写的脚本，大佬们有兴趣可以一起贡献代码，[仓库地址](https://github.com/YuJiZhao/script)