---
title: File Reader文件操作
date: 2021-06-11 18:23:56
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/4.jpg
tags: 
  - 文件
categories: javascript
---

        文件File对象
        该对象允许Web应用程序异步读取存储在用户计算机上的文件（或原始数据缓冲区）的内容，使用File或Blob对象指定要读取的文件或数据。
        其中File对象可以是来自用户在一个<input/>元素上选择文件后返回的FileList对象，也可以是来自拖拽操作生成的DataTransfer对象，
        还可以是来自一个HTMLCanvasElement上执行 mozGetAsFile()方法后返回的结果；
        Blob对象表示一个不可变、原始数据的类文件对象，它的数据可以按文本或二进制的格式进行读取，也可以转换成 ReadableStream来用于数据操作，
        Blob表示的不一定是JavaScript原生格式的数据，File接口基于Blob，继承了Blob的功能并将其扩展使其支持用户系统上的文件。
        操作：
            创建一个FileReader对象
                var fReader = new FileReader();
            方法：
                abort()  终止该读取操作
                开始读取指定的Blob对象或File对象中的内容
                readAsDataURL()  URL格式的字符串表示的内容
                readAsArrayBuffer()  ArrayBuffer对象表示的内容
                readAsBinaryString()  原始二进制数据
                readAsText()  字符串以表示的内容
            属性：
                error ： 在读取文件时发生的错误
                readyState ： 表明FileReader对象的当前状态
                result ： 读取到的文件内容
                onabort ： 当读取操作被终止时调用
                onerror ： 当读取操作发生错误时调用
                onload ： 当读取操作成功完成时调用
                onloadend ： 当读取操作完成时调用，不管是成功或者失败，该处理程序在onload或onerror之后调用
                onloadstart ： 当读取操作将要开始之前调用
                onprogress ： 在读取数据过程中周期性调用
以下是基本操作代码：

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
  
    <input type="file" onchange="getInfo(this)">
    <script>
        function getInfo(node){
            console.log(node.files[0]);

            // 创建一个FileReader对象
            var fr = new FileReader();

            // 读取文件
            fr.readAsText(node.files[0]);

            // 监听读取成功
            fr.onload = function(e){
                console.log(e);
                // 用选择的格式表示该文件
                console.log(e.target.result);
            }
        }
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611172406286.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)其中1.txt内容为：
![展示](https://img-blog.csdnimg.cn/20210611172457651.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)下面做一个多图片文件读取显示案例：

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
    <input type="file" multiple id="myfile" onchange="loadfile()">
    <div id="content"></div>
    <script>
        var myfile = document.getElementById("myfile");
        var fReader = new FileReader();
        var con = document.getElementById('content');
        var n = 0;  // 文件个数
        var len;  // 文件的个数

        // 需要等前一个文件读完才能读下一个，因此不能直接for循环读取所有文件
        function loadfile(){
            // 文件提交的时候，设置读取文件的个数
            len = myfile.files.length;
            fReader.readAsDataURL(myfile.files[0]);
        }

        fReader.onload = function(e){
            // 以图片形式放到div中
            var str = '<img src="' + e.target.result + '">';
            con.innerHTML += str;
            n++;
            if(n < len){
                readfile(n);
            }
        }

        function readfile(n){
            fReader.readAsDataURL(myfile.files[n]);
        }
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611181040938.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)        在我们的页面当中，经常会上传一些文件，同时在上传之前还需要立即展示以下文件内容，使用FileReader API来读取本地文件，可以保存在本地，可快速展示，例如：上传头像，提交完图片立刻显示，还可以做裁剪，或拖拽文件，预览文件内容。
拓展：window.URL.createObjectURL()方法可以返回一个文件对象的url

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
    <input type="file" multiple id="myfile" onchange="loadfile()">
    <div id="content"></div>
    <script>
        var myfile = document.getElementById("myfile");
        var con = document.getElementById('content');

        function loadfile(){
            // 返回url数据结果
            var url = window.URL.createObjectURL(myfile.files[0]);
            // 放到页面上
            var str = '<img src="' + url + '">';
            con.innerHTML += str;
        }
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611182204427.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)可见用这种方法图片返回的是url地址，之前读取文件的是返回base64编码，见下：
![展示](https://img-blog.csdnimg.cn/20210611182318727.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)