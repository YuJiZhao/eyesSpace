---
title: GD图像处理——验证码处理
date: 2021-07-28 19:23:05
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/3.jpg
tags: 
  - 验证码
  - 图像
  - GD库
categories: PHP
---

## 验证码的概念
验证码（CAPTCHA）是“Completely Automated Public Turingeest to tell Computers and Humans Apart”（全自动区分 计算机 和人类的 图灵测试 ）的缩写，是一种区分用户是计算机还是人的公共全自动 程序 。 可以防止：恶意破解密码、 刷票 、论坛灌水，有效防止某个黑客对某一个特定注册用户用特定程序暴力破解方式进行不断的登陆尝试，实际上用验证码是现在很多网站通行的方式，我们利用比较简易的方式实现了这个功能。 这个问题可以由计算机生成并评判，但是必须只有人类才能解答。 由于计算机无法解答CAPTCHA的问题，所以回答出问题的用户就可以被认为是人类。

中文名: 验证码
作用: 防止恶意破解密码、论坛灌水等
具体: 区分用户是计算机或人的程序
外文名: CAPTCHA

## 生成验证码
这篇博客使用的是PHP的GD库实现相关操作，对GD库不熟悉的话可以先看看我的另一篇博客：[PHP之图像处理](https://blog.csdn.net/tongkongyu/article/details/119154930)

图形验证码：计算机将拿到的验证码存放到图片中，然后用户看到然后识别，再提交给服务器，服务器再根据用户提交的和服务器之前生成的进行比较。

1). 实现验证码图片的展示
2). 实现验证码文字的随机变化
3). 实现验证码文字颜色的随机变化
4). 实现验证码背景或干扰噪点
5). 实现点击刷新验证码功能

**随机汉字的验证码：**

gd_demo.php文件代码：
```php
<?php

// 创建图片资源
$img = imagecreatetruecolor(200, 50);

// 背景色处理
$bg_color = imagecolorallocate($img, 220, 220, 220);
imagefill($img, 0, 0, $bg_color);

// 获得随机文字
$str = '验证码是一种区分用户是计算机还是人的公共全自动程序可以防止恶意破解密码刷票论坛灌水有效防止某个黑客对某一个特定注册用户用特定程序暴力破解方式进行不断的登陆尝试';

// 获得字符串长度
$len = strlen($str);  // 字节长度
// 当前所有的内容都是中文，因此每个字必定占三个字节
$c_len = $len / 3;

// 随机取汉字
$char1 = substr($str, mt_rand(0, $c_len -1) * 3, 3);
$char2 = substr($str, mt_rand(0, $c_len -1) * 3, 3);
$char3 = substr($str, mt_rand(0, $c_len -1) * 3, 3);
$char4 = substr($str, mt_rand(0, $c_len -1) * 3, 3);

// 选择字体
$font = 'C:\Windows\Fonts\simhei.ttf';

// 随机颜色
$str_color1 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
$str_color2 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
$str_color3 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));
$str_color4 = imagecolorallocate($img, mt_rand(0, 100), mt_rand(0, 100), mt_rand(0, 100));

// 写入文字
imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(0, 30), mt_rand(30, 40), $str_color1, $font, $char1);
imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(50, 70), mt_rand(30, 40), $str_color2, $font, $char2);
imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(90, 120), mt_rand(30, 40), $str_color3, $font, $char3);
imagettftext($img, mt_rand(20, 30), mt_rand(-45, 45), mt_rand(140, 170), mt_rand(30, 40), $str_color4, $font, $char4);

// 设置干扰
for($i = 0; $i < 50; $i++){  // 干扰点
    $dot_color = imagecolorallocate($img, mt_rand(150, 250), mt_rand(150, 250), mt_rand(150, 250)); // 设置点的颜色
    imagestring($img, mt_rand(1, 5), mt_rand(0, 200), mt_rand(1, 50), '*', $dot_color);
}
for($i = 0; $i < 10; $i++){  // 干扰线
    $line_color = imagecolorallocate($img, mt_rand(150, 250), mt_rand(150, 250), mt_rand(150, 250)); // 设置线的颜色
    imageline($img, mt_rand(0, 200), mt_rand(0, 50), mt_rand(0, 200), mt_rand(0, 50), $line_color);
}

// 输出图片
header('Content-type:image/png');
imagepng($img);

// 关闭资源
imagedestroy($img);
```
gd_demo.html文件代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--实现点击图片可自动更新验证码-->
<div style="width: 400px; height: 200px; margin: 100px auto">
    <img src="gd_demo.php" onclick="this.src = 'gd_demo.php?captcha=' + Math.random()"/>
</div>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/9fe36d3377774cd5bc70d4b0f6ce3225.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
**随机数字字母的验证码(封装版代码)：**

demo.php文件代码：
```php
<?php
function rand_str($length) {
    // 验证码中所需要的字符
    $chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    $str = '';
    for($i = 0; $i < $length; $i++)
    {
        // 随机截取 $chars 中的任意一位字符；
        $str .= substr($chars, mt_rand(0, strlen($chars) - 1), 1);
    }
    return $str;
}

function rand_color($image){
    // 生成随机颜色
    return imagecolorallocate($image, rand(127, 255), rand(127, 255), rand(127, 255));
}

$image = imagecreate(200, 100);
imagecolorallocate($image, 0, 0, 0);
for ($i=0; $i <= 9; $i++) {
    // 绘制随机的干扰线条
    imageline($image, rand(0, 200), rand(0, 100), rand(0, 200), rand(0, 100), rand_color($image));
}
for ($i=0; $i <= 100; $i++) {
    // 绘制随机的干扰点
    imagesetpixel($image, rand(0, 200), rand(0, 100), rand_color($image));
}
$length = 4;//验证码长度
$str = rand_str($length);//获取验证码
$font = 'C:\Windows\Fonts\simhei.ttf';
for ($i=0; $i < $length; $i++) {
    // 逐个绘制验证码中的字符
    imagettftext($image, rand(20, 38), rand(0, 60), $i*50+25, rand(30,70), rand_color($image), $font, $str[$i]);
}

header('Content-type:image/jpeg');
imagejpeg($image);
imagedestroy($image);
```
demo.html文件代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--实现点击图片切换验证码-->
<div style="width: 400px; height: 200px; margin: 100px auto">
    <img src="demo.php" onclick="this.src = 'demo.php?captcha=' + Math.random()"/>
</div>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/03dd9d15b5e1407db21db921b1dbed93.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
有兴趣的话还可以看看我的其他相关博客：
[GD图像处理——水印图实现](https://blog.csdn.net/tongkongyu/article/details/119154901)
[GD图像处理——缩略图实现](https://blog.csdn.net/tongkongyu/article/details/119154890)