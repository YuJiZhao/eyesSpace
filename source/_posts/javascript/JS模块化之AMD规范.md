---
title: JS模块化之AMD规范
date:  2021-05-29 17:26:11
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/65.jpg
tags: 
  - 模块化
  - AMD规范
categories: javascript
---

  ## AMD概述
    
    AMD说明：
        AMD：Asynchronous Module Definition(异步模块定义)
        专门用于浏览器端，模块的加载是异步的
    基本语法：
        定义暴露模块：
            1. 没有依赖的模块：
                define(function(){
                    return 模块
                })
            2. 定义有依赖的模块：
                define(['module1','module2'],function(m1,m2){
                    return 模块
                })
        引入使用模块：
            require(['module1','module2'],function(m1,m2){
                使用m1/m2
            })
    实现(浏览器端)：
        使用库： Require.js
## 没有AMD之前的模块化编程
![目录结构](https://img-blog.csdnimg.cn/20210529171242108.png)
dataService.js：(用于提供数据)

```javascript
// 定义一个没有依赖的模块
(function(window){
    let name = 'dataService.js';
    function getName(){
        return name;
    }
    window.dataService = {getName};
})(window)
```
alerter.js: (用于使用数据)

```javascript
// 定义一个有依赖的模块
(function (window, dataService) {
    let msg = 'alerter.js';
    function showMsg() {
        console.log(msg,dataService.getName());
    }
    window.alerter = {showMsg};
})(window, dataService)
```
app.js: (作为根模板)

```javascript
(function(alerter){
    alerter.showMsg();
})(alerter)
```
index.html: （在浏览器端展示,引用顺序不能乱）

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
    <script src="./js/dataService.js"></script>
    <script src="./js/alerter.js"></script>
    <script src="./app.js"></script>
</body>
</html>
```
代码总览：
![代码总览](https://img-blog.csdnimg.cn/20210529171747840.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)这种方法虽然可以有效避免变量名污染，但各模板间的依赖关系有可能极为复杂，导致js文件引入出现问题。
## 使用AMD的模块化
RequireJS是一个非常小巧的JavaScript模块载入框架，是AMD规范最好的实现者之一,因此以下使用RequireJS展示。
目录结构：
![目录展示](https://img-blog.csdnimg.cn/20210529172033614.png)
dataService.js:

```javascript
// 定义没有依赖的模板
define(function() {
    let name = 'dataService.js';
    function getName() {
        return name;
    }
    // 暴露模块
    return {getName};
});
```
alerter.js:

```javascript
// 定义有依赖的模块
define(['dataService','jquery'], function(dataService, $) {
    let msg = 'alerter.js';
    function showMsg() {
        console.log(msg, dataService.getName());
    }
    $('body').css('background', 'pink');
    // 暴露模块
    return {showMsg};
});
```
app.js:

```javascript
(function(){
    requirejs.config({ // 参数配置
        // baseURL: 'js/', 基本路径，若开启，则路径为baseURL+paths
        paths: { // 配置路径,用以寻找模块
            dataService: './modules/dataService', // 此处不加.js后缀，因为运行时默认会加上
            alerter: './modules/alerter',
            jquery: './libs/jquery-2.2.4.min'
        }
    })

    requirejs(['alerter'], function(alerter){
        alerter.showMsg();
    })
})()
```
index.html:

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
    <!-- data-main引入入口js文件，src引入require.js库 -->
    <script data-main="./js/app.js" src="./js/libs/require.min.js"></script>
</body>
</html>
```
代码总览：
![代码总览](https://img-blog.csdnimg.cn/20210529172758776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)