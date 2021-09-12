---
title: ES6模块化编程
date:  2021-05-29 21:23:59
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/2.jpg
tags: 
  - ES6
  - 模块化
categories: javascript
---

```
ES6依赖模块需要编译打包处理
语法：
    导出模块： export
    引入模块： import
实现(浏览器端)：
    使用Babel将ES6编译为ES5代码
    使用Browserify编译打包js
```
先预览一下文件目录：
![文件目录](https://img-blog.csdnimg.cn/20210529205746354.png)
因为在实现时需要使用npm下载一些包，所以先初始化环境：

```javascript
npm init --yes
```
手动创建src文件放自己写的代码，module文件夹下的三个文件对应ES6三种暴露方式，main为根模块，也是打包入口。

module1.js:

```javascript
// 暴露模块  分别暴露

export function foo() {
    console.log("foo() module1");
}

export function bar() {
    console.log("bar() module1");
}

export let arr = [1, 2, 3, 4, 5];
```
module2.js:

```javascript
// 统一暴露

function fun1() {
    console.log("fun1() module2");
}

function fun2() {
    console.log("fun2() module2");
}

export {fun1, fun2};
```
module3.js:

```javascript
// 默认暴露 可以暴露任何数据类型，暴露什么数据接收到的就是什么数据
// export default value;
export default {
    msg: '我是默认暴露的对象',
    foo: function(){
        console.log(this.msg);
    },
    bar() {
        console.log('我是默认暴露的函数');
    }
}
```
main.js:

```javascript
// 引入其他模块
// 语法： import xxx from '路径';
import $ from 'jquery'; // 引入第三方库默认放最上面

import {foo, bar, arr} from './module/module1'; //引入时是灰色，使用后变成正常亮色
import {fun1, fun2} from './module/module2';
import module3 from './module/module3';

foo();
bar();
console.log(arr);
fun1();
fun2();
module3.foo();
module3.bar();
```
代码全局预览：
![全局预览](https://img-blog.csdnimg.cn/2021052921222877.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

此时原始代码有很多ES6语法以及require等接口无法被多数浏览器识别，因此需要对其进行处理
先使用babel将ES6语法转为ES5语法，需要先全局安装babel-cli, 局部安装babel-preset-es2015,安装完后新增babel命令。
    - preset 预设(将es6转换成es5的所有插件打包)
    - cli (command line interface 命令行接口)

```powershell
npm i babel-cli -g
```

```powershell
npm i babel-preset-es2015 --save-dev
```
安装好后还不能开始转换操作，babel工作前先需要读取 .babelrc 文件内的配置，因此在根目录创建.babelrc文件夹，该文件代码格式为json格式，里面储存配置信息,代码如下，意思是让babel转成ES5语法

```json
{
    "presets": ["es2015"]
}
```
准备工作做好后便可进行翻译操作：

```powershell
babel src -d build
```
翻译不会改变目录结构
如图：
![目录展示](https://img-blog.csdnimg.cn/20210529211313102.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
但是require语句无法翻译，此时需要browserify进行打包处理，先配置browserify环境，配置完后新增browserify命令。

```powershell
npm i browserify -g
```
开始打包输出到dist文件夹下：

```powershell
browserify build/main.js -o dist/bundle.js
```

打包成功后dist文件夹下新增bundle.js文件，该文件可以正常使用。
index.html：

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
    <script src="./dist/bundle.js"></script>
</body>
</html>
```
效果展示：
![效果展示](https://img-blog.csdnimg.cn/20210529212056736.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
