---
title: 使用Webpack5打包各类资源
date:  2021-10-14 20:00:02
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/108.jpg
tags:
  - webpack
categories: javascript
---


# 一：webpack五个核心概念
+ **Entry：** 入口(Entry)指示 webpack 以哪个文件为入口起点开始打包，分析构建内部依赖图。
+ **Output：** 输出(Output)指示 webpack 打包后的资源 bundles 输出到哪里去，以及如何命名。
+ **Loader：** Loader 让 webpack 能 够 去 处 理 那 些 非 JavaScript 文 件 (webpack 自 身 只 理 解 JavaScript) 
+ **Plugins：** 插件(Plugins)可以用于执行范围更广的任务。插件的范围包括，从打包优化和压缩， 一直到重新定义环境中的变量等。
+ **Mode：** 模式(Mode)指示 webpack 使用相应模式的配置。

mode的两个选项：
| 选项 | 描述 | 特点 |
|--|--|--|
| development | 会将 DefinePlugin 中 process.env.NODE_ENV 的值设置 为 development。<br>启用 NamedChunksPlugin 和 NamedModulesPlugin。 | 能让代码本地调试运行的环境 |
| production | 会将 DefinePlugin 中 process.env.NODE_ENV 的值设置 为 production。<br>启用 FlagDependencyUsagePlugin, FlagIncludedChunksPlugin, ModuleConcatenationPlugin, NoEmitOnErrorsPlugin, OccurrenceOrderPlugin, SideEffectsFlagPlugin 和 TerserPlugin。 | 能让代码优化上线运行的环境 |

# 二：webpack初体验
进入指定目录，输入命令：

```shell
npm init --yes
```
`npm init`是初始化npm环境的命令，而`--yes`表示全部配置项为默认值。

然后全局安装webpack：

```shell
npm install webpack webpack-cli -g
```

然后本地安装webpack:



```shell
npm install webpack-cli -D
```
安装成功的标志：
![在这里插入图片描述](https://img-blog.csdnimg.cn/94b07b4ec0cf47439bea4f7927bb2b7d.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
安装成功后目录下会出现这两个文件以及一个文件夹
![在这里插入图片描述](https://img-blog.csdnimg.cn/9840397b307449318389310c80957baf.png)
而一个标准的文件目录格式如下，需要自己再建一个build文件夹，用以存放打包后的代码，这个代码可以由浏览器运行，另外还要创建一个src文件夹，用以存放自己写的代码，src内创建index.js文件作为webpack打包入口文件。
![在这里插入图片描述](https://img-blog.csdnimg.cn/06016419bb064395a3c1cb6e2ce9dc15.png)
在index.js写下如下代码：

```js
function add(x, y) {
    return x + y;
}

console.log("hello world")
```
然后再测试打包效果，webpack开发环境打包指令如下：

```shell
webpack ./src/index.js -o ./build/built --mode=development
```
该指令的意思是：webpack以./src/index.js作为入口文件，-o是输出的意思，即打包输出到./build/built目录，且整体打包环境是开发环境。

运行后在build目录下会出现一个文件夹built
![在这里插入图片描述](https://img-blog.csdnimg.cn/c8b5da97db004a44915f17c358e7336b.png)
其中main.js内容如下,因为是开发环境打包，因此并没有压缩代码量的效果
```javascript
/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
/******/ (() => { // webpackBootstrap
/******/ 	var __webpack_modules__ = ({

/***/ "./src/index.js":
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
/***/ (() => {

eval("function add(x, y) {\r\n    return x + y;\r\n}\r\n\r\nconsole.log(\"hello world\")\n\n//# sourceURL=webpack://test/./src/index.js?");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = {};
/******/ 	__webpack_modules__["./src/index.js"]();
/******/ 	
/******/ })()
;
```
然后我们就可以试试生产环境下的打包，结果只有一个输出语句，因为那个add函数没有调用，因此直接省略掉了，注释也没了，可见打包还是很节约空间的。

```shell
webpack ./src/index.js -o ./build/built --mode=production
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/7ddccae1256e498ebcf8c0669abf5df7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 三：打包样式资源
在打包前，需要现在项目根目录建一个webpack.config.js文件，该文件作为webpack的配置文件而存在，作用是指示webpack如何运行，即运行webpack指令时，会加载里面的配置。里面使用commonjs语法(目前所有构建工具都是基于nodejs平台运行，模块化默认采用commonjs)

下面是webpack.config.js文件的基础配置格式：

```javascript
// resolve用来拼接绝对路径的方法
const { resolve } = require("path");

module.exports = {
    // 入口起点
    entry: "./src/index.js",
    // 输出
    output: {
        // 输出文件名
        filename: "built.js",
        // 输出路径
        // __dirname 是nodejs的变量，代表当前文件的目录绝对路径
        path: resolve(__dirname, "build")
    },
    // loader的配置
    module: {
        rules: [
            // 详细loader配置
        ]
    },
    // plugins的配置
    plugins: [
        // 详细plugins的配置
    ],
    // 打包模式
    mode: "development"  // mode: "production"
}
```

而打包样式资源需要使用css-loader与style-loader，其中css-loader会将css文件变成commonjs模块加载到js中，里面内容是样式字符串。而style-loader会创建style标签，将js中的样式资源插入进去，添加到head中生效。使用时需要在webpack.config.js需要进行配置，只要改变module的内容就可以了，如下：

```javascript
    module: {
        rules: [
            // 详细loader配置
            {
                // 匹配哪些文件
                test: /\.css$/,
                // 使用哪些loader进行处理,use数组的执行顺序是从右往左，从下到上
                use: [
                    "style-loader",
                    "css-loader"
                ]
            }
        ]
    },
```
配置好后就是下载css-loader与style-loader了：

```shell
npm i css-loader style-loader -D
```
下载好后就可以打包样式文件了，在src目录新建css目录，创建文件index.css，写下如下代码：

```css
html {
    height: 100%;
    background-color: aqua;
}
```
在index.js中引入：

```javascript
import "./css/index.css"

function add(x, y) {
    return x + y;
}

console.log("hello world")
```
命令行运行指令：

```shell
webpack
```
运行后在build目录下生成built.js文件，在build目录新建index.html，引入built.js用以测试效果。

代码总览如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/4de0399754ef4602b09747b55f0cd955.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
运行index.html文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/e70880c7bb044ac78d9682a547c99392.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
这是打包css文件，如果要打包less文件的话，还需要进行配置。首先是配置webpack.config.js，只需要在modules中添加一项less的loader配置就可以了：

```javascript
    module: {
        rules: [
            // 详细loader配置
            {
                // 匹配哪些文件
                test: /\.css$/,
                // 使用哪些loader进行处理,use数组的执行顺序是从右往左，从下到上
                use: [
                    "style-loader",
                    "css-loader"
                ]
            },
            {
                // 配置less的loader
                test: /\.less$/,
                use: [
                    "style-loader",
                    "css-loader",
                    // less-loader能将less文件编译成css文件
                    "less-loader"
                ]
            }
        ]
    },
```
然后下载less-loader与less：

```shell
npm i less-loader less -D
```
然后就可以进行测试了，在css文件夹里建一个test.less文件，写下以下代码：

```css
body {
    height: 500px;
    background-color: bisque;
    div {
        color: red;
    }
}
```
然后在index.js中引入：
![在这里插入图片描述](https://img-blog.csdnimg.cn/8d8ba67296a1475bb3340f2e691ca396.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
打开index.html结果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/d1a9890bacbf4418a9a6545609669206.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 四：打包html资源
处理html资源就需要用到插件而不是loader了：

```shell
npm i html-webpack-plugin -D
```
在src下新建一个index.html文件，我们可以在index.html里面写html代码，打包的时候，webpack会将index.html复制一份到build目录，并把打包的资源通过标签的方式引入到index.html中。

webpack.config.js中增加的配置如下：

```javascript
    // plugins的配置
    plugins: [
        // html-webpack-plugin插件：会自动创建一个空的html文件，并自动引入打包输出的所有资源(js/css)
        new HtmlWebpackPlugin({
            // 复制"./src/index.html"文件，并自动引入打包输出的所有资源(js/css)
            template: "./src/index.html"
        })
    ],
```

代码总览如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/40148573859d4f918579303060ba4661.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
运行结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/178ab7441e4e432c881fe43c50d2fb1f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 五：打包图片资源
打包图片资源依赖两个loader：url-loader与file-loader：

```shell
npm i url-loader file-loader -D
```
url-loader内部继承了file-loader，与file-loader不同的是：
+ url-loader可以把较小的图片转化成base64数据，从而减少对图片资源的http请求。同时打包文件也会变大；
+ 当图片大小超过设置的限制（limit）时，默认采用file-loader进行加载。fallback默认值“file-loader”；
+ 所有file-loader的属性，url-loader均可以使用。例如publicPath设置图片公共地址（图片部署到CDN服务器）

现在就可以开始准备了，先在src目录下新建一个index.html，代码如下：

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
    <div>我是index.html</div>
    <div class="img1"></div>
    <div class="img2"></div>
    <div class="img3"></div>
</body>
</html>
```
再准备三张图片以备打包，在src中新建image目录，图片如下，注意第三张logo的大小是3.92kb，而另外两张均大于100kb，他们在后续打包时会有不同。
![在这里插入图片描述](https://img-blog.csdnimg.cn/4ea9c647190a44ef9b439e412436e9ad.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

再在css目录下的test.less写下：

```css
body {
    background-color: bisque;

    .img1 {
        width: 100px;
        height: 100px;
        background-image: url("../image/10.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }

    .img2 {
        width: 100px;
        height: 200px;
        background-image: url("../image/15.jpg");
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }

    .img3 {
        width: 50px;
        height: 50px;
        background-image: url("../image/logo.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }
}
```
webpack.config.js的loader配置如下：

```javascript
// loader的配置
    module: {
        rules: [
            {
                // 匹配哪些文件
                test: /\.css$/,
                // 使用哪些loader进行处理,use数组的执行顺序是从右往左，从下到上
                use: [
                    "style-loader",
                    "css-loader"
                ]
            },
            {
                // 配置less的loader
                test: /\.less$/,
                use: [
                    "style-loader", 
                    // less-loader能将less文件编译成css文件
                    "css-loader",
                    "less-loader",
                ]
            },
            {
                // 处理图片资源
                test: /\.(jpg|png|gif)$/,
                use: [
                    {
                        loader: "url-loader",
                        options: {
                            // 图片大小限制，小于该值则会被处理为base64编码，优点是减少请求数量，缺点是打包体积会更大
                            limit: 8 * 1024,  // 单位是b, 一般用kb描述，因此乘以1024
                            esModule:false
                        },
                    }
                ],
                type: 'javascript/auto',
            }
        ],
    },
```

代码总览如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/4fb242bd52dc4389bc2837d3ee96f454.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
命令行输入webpack命令开始打包，打包后build目录如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/15886221516f45859e27ce1f2c2bd55c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
可见打包后只剩下了两张图片，打开index.html，因为logo小于8kb，因此被处理为base64编码。而图片名称也被修改了，这是webpack根据图片内容生成的唯一hash值，避免打包时出现同名图片。
![在这里插入图片描述](https://img-blog.csdnimg.cn/a7739aaa91a34648b049a0beabab55fc.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
上述示例是从css中引入图片资源，事实上我们还有可能会在html中引用图片资源，现修改index.html为以下代码：

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
    <div>我是index.html</div>
    <div class="img1"></div>
    <div class="img2"></div>
    <div class="img3"></div>
    <img src="./image/10.png">
    <img src="./image/logo.png">
</body>
</html>
```
如果要打包这种图片的话，需要额外处理，即下载html-loader，html-loader专门处理html文件里引入的图片，它能引入图片交给url-loader进行处理。

```shell
npm i html-loader -D
```
webpack.config.js的loader配置如下：

```javascript
    // loader的配置
    module: {
        rules: [
            {
                // 匹配哪些文件
                test: /\.css$/,
                // 使用哪些loader进行处理,use数组的执行顺序是从右往左，从下到上
                use: [
                    "style-loader",
                    "css-loader"
                ]
            },
            {
                // 配置less的loader
                test: /\.less$/,
                use: [
                    "style-loader", 
                    // less-loader能将less文件编译成css文件
                    "css-loader",
                    "less-loader",
                ]
            },
            {
                // 处理css中的图片资源
                test: /\.(jpg|png|gif)$/,
                use: [
                    {
                        loader: "url-loader",
                        options: {
                            // 图片大小限制，小于该值则会被处理为base64编码，优点是减少请求数量，缺点是图片体积会更大
                            limit: 8 * 1024,  // 单位是b, 一般用kb描述，因此乘以1024
                            // url-loader默认使用es6模块化解析，而html-loader引入图片是commonjs，因此需要改为false
                            esModule: false,
                            // name: '[hash:10].[ext]'  // 自定义打包后的图片名，即hash取前十位，后缀名不变
                        },
                    }
                ],
                type: 'javascript/auto',
            },
            {
                // 处理html中的图片资源
                test: /\.html$/,
                loader: "html-loader"
            }
        ],
    },
```
代码总览如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/7f648bec8f0e483b9543311f3dae9982.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
运行结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/97d7e8f814614bbfa56abbc62e818fa7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

