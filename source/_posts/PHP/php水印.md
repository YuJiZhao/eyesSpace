---
title: GD图像处理——水印图实现
date: 2021-07-29 13:15:41
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/69.jpg
tags: 
  - 水印图
  - 图像
  - GD库
categories: PHP
---

**水印图概念**
照片水印主要指的是现代的数字照片上面留下的一些logo、信息、网址什么的，比如图像，声音，视频信号等等在里面加了一些数字信息，这样是为了版权保护，当然加入水印也是不能影响原始文件的可读性以及观瞻的，水印具有以下几个特征，首先是证明各位是这个照片的原始主人，或者有的商家为了做宣传用，也会在照片上增加自己的网址，品牌名字等等。


**使用图片作水印**
制作图片水印图原理：将一个带有明显标志的图片放到另外一张需要处理的图片之上
制作流程：
a. 获得原图资源
b. 获取水印图资源
c. 合并图片(把水印图合到目标图上)
d. 保存输出
e. 清除资源

使用该方法需要先了解一下PHP中的 imagecopy() 函数，该函数能复制图像的一部分，语法格式如下：

```php
imagecopy($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct);
```
该函数可以将 \$src_im 图像中坐标（\$src_x，\$src_y）的位置，拷贝一份宽度为 \$src_w，高度为 \$src_h 的矩形区域到 \$dst_im 图像中坐标为 （\$dst_x，\$dst_y）的位置上，最后一个参数是设置\$src_im的透明度。

要使用图片水印的话，我们就需要明确水印图片的宽度和高度，除了可以使用 getimagesize() 函数外，还可以使用 PHP 中的 imagesx()、imagesy() 两个函数来分别获取图片的宽度和高度。

```php
imagesx($image)
imagesy($image)
```

代码实现如下：

```php
<?php

// 指定图片
$src_image = 'https://i0.hdslb.com/bfs/album/548e17fdb10dca85d136040896d1e8384daeb4d7.jpg';
$wat_image = 'https://www.runoob.com/wp-content/uploads/2013/07/pic_html5.gif';

// 获得原图资源
$dst = imagecreatefromjpeg($src_image);
$wat = imagecreatefromgif($wat_image);

// 合并图片(水印制作)
$res = imagecopymerge($dst, $wat, imagesx($dst) - imagesx($wat), imagesy($dst) - imagesy($wat),0, 0, imagesx($wat), imagesy($wat), 50);

// 保存输出
header('Content-type:image/jpeg');
imagejpeg($dst);

// 清除资源
imagedestroy($dst);
imagedestroy($wat);
```
![展示](https://img-blog.csdnimg.cn/87d084e6f0ea4a5c9e8cd87942f23bc5.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
封装版代码：

```php
<?php
header('Content-type:image/jpeg');
/*
 * [watermark description]
 * @param  string  $img              [待加水印的图片地址]
 * @param  string  $watermark        [水印图片地址]
 * @param  integer $district         [水印的位置]
 * @param  integer $watermarkquality [图片水印的质量]
 * @return                           [添加水印的图片]
 */
function watermark($img, $watermark, $district = 0,$watermarkquality = 95){
    $imginfo = @getimagesize($img);
    $watermarkinfo = @getimagesize($watermark);
    $img_w = $imginfo[0];
    $img_h = $imginfo[1];
    $watermark_w = $watermarkinfo[0];
    $watermark_h = $watermarkinfo[1];
    if($district == 0) $district = rand(1,9);
    if(!is_int($district) OR 1 > $district OR $district > 9) $district = 9;
    switch($district){
        case 1:
            $x = +5;
            $y = +5;
            break;
        case 2:
            $x = ($img_w - $watermark_w) / 2;
            $y = +5;
            break;
        case 3:
            $x = $img_w - $watermark_w - 5;
            $y = +5;
            break;
        case 4:
            $x = +5;
            $y = ($img_h - $watermark_h) / 2;
            break;
        case 5:
            $x = ($img_w - $watermark_w) / 2;
            $y = ($img_h - $watermark_h) / 2;
            break;
        case 6:
            $x = $img_w - $watermark_w;
            $y = ($img_h - $watermark_h) / 2;
            break;
        case 7:
            $x = +5;
            $y = $img_h - $watermark_h - 5;
            break;
        case 8:
            $x = ($img_w - $watermark_w) / 2;
            $y = $img_h - $watermark_h - 5;
            break;
        case 9:
            $x = $img_w - $watermark_w - 5;
            $y = $img_h - $watermark_h - 5;
            break;
    }
    switch ($imginfo[2]) {
        case 1:
            $im = @imagecreatefromgif($img);
            break;
        case 2:
            $im = @imagecreatefromjpeg($img);
            break;
        case 3:
            $im = @imagecreatefrompng($img);
            break;
    }
    switch ($watermarkinfo[2]) {
        case 1:
            $watermark_logo = @imagecreatefromgif($watermark);
            break;
        case 2:
            $watermark_logo = @imagecreatefromjpeg($watermark);
            break;
        case 3:
            $watermark_logo = @imagecreatefrompng($watermark);
            break;
    }
    if(!$im or !$watermark_logo) return false;
    $dim = @imagecreatetruecolor($img_w, $img_h);
    if(@imagecopy($dim, $im, 0, 0, 0, 0,$img_w,$img_h )){
        imagecopy($dim, $watermark_logo, $x, $y, 0, 0, $watermark_w, $watermark_h);
    }
    // 以下注释是为本地文件添加水印，可按需使用
//    $file = dirname($img) . '/w' . basename($img);
//    $result = imagejpeg ($dim,$file,$watermarkquality);
    imagejpeg($dim); // 为了引用网络图片将注释代码换成了这个
    imagedestroy($watermark_logo);
    imagedestroy($dim);
    imagedestroy($im);
//    if($result){
//        echo $img.' 水印添加成功';
//        return;
//    }
//    else {
//        return false;
//    }
}

$file = 'https://i0.hdslb.com/bfs/album/548e17fdb10dca85d136040896d1e8384daeb4d7.jpg';   //待加水印的图片地址
$water = 'https://www.runoob.com/wp-content/uploads/2013/07/pic_html5.gif';  //水印图片的地址
watermark($file, $water);
```

使用文字作为水印图只需要在图片上画上一些文字即可。详情可见我的另一篇博客：[PHP之GD图像处理](https://blog.csdn.net/tongkongyu/article/details/119154930)

有兴趣还可以看看我的其他相关博客：
[GD图像处理——验证码处理](https://blog.csdn.net/tongkongyu/article/details/119154877)
[GD图像处理——缩略图的实现](https://blog.csdn.net/tongkongyu/article/details/119154890)