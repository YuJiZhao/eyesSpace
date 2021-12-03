---
title: python之pil的使用
date:  2021-10-13 15:48:51
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/104.jpg
tags:
  - pil
categories: python
---

# 一：PIL功能介绍与安装
PIL，全称Python Image Library，主要作用是图像处理，可用于图片剪切、粘贴、缩放、镜像、水印、颜色块、滤镜、图像格式转换、色场空间转换、验证码、旋转图像、图像增强、直方图处理、插值和滤波等功能。不过只支持到Python 2.7。Pillow是PIL的一个派生分支，但如今已经发展成为比PIL本身更具活力的图像处理库。我们需要安装的就是Pillow。

PIL具体用途：
+ 图像归档(Image Archives)。PIL非常适合于图像归档以及图像的批处理任务。你可以使用PIL创建缩略图，转换图像格式，打印图像等等。
+ 图像展示(Image Display)。PIL较新的版本支持包括Tk PhotoImage，BitmapImage还有Windows DIB等接口。PIL支持众多的GUI框架接口，可以用于图像展示。
+ 图像处理(Image Processing)。PIL包括了基础的图像处理函数，包括对点的处理，使用众多的卷积核(convolution kernels)做过滤(filter),还有颜色空间的转换。PIL库同样支持图像的大小转换，图像旋转，以及任意的仿射变换。PIL还有一些直方图的方法，允许你展示图像的一些统计特性。这个可以用来实现图像的自动对比度增强，还有全局的统计分析等。


```shell
pip install Pillow
```
貌似Pillow是默认安装好了的，可以通过`pip list`查看是否已经安装
# 二：PIL的基本操作
以下是打开图片保存图片的基本操作。
```python
from PIL import Image

# 1.打开图片
img = Image.open("image/10.png")
# 2.显示图片(执行显示图片后线程会中断，关闭图像后恢复)
img.show()
# 3.保存图片
img.save("image/xiaomai.png")
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/d83056bbdef3458e8e61149965e1b595.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)我在尝试的过程中发现，png格式的图片保存为jpg格式时会报错：`OSError: cannot write mode RGBA as JPEG`，经过查阅资料发现错误原因主要是因为PNG格式和JPG格式图片的通道数不同。
+ PNG是四通道：RGBA意思是红色，绿色，蓝色，Alpha的色彩空间，Alpha指透明度
+ JPG是三通道：RGB意思是红色，绿色，蓝色

所以，PNG格式图片要保存成JPG格式就要丢弃A通道：

```python
from PIL import Image

# 1.打开图片
img = Image.open("image/10.png")
# 2.显示图片(执行显示图片后线程会中断，关闭图像后恢复)
img.show()
# 3.保存图片
img = img.convert("RGB")
img.save("image/xiaomai.jpg")
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f4f2f1dfbcb74f81bd4c5946be761c30.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)下面是图片旋转的代码：

```python
from PIL import Image

# 1.打开图片
img = Image.open("pli/7.png")
# 2.水平翻转
img1 = img.transpose(Image.FLIP_LEFT_RIGHT)
# 3.保存图片
img1.save("pli/1.png")
# 4.垂直翻转
img2 = img.rotate(180)
# 5.保存照片
img2.save("pli/2.png")
# 6.水平+垂直翻转
img3 = img.transpose(Image.FLIP_LEFT_RIGHT).rotate(180)
# 7.保存图片
img3.save("pli/3.png")
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/319f05af4b6f40b29162322c98ef1591.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 三：给图片增加文字
要在图像上绘制文本要用到 PIL 的两个模块：ImageDraw和ImageFont。 ImageDraw 用于创建绘图对象，ImageFont用于加载字体。
```python
from PIL import Image, ImageDraw, ImageFont
# 1.打开图片
img = Image.open("image/10.png")
# 2.调用画图模块
draw = ImageDraw.Draw(img)
# 3.设置字体
tfont = ImageFont.truetype("萌神手写体.ttf", 24)
# 4.添加文字
"""
    参数一：文字在图片的位置：(x, y)
    参数二：文字内容
    参数三：字体颜色，当然颜色也可以用RGB值指定
    参数四：字体类型
"""
draw.text((50, 30), "eyes++", fill="green", font=tfont)
# 5.保存图片
img.save("image/addWord.png")
# 6.显示图片
img.show()
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/5f604d0a2e7b42c3958fb6744d5f8798.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 四：PIL滤镜功能

```python
from PIL import Image, ImageFilter
img = Image.open("image/10.png")
img = img.filter(ImageFilter.CONTOUR)
img.save("image/Filter.png")
img.show()
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/a9979bfb20d34e4e9b02459e4f6c91ff.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
滤镜类型如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/56911c96b013459587f4b98f0d53e2c0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 五：PIL镜像功能

```python
from PIL import Image
img = Image.open("image/10.png")
img = img.transpose(Image.FLIP_LEFT_RIGHT)
img.save("image/mirror.png")
```
![在这里插入代码片](https://img-blog.csdnimg.cn/59ec6229539248249ce6ce66e4fff7d8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)transpose有这么几种模式
+ FLIP_LEFT_RIGHT：左右镜像
+ FLIP_TOP_BOTTOM：上下镜像
+ ROTATE_90：逆时针转90度
+ ROTATE_180：逆时针转180度
+ ROTATE_270：逆时针转270度
+ TRANSPOSE：像素矩阵转置
+ TRANSVERSE

最后一种模式我也不知道什么意思，也没查到，但效果是下面这样的，盲猜是对角线对转。。。。。
![在这里插入图片描述](https://img-blog.csdnimg.cn/5536425799ce459b8064d819fdc95040.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)除了使用transpose制造镜像，使用rotate也可以的，不过rotate只能旋转：
![在这里插入图片描述](https://img-blog.csdnimg.cn/072c42190bcc449db7d957d626624452.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
发现这样旋转会有棱角，然后经过某位不愿透露姓名的大佬指点，发现是rotate旋转只是像素旋转，画布不动，于是我写下了以下测试代码：

```python
from PIL import Image

img = Image.open("image/12.jpg")
img1 = img.transpose(Image.ROTATE_90)
img1.save("image/test.jpg")
img2 = img.rotate(90)
img2.save("image/test2.jpg")
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f7a93ce1579b47c9ad12b86bd04cc9c9.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)可以发现小埋的rotate旋转不会自动填充空白像素，而血小板的会自动黑色填充。是因为png格式是非失真性压缩的，允许使用类似于GIF格式的调色板技术，支持真彩色图像，并具备阿尔法通道（半透明）等特性。而jpg格式没有阿尔法通道，因此jpg格式图片不能为透明，png格式可以。

# 六：图片拼接功能
虽说是拼接，但更像是将两幅图粘贴到一个新的画布上
```python
from PIL import Image, ImageDraw

# 打开图片
img1 = Image.open("image/10.png")
img2 = Image.open("image/addWord.png")
# 查看图片尺寸，便于拼接图片
print(img1.size)
print(img1.size)
# 新建空白图片,三个参数分别是模式(RGB/RGBA)、大小、颜色
newimg = Image.new(mode="RGB", size=(1174, 614), color=(255, 100, 50))
# 拼接图片,第一个参数是图片，第二个是起始位置
newimg.paste(img1, (0, 0))
newimg.paste(img2, (587, 0))
newimg.show()
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/658ad834e7344305a17d1c297f8471d3.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 七：PIL裁剪功能
图片裁剪用到的方法是image.crop()，这个方法能从图像中提取出某个矩形大小的图像。它接收一个四元素的元组作为参数，各元素为（left, upper, right, lower），坐标系统的原点（0, 0）是左上角。

```python
from PIL import Image
img = Image.open("image/10.png")
print(img.size)
imgCut = img.crop((100, 200, 500, 600))
imgCut.show()
```
在这里插入代码片![在这里插入图片描述](https://img-blog.csdnimg.cn/4c01cf541c6749c5a1a88ff428db5e61.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 八：图片缩放
可能这么看不是二分之一，但这是显示问题，可以看数据：

```python
from PIL import Image

# :
img = Image.open('image/10.png')
# 获得图像尺寸:
w, h = img.size
# 缩放到50%:
img.thumbnail((w//2, h//2))
# 把缩放后的图像用jpeg格式保存:
img.save('image/zoom.png')
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/36cc575c57ac4f9c908eb0baabc9df14.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

