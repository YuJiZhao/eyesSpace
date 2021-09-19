---
title: 页面自动滚动脚本
date: 2021-05-02 14:53:37
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/7.jpg
tags:
  - 脚本
categories: 脚本
---

看小说的时候总是不想用鼠标滑来滑去，就想了个办法让页面滚动起来，大家可以在油猴脚本下载这个脚本——[页面自动滚动脚本](https://greasyfork.org/zh-CN/users/763055-yujizhao)

下面是它的使用说明：
+ 按 ALT + 上 则向上滚动
+ 按 ALT + 下 则向下滚动
+ 多次按或长按ALT + 上/下 时速度可叠加
+ 同时按CTRL + ALT键可停止自动滚动
+ 配合浏览器自带的两个快捷键： CTRL + 上： 回到顶部。 CTRL + 下： 回到底部
+ 单击页面可停止自动滚动
+ 滚动时滑动鼠标滚轮则自动滚动停止

下面是它的源代码：
```javascript


    // ==UserScript==
    // @name         autoScroll
    // @namespace    eyes
    // @version      0.2
    // @description  It allows the page to scroll on its own
    // @author       eyes
    // @match        *://*/*
    // @grant        none
    // ==/UserScript==
     
    (function() {
        'use strict';
        var speed = 0;
        // 获取滑动位置
        function getScrollTop() {
            var scrollTop = 0,
                bodyScrollTop = 0,
                documentScrollTop = 0;
            if (document.body) {
                bodyScrollTop = document.body.scrollTop;
            }
            if (document.documentElement) {
                documentScrollTop = document.documentElement.scrollTop;
            }
            scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
            return scrollTop;
        }

        //浏览器视口的高度
        function getWindowHeight() {
            var windowHeight = 0;
            if (document.compatMode == 'CSS1Compat') {
                windowHeight = document.documentElement.clientHeight;
            } else {
                windowHeight = document.body.clientHeight;
            }
            return windowHeight;
        }

        //文档的总高度
        function getScrollHeight() {
            var scrollHeight = 0,
                bodyScrollHeight = 0,
                documentScrollHeight = 0;
            if (document.body) {
                bodyScrollHeight = document.body.scrollHeight;
            }
            if (document.documentElement) {
                documentScrollHeight = document.documentElement.scrollHeight;
            }
            scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
            return scrollHeight;
        }

        // 滚动事件
        setInterval(() => {
            // 判断页面是否滑到底部
            let bottomFlag = (getScrollTop() + getWindowHeight() == getScrollHeight()) ? true : false;
            let topFlag = (getScrollTop() == 0) ? true : false;
            if (bottomFlag || topFlag) {
                speed = 0;
            } else {
                document.documentElement.scrollTop += speed;
            }
        }, 5)

        // 判断是否需要滚动
        document.onkeydown = (e) => {
            e = event || window.event;
            if (e && e.keyCode == 38 && e.altKey) { // 上键
                let bottomFlag = (getScrollTop() + getWindowHeight() == getScrollHeight()) ? true : false;
                if (bottomFlag) {
                    document.documentElement.scrollTop += -1;
                }
                speed -= 1.5;
            }
            if (e && e.keyCode == 40 && e.altKey) { // 下键
                let topFlag = (getScrollTop() == 0) ? true : false;
                if (topFlag) {
                    document.documentElement.scrollTop += 1;
                }
                speed += 1.5;
            }
        }

        // 单击页面停止滚动
        document.onclick = () => {
            speed = 0;
        }

        // 滑动滚轮页面停止滚动
        document.onmousewheel = () => {
            speed = 0;
        }
        document.addEventListener("DOMMouseScroll", () => {
            speed = 0;
        })
    })();
```

如果对该类脚本感兴趣的话，欢迎来到我的github看看，我开源了一个script仓库，里面有很多我写的和正在写的脚本，大佬们有兴趣可以一起贡献代码，[仓库地址](https://github.com/YuJiZhao/script)