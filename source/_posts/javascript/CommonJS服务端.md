---
title: Commonjs基于服务器端(node)的运用
date: 2021-05-28 20:29:52
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/18.jpg
tags: 
  - CommonJS
  - 模块化
  - node.js
categories: 'javascript'
---

    node是完全遵循CommonJS规范的
    CommonJS说明：
        每个文件都可以当作一个模块
        在服务端：模块的加载是运行时同步加载的
        在浏览器端：模块需要提前编译打包处理
    基本语法：
        暴露模块：
            module.exports = value
            exports.xxx = value
        引入模块：require(xxx) （其中第三方模块时xxx为模块名，自定义模块时为模块文件路径）
    实现：
        服务器端实现：Node.js
        浏览器端实现：Browserify（也称CommonJS的浏览器端的打包工具）
 大家还可以看看我的另一篇博文：[CommonJS基于浏览器端运用](https://blog.csdn.net/tongkongyu/article/details/117381504?spm=1001.2014.3001.5501)
 以下是代码部分：
 文件目录：
 ![目录](https://img-blog.csdnimg.cn/20210528202237452.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
先通过命令行初始化环境，方便下包：

```javascript
npm init --yes
```
下载uniq包：

```javascript
npm i uniq --save
```
modules目录下文件：
module1.js

```javascript
// module.exports = value 暴露一个对象
module.exports = {
    msg: 'module1',
    foo(){
        console.log(this.msg);
    }
};
```
module2.js

```javascript
// 暴露一个函数 module.exports = function()
module.exports = function() {
    console.log('module2');
}
```
module3.js

```javascript
// exports.xxx = value
exports.foo = function() {
    console.log('foo() module3');
};

exports.bar = function() {
    console.log('bar() module3');
};

exports.arr = [6,2,5,2,8,10];
```
app.js

```javascript
// 将其他模块汇集到主模块
let uniq = require('uniq'); // 第三方库应该放到自定义模板上方，这个库可以给数组排序并且去重

let module1 = require('./modules/module1');
let module2 = require('./modules/module2');
let module3 = require('./modules/module3');

// module1是对象，所以调用方式如下：
module1.foo();
// module2是函数，所以调用方式如下：
module2();
// module3有多个函数，所以调用方式如下：
module3.foo();
module3.bar();
console.log(module3.arr);
// 取出module3中的数组,该方法会自动给数组元素去重和排序，根据数字第一位编码排序
let result = uniq(module3.arr);
console.log(result);
```
node运行：

![运行结果](https://img-blog.csdnimg.cn/20210528202900396.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)