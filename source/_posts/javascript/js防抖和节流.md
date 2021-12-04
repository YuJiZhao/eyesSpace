---
title: js防抖和节流
date: 2021-05-20 20:24:53
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/007.jpg
tags: 
  - 防抖节流
  - 效率
categories: javascript
---

防抖和节流都是限制函数的执行次数

防抖:
    概念：通过setTimeout方法，在一定时间间隔内，将多次触发变成一次触发
    实现原理：触发高频事件后n秒内函数只会执行一次，如果n秒内高频事件再次被触发，则重新计算时间。
节流：
    概念：减少一段时间的触发频率
    实现原理：高频事件触发，但在n秒内只会执行一次，所以节流会稀释函数的执行频率。

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>

    <!-- 防抖 -->
    <input type="text" id="input1">
    <br>
    <input type="text" id="input2">
    <script>
        // 介绍版
        const input1 = document.querySelector("#input1");
        let timer = null;
        input1.onkeyup = () => {
            if (timer) {
                clearTimeout(timer);
            }
            timer = setTimeout(() => {
                console.log(input1.value);
            }, 500)
        }

        // 封装版
        let debounce = (fn, delay = 300) => {
            let timer;
            return function () {
                if (timer) {
                    clearTimeout(timer);
                }
                timer = setTimeout(() => {
                    fn();
                }, delay)
            }
        }
        let ajax = () => {
            console.log('服务器响应');
        }
        const input2 = document.querySelector("#input2");
        input2.onkeyup = debounce(ajax, 500);
    </script>

    <!-- 节流 -->
    <style>
        #box1,#box2 {
            width: 100px;
            height: 100px;
        }
        #box1{background-color: royalblue;}
        #box2{background-color: red;}
    </style>
    <div id="box1" draggable="true"></div>
    <div id="box2" draggable="true"></div>
    <script>
        // 介绍版
        const box1 = document.querySelector('#box1');
        let time = null;
        box1.ondrag = (e) => {
            if (time) {
                return;
            }
            time = setTimeout(() => {
                console.log(e.offsetX, e.offsetY);  // 火狐不兼容
                time = null;
            }, 300)
        }

        // 封装版 
        let throttle = (fn,delay) => {
            let timer;
            return function(){
                if(timer){
                    return;
                }
                timer = setTimeout(() => {
                    fn();
                    timer = null;
                },delay)
            }
        }
        let response = () => {
            console.log('节流');
        } 
        const box2 = document.querySelector('#box2');
        box2.ondrag = throttle(throttle(response),300);
    </script>
</body>

</html>
```