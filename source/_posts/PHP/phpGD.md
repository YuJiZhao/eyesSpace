---
title: PHP之GD图像处理
date: 2021-07-28 15:35:02
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/16.jpg
tags: 
  - 图像
  - GD库
categories: PHP
---

## 一：引入与介绍
 PHP 除了可以处理文本数据外，通过一个叫 GD 的 PHP 扩展库，PHP 还可以动态生成不同格式图像或者对已有图片进行加工处理。

**GD库是什么：** GD 库（也可以称为 GD2 函数库）是一个开源的用于创建图形图像的函数库，该函数库由C语言编写，可以在 Perl，PHP 等多种语言中使用。GD 库中提供了一系列用来处理图片的 API（接口），使用 GD 库可以处理图片、生成图片，也可以给图片加水印等。另外，很多开源项目都对 GD 库提供了很好的技术支持，如 Jpgraph 类库就是基于 GD 库开发的用于制作复杂统计图的类库。

**GD库可以做什么：** 在 PHP 中使用 GD 库可以在页面中绘制各种图形图像，以及统计图，如果与 Ajax 技术相结合还可以制作出各种强大的动态图表。还有就是在网站登陆页面中使用的验证码，也可以使用 GD 库来实现。需要注意的是，GD 库开始时是支持 GIF 格式的，但由于 GIF 使用了有版权争议的 LZW 算法，会引起法律问题，于是从 GD 库 1.6 版起所有的 GIF 支持都移除了，但是又在 GD 库 2.0.28 版起又加了回来。如果使用了二者之间版本的 GD 库时，有关 GIF 相关函数是不可用。

**开启GD库扩展：** GD 库在 PHP5 及以上的版本中是默认安装好的，但是在使用之前需要先开启 GD 库，在 Windows 系统下只需将 php.ini 配置文件中“extension=php_gd2.dll”一项前面的注释删除即可。注意：不同版本的 PHP，它们配置文件中关于 GD 库的配置项也能有所不同，比如在 php7.2 及之后的版本中 GD 库的配置项就变成了“extension=gd2”。
![展示](https://img-blog.csdnimg.cn/5b7ff3e32013469a874df52426c7b63e.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)是否引入成功可以通过phpinfo()函数查看：
![展示](https://img-blog.csdnimg.cn/0f6c8b1d402b4b94bec720236575375e.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)或者通过打印 gd_info() 函数来验证 GD 库是否安装成功：
![展示](https://img-blog.csdnimg.cn/2a1e32a4f1bb44e18e947eb95de7ab65.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
## 二：利用画布资源
在 PHP 中，通过 GD 库处理图像的操作，都是先在内存中处理，操作完成以后再以文件流的方式，输出到浏览器或保存在服务器的磁盘中。创建图像一般分为 4 个基本步骤。

+ 创建画布：所有的绘图设计都需要在一个背景图片上完成，而画布实际上就是在内存中开辟的一块临时区域，用于存储图像的信息。以后的图像操作都将基于这个背景画布，该画布的管理就类似于我们在画画时使用的画布。
+ 绘制图像：画布创建完成以后，就可以通过这个画布资源，使用各种画像函数设置图像的颜色、填充画布、画点、线段、各种几何图形，以及向图像中添加文本等。
+ 输出图像：完成整个图像的绘制以后，需要将图像以某种格式保存到服务器指定的文件中，或将图像直接输出到浏览器上显示给用户。在图像输出之前，一定要使用 header() 函数发送 Content-type 通知浏览器，这次发送的是图片不是文本。
+ 释放资源：图像被输出以后，画布中的内容也不再有用。出于节约系统资源的考虑，需要及时清除画布占用的所有内存资源。

### 1. 创建画布
1). imagecreate(宽, 高)： 创建一个空白画布(背景是白色)
2). imagecreatetruecolor(宽, 高)：创建一个真彩画布(背景色是黑色, 需要填充)
3). imagecreatefromjpeg(图片文件路径)：打开一个jpeg格式的图片资源
4). imagecreatefromgif(图片文件路径)： 打开一个gif格式图片资源（PHP中无法实现动态）
5). imagecreatefrompng(图片文件路径)： 打开png格式图片资源

 imagecreate(int $width, int $height)和imagecreatetruecolor(int $width, int $height)两个函数都可以创建一张画布，而且成功后都会返回一个资源句柄，失败则返回 FALSE。不同的是它们可以容纳的色彩范围不同，imagecreate() 创建一个基于普通调色板的图像，通常支持 256 色；而 imagecreatetruecolor() 可以创建一个真色彩图像，但是它不能用于 GIF 格式图像。

```php
echo "<pre>";

// 创建一个空白画布
$img1 = @imagecreate(100, 100) or die('图片初始化失败');
var_dump($img1);
// 创建一个真彩画布
$img2 = @imagecreatetruecolor(100, 100) or die('图片初始化失败');
var_dump($img2);

/*
 * 由于没有在画布上执行任何操作，所以浏览器不会输出画布。
 * 但是可以通过 imagesx() 和 imagesy() 来获取图像的宽和高（单位是像素）
 */
echo '画布1的宽度为：'.imagesx($img1).'像素' . '<br>';
echo '画布1的高度为：'.imagesy($img1).'像素' . '<br>';

// 从已有的jpeg图片中打开资源
$img3 = @imagecreatefromjpeg('../0.image/52.jpeg') or die('图片初始化失败');
var_dump($img3);
```

![展示](https://img-blog.csdnimg.cn/26d8ec645817414493bd6f2322e70068.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### 2. 输出图像
在 PHP 中可以使用不同的函数输出不同格式的图像，如下表所示是常用的图像输出函数。
<table>
<tbody>
<tr>
<th>
函数名</th>
<th>
描述</th>
</tr>
<tr>
<td>
imagegif()</td>
<td>
输出一个GIF格式图像到浏览器或文件</td>
</tr>
<tr>
<td>
imagejpeg()</td>
<td>
输出一个JPEG格式图像到浏览器或文件</td>
</tr>
<tr>
<td>
imagepng()&nbsp;</td>
<td>
输出一个PNG格式图像到浏览器或文件</td>
</tr>
</tbody>
</table>

语法格式如下：

```php
imagegif($image[, $filename]);  // 保存成gif格式图片

imagejpeg($image[, $filename[, $quality]]);  // 保存成jpeg格式图片

imagepng($image[, $filename]);  // 保存成png格式图片
```
其中，\$image 为创建的图像资源；\$filename 为可选参数，用来设置文件的保存路径，如果设置为 NULL，则将会直接输出原始图像流；\$quality 为可选参数，用来设置输出图片的质量，范围从 0（最差质量，文件更小）到 100（最佳质量，文件最大）。默认为 IJG 默认的质量值（大约为 75）。

```php
<?php
// header() 是用来告诉浏览器以什么形式输出图像的，不能省略。如果省略可能会出现乱码。
header('Content-type:image/png');

$image = imagecreatefromjpeg('https://www.runoob.com/images/pulpit.jpg');
imagepng($image,'php.png');
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/e5d276cf2f1e4d98913a2f82f200eb81.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

### 3. 释放资源
在图像的所有资源使用完毕后，通常需要释放图像处理所占用的内存。在 PHP 中可以通过 imagedestroy() 函数来释放图像资源, 另外，释放图像资源的操作是没有输出内容的，其语法格式如下所示：

```php
imagedestroy($image);
```

### 4. 绘制图像
**1). 分配颜色 ：**
在真彩图片资源中，所有分配的颜色都不会自动给图片资源上色，是用来后续操作图片资源的时候指定着色的。但是如果当前使用的imagecreate创建的图片资源，那么第一个分配的颜色会自动被着色为图片背景色。需要注意的是，凡是给图片上增加内容，基本都要分配颜色(每一个操作图片的函数之前，都需要先调用分配颜色的函数得到一个颜色)

**imagecolorallocate() 函数**
imagecolorallocate() 函数可以为一个图像资源分配颜色，如果在图像中需要设置多种颜色，只要多次调用该函数即可。函数的语法格式如下所示：
```php
 imagecolorallocate($image, $red, $green, $blue);
```

其中，\$image 为要设置颜色的图像资源，imagecolorallocate() 函数会返回一个标识符，代表了由给定的 RGB 成分组成的颜色；\$red，\$green 和\$blue 分别是所需要的颜色的红，绿，蓝成分，取值范围是 0 到 255 的整数或者十六进制的 0x00 到 0xFF。

**imagecolorallocatealpha() 函数**
imagecolorallocatealpha() 函数的作用和 imagecolorallocate() 相同，但多了一个额外的设置透明度的参数 alpha，函数的语法格式如下：

```php
imagecolorallocatealpha($image, $red, $green, $blue, $alpha);
```
其中，\$image 为要设置颜色的图像资源；\$red，\$green 和 \$blue 分别是所需要的颜色的红，绿，蓝成分，取值范围是 0 到 255 的整数或者十六进制的 0x00 到 0xFF；\$alpha 用来设置颜色的透明的，取值范围在 0 到 127 之间，0 表示完全不透明，127 则表示完全透明。

**2). 填充区域 ：**
区域填充不可以用来绘制图像，但它可以将一个已存在图像的颜色替换为其它颜色。在 PHP 中通过 imagefill() 函数来执行区域填充，它的语法格式如下所示：

```php
imagefill($image, $x, $y, $color);
```
其中，\$image 为创建的图像资源；\$x 和 \$y 为要设置颜色的横纵坐标；\$color 为要设置的颜色。

imagefill()的填充逻辑：从指定点开始自动匹配相邻点，如果颜色一致，则自动渲染，扩展到全图。

**3). 画点和直线 ：**
画点和线是绘制图像中最基本的操作，如果灵活使用，可以通过它们绘制出千变万化的图像。在 PHP 中，使用 imagesetpixel() 函数在画布中绘制一个单一像素的点，并且可以设置点的颜色，函数的语法格式如下：

```php
imagesetpixel($image, $x, $y, $color);
```
该函数可以在第一个参数 \$image 提供的画布中，在（\$x，\$y）的坐标位置上，绘制一个颜色为 \$color 的一个像素点。在实际开发中还可以通过循环和随机数的结合来绘制更多的像素点。

如果需要绘制一条线段，则可以使用 imageline() 函数，其语法格式如下：

```php
imageline($image, $x1, $y1, $x2, $y2, $color);
```
我们都知道两个点可以确定一条线段，所以该函数可以在 \$image 提供的画布中，从坐标（\$x1，\$y1）到坐标（\$x2，\$y2）绘制一条颜色为 \$color 的线段。

**4). 画矩形 ：**
在 PHP 中我们可以使用 imagerectangle() 或者 imagefilledrectangle() 函数来绘制一个矩形，与 imagerectangle() 函数不同的是 imagefilledrectangle() 函数会在绘制完成矩形后填充矩形，它们的语法格式如下所示：

```php
imagerectangle($image, $x1, $y1, $x2, $y2, $color);  // $color是边线颜色

imagefilledrectangle($image, $x1, $y1, $x2, $y2, $color);  // $color是矩阵填充色
```
这两个函数的功能类似，都是在 \$image 画布中画一个矩形，矩形的左上角坐标为（\$x1，\$y1），右下角坐标为（\$x2，\$y2）。

**5). 画多边形 ：**
在 PHP 中可以使用 imagepolygon() 函数来绘制一个多边形；也可以使用 imagefilledpolygon() 来绘制并填充一个多边形，它们的语法格式如下所示：

```php
imagepolygon($image, $points, $num_points, $color);  // $color是边线颜色

imagefilledpolygon($image, $points, $num_points, $color);  // $color是多边形填充色
```
这两个函数都是可以在画布\$image 中画一个多边形；
第二个参数\$points 是一个数组，包含了多边形的各个顶点坐标，例如 \$points[0]=x0，\$points[1]=y0，\$points[2]=x1，\$points[3]=y1，依此类推
第三个参数 \$num_points 用来设置多边形的顶点数，必须大于 3
需要注意的是，\$points 数组中的顶点坐标数（坐标是成对出现的）不得小于多边形的顶点数 \$num_points。

**6). 画圆弧 ：**
在 PHP 中可以使用 imagearc() 函数来画出一条弧线或者圆形，也可以使用 imagefilledarc() 函数来绘制弧线或者圆形并填充，它们的语法格式如下所示：

```php
imagearc($image, $cx, $cy, $width, $height, $start, $end, $color);

imagefilledarc($image, $cx, $cy, $width, $height, $start, $end, $color, $style);
```
这两个函数都可以在画布 \$image 上绘制一个椭圆弧，其中 \$cx 和 \$cy 分别为圆弧中心点的横纵坐标；\$width 和 \$height 分别为圆弧的宽度和高度；\$start 和 \$end 分别代表圆弧的起点角度和终点角度，0° 为钟表 3 点钟的位置，以顺时针方向递增；至于 \$color 参数，imagearc() 用来表示圆弧的线条颜色，而 imagefilledarc() 用来表示弧线区域的填充色；

imagefilledarc() 函数比 imagearc() 函数多了一个 $style 参数，它用来设置颜色的填充类型，可以是如下的值：
+ IMG_ARC_PIE：普通填充，产生圆形边界；
+ IMG_ARC_CHORD：只使用直线连接起点和终点，与 IMG_ARC_PIE 互斥；
+ IMG_ARC_NOFILL：指明弧或弦只有轮廓，不填充；
+ IMG_ARC_EDGED：用直线将起始和结束点与中心点相连，和 IMG_ARC_NOFILL 一起使用是画饼状图轮廓的好方法。


**7). 在画布上写字 ：**
想要在图像中显示文字也需要按坐标位置画上去。在 PHP 中不仅支持多种的字体库，还提供了非常灵活的文字绘制方法。例如，在图像中绘制缩放、倾斜、旋转的文字等。常用的绘制文字的函数如下表所示：
<table>
<tbody>
<tr>
<th>
函数名&nbsp;</th>
<th>
描述</th>
</tr>
<tr>
<td>
imagestring()</td>
<td>
水平绘制一行字符串</td>
</tr>
<tr>
<td>
imagestringup()</td>
<td>
垂直绘制一行字符串</td>
</tr>
<tr>
<td>
imagechar()</td>
<td>
水平绘制一个字符</td>
</tr>
<tr>
<td>
imagecharup()</td>
<td>
垂直绘制一个字符</td>
</tr>
<tr>
<td>
imagettftext()</td>
<td>
用 TrueType 字体向图像中写入文本</td>
</tr>
</tbody>
</table>

虽然这几个函数的功能有所差异，但调用方式是类似的，尤其是 imagestring()、imagestringup()、imagechar() 以及 imagecharup() 函数，它们的参数都是相同的，因此就不再分开介绍了，这些函数的语法格式如下：

```php
imagestring($image, $font, $x, $y, $s, $color);

imagestringup($image, $font, $x, $y, $s, $col);

imagechar($image, $font, $x, $y, $c, $color);

imagecharup($image, $font, $x, $y, $c, $color);
```
使用这些函数可以在画布 \$image 上，坐标为（\$x，$y）的位置，绘制字符串（或字符）\$s，字符串的颜色为 \$color，字体为 \$font。如果 \$font 是 1，2，3，4 或 5，则使用内置字体。

而imagettftext需要特殊说明：它可以使用 TrueType 字体（Windows 系统中扩展名为 .ttf 格式的字体）向图像中写入文本。

语法格式如下：
```php
imagettftext($image, $size, $angle, $x, $y, $color, $fontfile, $text)
```

参数说明如下：
+ \$image：由图像创建函数（例如 imagecreatetruecolor()）返回的图像资源；
+ \$size：字体的尺寸；
+ \$angle：角度制表示的角度，0 度为从左向右读的文本，数值越高则表示将文本进行逆时针旋转。例如 90 度表示从下向上读的文本；
+ \$x、$y：表示文本中第一个字符的坐标点（大概是字符左下角的位置）；
+ \$color：用来设置文本的颜色；
+ \$fontfile：是要使用的 TrueType 字体文件的路径；
+ \$text：UTF-8 编码的文本字符串。

## 三：获得图片信息
1). 获得图片尺寸
```php
getimagesize($image);
```
getimagesize() 函数将测定任何 GIF，JPG，PNG，SWF，SWC，PSD，TIFF，BMP，IFF，JP2，JPX，JB2，JPC，XBM 或 WBMP 图像文件的大小并返回图像的尺寸以及文件类型和一个可以用于普通 HTML 文件中 IMG 标记中的 height/width 文本字符串。

2). 获得画布尺寸

```php
imagex($image);  // 获得图片宽度，单位为像素

imagey($image);  // 获得图片高度，单位为像素
```
实例：
```php
<?php
// 定义图片
$image = 'https://www.runoob.com/images/pulpit.jpg';

// 获得图片全部信息
echo '<pre>';
$info = getimagesize($image);
var_dump($info);

// 打开图片资源
$img = imagecreatefromjpeg($image);

// 获得图片长宽信息
echo imagesx($img) . '<br>';
echo imagesy($img) . '<br>';
```

![展示](https://img-blog.csdnimg.cn/b3506e23e2804373a83e43bc4c258d6b.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
getimagesize($image)内容解读：

```php
array(7) {
  [0]=>
  int(304)
  [1]=>
  int(228)
  [2]=>
  int(2)
  [3]=>
  string(24) "width="304" height="228""
  ["bits"]=>
  int(8)
  ["channels"]=>
  int(3)
  ["mime"]=>
  string(10) "image/jpeg"
}
```

+ array(0)：  int型图片宽度

+ array(1)：  int型图片高度

+ array(2)： 图像类型的标记：1 = GIF，2 = JPG，3 =PNG，4 = SWF，5 = PSD，6 = BMP，7 = TIFF(intel byte order)，8 =TIFF(motorola byte order)，9 = JPC，10 = JP2，11 = JPX，12 =JB2，13 = SWC，14 = IFF，15 = WBMP，16 = XBM。这些标记与 PHP 4.3.0 新加的 IMAGETYPE 常量对应。
+ array(3)： 文本字符串，内容为"height="yyy"width="xxx""，可直接用于 IMG 标记。 
+ array("bits")：代表每种颜色的位数。
+ array("channels") ：代表图片色系，对于 RGB 图像其值为 3，对于 CMYK 图像其值为 4
+ array("mime") ：自 PHP 4.3.0 起被引入，代表符合该图像的 MIME 类型。此信息可以用来在 HTTP Content-type 头信息中发送正确的信息。

对于GD库的实际运用，我还写了三篇博客，有兴趣可以看看：
[GD图像处理——验证码处理](https://blog.csdn.net/tongkongyu/article/details/119154877)
[GD图像处理——水印图实现](https://blog.csdn.net/tongkongyu/article/details/119154901)
[GD图像处理——缩略图的实现](https://blog.csdn.net/tongkongyu/article/details/119154890)