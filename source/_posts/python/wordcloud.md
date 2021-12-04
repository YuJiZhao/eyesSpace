---
title: wordcloud绘制词云彩
date: 2021-10-11 15:09:11
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/59.jpg
tags:
  - wordcloud
categories: python
---


# 一：wordcloud介绍
wordcloud库可以说是python非常优秀的词云展示第三方库。词云以词语为基本单位更加直观和艺术的展示文本，wordcloud库是基于numpy和pillow这两个内置库的。官网地址：[wordcloud](https://pypi.org/project/wordcloud/)，github地址：[word_cloud](https://github.com/amueller/word_cloud)。
![在这里插入图片描述](https://img-blog.csdnimg.cn/15dbb600fa0d46b69f92d61db2e9ecb5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 二：wordcloud安装
可以直接使用pip安装：

```shell
pip install wordcloud
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/0ac12dc26b9f499dbc1b2369088499e9.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)我应该算是幸运的吧，我看网上一堆人都是说第一次装绝对会出错，我这次装虽然没有报错，但也有一堆warning，感觉报错的主流解法都是直接下载whl文件安装，如果安装出错的话这里推荐一个安装教程：[wordcloud安装方法（Windows10）](https://blog.csdn.net/weixin_45231460/article/details/104273699)

# 三：wordcloud的使用
我们可以通过wordcloud方法来生成我们的词云对象。我们在定义对象的时候也能生成我们的对象的参数定义：
+ **width**：图像的宽度，默认为400px
+ **height**：图像的高度，默认为200px
+ **min_font_size**：最小字号，默认为4px
+ **max_font_size**：最大字号，默认不做限制
+ **margin**：画布边缘留白的空隙，默认留白空间是2px
+ **font_step**：字体步进，系统会根据词出现的次数来定词的大小，次数多的和次数少的之间的字号差距就是步进间隔。默认为1
+ **font_path**：展示字体的路径
+ **prefer_horizontal**： 词语水平方向排版出现的频率，默认 0.9 ,所以词语垂直方向排版出现频率为 0.1 
+ **scale**：在字段width和height乘以的倍数，最终呈现的画布尺寸以这个结果。默认是1，此方法适合需要呈现大尺寸的画布
+ **max_words**：词云最大单词数量，默认为200
+ **stop_words**：不显示词语列表，采用集合形式：stop_words={" “,” “,” "}
+ **background_color**：背景颜色，默认为黑色
+ **normalize_plurals**：bool类型，是否去掉单词末尾的s，默认去掉
+ **repeat**：bool类型，单词是否重复展示，默认不重复

除了上面这些参数，wordcloud还有一个特殊的参数，就是词云的形状，wordcloud的形状我们必须引入另外一个库imageio，通过imageio我们可以将目前的图片加载成元单元。

```shell
pip install imageio
```
引入完成后，我们可以设置词云的形状，参数为 **mask**

wordcloud的一些方法：
<table style="border-width:2px;border-style:solid;" border="2" align="center"><tbody><tr><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;方法名　　</td><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;参数</td><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;返回值</td><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;备注</td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><code class="descname">fit_words</code><span class="sig-paren">(<em>frequencies</em><span class="sig-paren">)</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><strong>frequencies</strong><span class="classifier-delimiter">:<span class="classifier">dict from string to float</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;">self　</span></td><td style="border-color:#000000;border-style:solid;border-width:1px;" rowspan="2"><span style="font-size:14px;">根据单词及其频率生成词云</span></td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"> <p><span style="font-size:14px;"><code class="descname">generate_from_frequencies</code></span></p> <p><span style="font-size:14px;"><span class="sig-paren">(<em>frequencies</em>,&nbsp;<em>max_font_size=None</em><span class="sig-paren">)</span></span></span></p> </td><td style="border-color:#000000;border-style:solid;border-width:1px;"> <p><span style="font-size:14px;"><strong>frequencies</strong><span class="classifier-delimiter">:<span class="classifier">dict from string to float</span></span></span></p> <p><span class="classifier-delimiter" style="font-size:14px;"><span class="classifier"><strong>max_font_size</strong><span class="classifier-delimiter">:<span class="classifier">int</span></span></span></span></p> </td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;">self</span></td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><code class="descname">generate</code><span class="sig-paren">(<em>text</em><span class="sig-paren">)</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><strong>text</strong><span class="classifier-delimiter">:<span class="classifier">string</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;">self</span></td><td style="border-color:#000000;border-style:solid;border-width:1px;" rowspan="2"><span style="font-size:14px;">根据文本生成词云，是方法generate_from_text的别称。输入的文本应该是一个自然文本。若输入的是已排列好的单词，那么单词会出现两次，可以设置参数collocations=False去除此单词重复。调用process_text和genereate_from_frequences</span></td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><code class="descname">generate_from_text</code><span class="sig-paren">(<em>text</em><span class="sig-paren">)</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><strong>text</strong><span class="classifier-delimiter">:<span class="classifier">string</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;">self</span></td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><code class="descname">process_text</code><span class="sig-paren">(<em>text</em><span class="sig-paren">)</span></span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><strong>text</strong><span class="classifier-delimiter">:<span class="classifier">string</span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;">words<span class="classifier-delimiter">:<span class="classifier">dict (string, int)</span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;">将一长段文本切片成单词，并去除stopwords。返回单词（words）和其出现次数的字典格式</span></td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><span style="font-size:14px;"><code class="descname"><code class="descname">recolor</code><span class="sig-paren">(<em>random_state=None</em>,&nbsp;<em>color_func=None</em>,&nbsp;<em>colormap=None</em><span class="sig-paren">)</span></span></code></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;"> <p><strong><strong>random_state</strong><span class="classifier-delimiter">:</span></strong><span class="classifier-delimiter"><span class="classifier">RandomState, int, or None, default=None</span></span></p> <p><strong><span class="classifier-delimiter"><span class="classifier"><strong>color_func</strong><span class="classifier-delimiter">:</span></span></span></strong><span class="classifier-delimiter"><span class="classifier"><span class="classifier-delimiter"><span class="classifier">function or None, default=None</span></span></span></span></p> <p><strong><span class="classifier-delimiter"><span class="classifier"><span class="classifier-delimiter"><span class="classifier"><strong>colormap</strong><span class="classifier-delimiter">:</span></span></span></span></span></strong><span class="classifier-delimiter"><span class="classifier"><span class="classifier-delimiter"><span class="classifier"><span class="classifier-delimiter"><span class="classifier">string or matplotlib colormap, default=None</span></span></span></span></span></span></p> </td><td style="border-color:#000000;border-style:solid;border-width:1px;">self</td><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;</td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><code class="descname">to_array</code><span class="sig-paren">(<span class="sig-paren">)</span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;">&nbsp;</td><td style="border-color:#000000;border-style:solid;border-width:1px;">image<span class="classifier-delimiter">:<span class="classifier">nd-array size (width, height, 3)</span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;">转换成numpy array</td></tr><tr><td style="border-color:#000000;border-style:solid;border-width:1px;"><code class="descname"><code class="descname">to_file</code><span class="sig-paren">(<em>filename</em><span class="sig-paren">)</span></span></code></td><td style="border-color:#000000;border-style:solid;border-width:1px;"><strong>filename</strong><span class="classifier-delimiter">:<span class="classifier">string</span></span></td><td style="border-color:#000000;border-style:solid;border-width:1px;">self</td><td style="border-color:#000000;border-style:solid;border-width:1px;">保存图片文件</td></tr></tbody></table>

需要注意的是，使用generate加载词云文本时，其中txt的内容需要以空格来分隔单词，并且对于英文单词而言，如果单词长度为1-2，系统会自动过滤。

效果如下：

```python
import wordcloud, imageio

# 准备字符串
str = """If El Niños were dangerous before,they are looking to become especially destructive in the near future. 
Already severe and unpredictable,recent research indicates these natural weather events are now swinging to even 
greater extremes. Since humans started burning fossil fuels on an industrial scale,coral records from the past 7,
000 years indicate that heat waves,wildfires,droughts,flooding and violent storms associated with El Niño have grown 
markedly worse. """

# 准备词云模板
mask = imageio.imread("wordcloud/template.png")
# 实例化一个wordcloud对象
wc = wordcloud.WordCloud(width=400, height=400, min_font_size=5, max_font_size=50, font_step=2, max_words=500, mask=mask)
# 加载词云文本
wc.generate(str)
# 输出图片
wc.to_file("wordcloud/word.png")
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/651bad50a9dd49f18a30412a748bf472.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)![在这里插入图片描述](https://img-blog.csdnimg.cn/63a64f4e4b0b4bc18f1f07fd417649ba.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 四：wordcloud案例
题目：wordcloud 是优秀的词云展示第三方库，它以以词语为基本单位，更加直观和艺术地展示文本，请根据附件文件（校长 2018.txt、校长 2018 毕业讲话.txt），分别绘制词云彩，可自行设定背
景或背景图片。

```python
import wordcloud, imageio

# 准备字符串
f1 = open("校长2018.txt", "r+", encoding="utf-8")
f2 = open("校长2018毕业讲话.txt", "r+", encoding="utf-8")
text1 = f1.read()
text2 = f2.read()
# 准备词云模板
mask = imageio.imread("wordcloud/template.png")
# 实例化一个wordcloud对象
wc = wordcloud.WordCloud(width=400, height=400, min_font_size=5, max_font_size=50, font_step=2, max_words=500, mask=mask)
# 加载词云文本
wc.generate(text1 + text2)
# 输出图片
wc.to_file("wordcloud/word.png")

print(text1+text2)
f1.close()
f2.close()
```
我在运行的时候发现，没有报错，但结果却是这样的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/9c3479c4c6b44dd3ab60b976fed72a4e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
经过查阅资料后找到原因，wordcloud默认字体是DroidSansMono.ttf，这个字体不支持中文格式，因此需要更换字体，找到wordcloud.py文件，打开后更改：

![在这里插入图片描述](https://img-blog.csdnimg.cn/c81ca19866534286bde2c55dcb805b86.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
我这里是改成了微软雅黑，同时还需要把对应的字体放在wordcloud.py同目录：
![在这里插入图片描述](https://img-blog.csdnimg.cn/21499c3ec91941d2be31ea23eca63057.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
然后就顺利完成了：
![在这里插入图片描述](https://img-blog.csdnimg.cn/29a21db25f8046859325d39cc370a8b8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

