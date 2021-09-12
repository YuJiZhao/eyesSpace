---
title: GD图像处理——缩略图的实现
date: 2021-07-29 00:28:00
updated: 2021-07-29 00:28:00
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/68.jpg
tags: 
  - GD库
  - 缩略图
categories: PHP
---

对PHP的GD库不熟悉的话可以先看看我的另一篇博客：[PHP之GD图像处理](https://blog.csdn.net/tongkongyu/article/details/119154930)

**制作图片缩略图的原理**
缩略图：通过原图得到一个较小的图（尺寸上）
缩略图原理：将原图打开，然后放到另一个较小的图片资源中，最后进行保存。

**实现固定宽高的缩略图**
a. 得到一张原图资源
b. 得到一个缩略图资源（较小）
c. 图片采样复制：GD提供了一个函数
d. 保存缩略图
e. 销毁所有资源（原图和缩略图）

实际开发中，一般在上传图片时就需要对图片进行压缩操作，想要压缩图片可以使用 imagecopyresized() 或者 imagecopyresampled() 函数，这两个函数都可以将一幅图像中的一块矩形区域拷贝到另一个图像中，而 imagecopyresampled() 函数更是可以平滑地插入像素值，因此，在减小了图像的大小的同时仍然保持极大的清晰度，所以在进行图片压缩时可以优先使用 imagecopyresampled() 函数。

```php
imagecopyresized($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h);

imagecopyresampled($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h);
```
两个函数的参数是完全一样的，说明如下：
+ $dst_image：目标图象连接资源。
+ $src_image：源图象连接资源。
+ $dst_x：目标 X 坐标点。
+ $dst_y：目标 Y 坐标点。
+ $src_x：源的 X 坐标点。
+ $src_y：源的 Y 坐标点。
+ $dst_w：目标宽度。
+ $dst_h：目标高度。
+ $src_w：源图象的宽度。
+ $src_h：源图象的高度。

```php
<?php

// 制作固定尺寸缩略图：按照缩略图要求的大小制作，不管原图比例（可能失真）

// 得到原图资源
$src_image = 'https://www.runoob.com/wp-content/uploads/2013/07/pic_html5.gif';
$src = imagecreatefromgif($src_image);

// 制作缩略图资源
$dst = imagecreatetruecolor(100, 100);

// 采样复制
imagecopyresampled($dst, $src, 0, 0, 0, 0, 100, 100, imagesx($src), imagesy($src));

// 保存缩略图
header('Content-type:image/png');
imagepng($dst, 'dst.png');
header('Content-type:image/jpeg');
imagepng($dst, 'dst.jpg');

// 销毁资源
imagedestroy($src);
imagedestroy($dst);
```

![展示](https://img-blog.csdnimg.cn/3469c29f054d4fc4b73d2105c0c1bbf9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
但是我经过比对发现，这样做出来的缩略图体积不一定会小，由下图可见，原图是128*128px, 缩略图是100*100px，但生成的缩略图比原图还大好几倍，而且jpg格式和png格式大小一样，本人查询各类资料都没找到合理的解释，如果有大佬知道的话还请在留言区指点指点。
![在这里插入图片描述](https://img-blog.csdnimg.cn/593e2b9e7b8344b7a0fa4dd8986357f8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
**实现等比例缩放的固定宽或高**
优点：图片不会失真(变形)
缺点：缩略图有些部分需要进行额外填充(白色填充：补白)

等比例缩略图与固定缩略图的制作区别：在于需要通过计算来得出缩略图的宽和高
算法原理：
1. 计算缩略图宽高比
2. 计算原图宽高比
3. 比较：
      如果缩略图宽高比大于原图宽高比，将缩略图中用原图的高尽可能填满：缩略图的高是完整的，宽度不够(补白)
      如果缩略图宽高比小于原图宽高比，将缩略图中用原图的宽尽可能填满：缩略图的宽是完整的，高度不够(补白)

```php
<?php
// 获得原图资源
$src_image = 'https://www.runoob.com/images/pulpit.jpg';

// 获得图片信息
$src_info = getimagesize($src_image);

// 创建画布资源
$src = imagecreatefromjpeg($src_image);

// 制作缩略图资源
$dst = imagecreatetruecolor(100, 100);

// 填充背景色
$dst_color = imagecolorallocate($dst, 255, 255, 255);
imagefill($dst, 0, 0, $dst_color);

// 计算缩略图从原图采样的宽和高(缩略图到底是宽还是高被填满)
$thumb_b = 100 / 100;
$src_b = $src_info[0] / $src_info[1];

// 声明缩略图宽高
$thumb_x = $thumb_y = 0;  // 宽高
$start_x = $start_y = 0;  // 起始位置

// 比较缩略图和原图的宽高比
if($thumb_b >= $src_b){
    $thumb_y = 100;
    $thumb_x = floor($thumb_y * $src_b);
    $start_x = floor((100 - $thumb_x) / 2);
} else{
    $thumb_x = 100;
    $thumb_y = floor($thumb_x / $src_b);
    $start_y = floor((100 - $thumb_y) / 2);
}

// 采样复制
imagecopyresampled($dst, $src, $start_x, $start_y, 0, 0, $thumb_x, $thumb_y, $src_info[0], $src_info[1]);

// 保存缩略图
header('Content-type:image/png');
imagepng($dst, 'dst.png');

// 销毁资源
imagedestroy($src);
imagedestroy($dst);
```

![展示](https://img-blog.csdnimg.cn/7c10bfa131aa4c4c95263e45cb4c2e01.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
函数封装：

```php
<?php

/*
 * @param  $file   要缩放的图片路径
 * @param  $width  缩放后的宽度
 * @param  $height 缩放后的高度
 * @param  $eq     是否等比缩放
 * @return [type]
 */

// 函数封装
function compress($file,$width,$height='',$eq=true){
    $img_info = getimagesize($file);
    switch ($img_info[2])
    {
        case 1:
            $image = imagecreatefromgif($file);
            break;
        case 2:
            $image = imagecreatefromjpeg($file);
            break;
        case 3:
            $image = imagecreatefrompng($file);
            break;
        default:
            die("暂不支持压缩该类型图片");
    }
    if($eq) $height = $img_info[1]*($width/$img_info[0]);
    $com_image = imagecreatetruecolor($width, $height);
    imagecopyresampled($com_image, $image, 0, 0, 0, 0, $width, $height, $img_info[0], $img_info[1]);
    header('Content-type:image/jpeg');
    imagejpeg($com_image);
    imagedestroy($com_image);
}

// 调用函数
$file = 'https://www.runoob.com/images/pulpit.jpg';
compress($file,300);
```
![展示](https://img-blog.csdnimg.cn/7228ef3fef714233a93b6a35deca712e.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
有兴趣的话还可以看看我的另外相关博客：
[GD图像处理——验证码处理](https://blog.csdn.net/tongkongyu/article/details/119154877)
[GD图像处理——水印图实现](https://blog.csdn.net/tongkongyu/article/details/119154901)
