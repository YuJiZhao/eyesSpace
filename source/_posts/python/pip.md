---
title: python的库文件管理(pip的使用)
date: 2021-07-06 17:58:08
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/9.jpg
tags: 
  - pip
  - 文件
categories: python
---

## 命令行中python的进入与退出
1：python指令
安装完后会出现python命令，在命令行工具内输入后可显示所安装的python的相关信息，并进行python操作。
![展示](https://img-blog.csdnimg.cn/20210706160045565.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)2: exit() 与 ctrl+z 指令
输入python后进入python模式，可以通过上述两个指令退出python。
![展示](https://img-blog.csdnimg.cn/20210706160746821.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
## 库文件管理——pip
pip就是 Python标准库中的一个包，这个包比较特殊，用它可以来管理Python标准库中其他的包。pip支持从PyPI（https://pypi.org/），版本控制，本地项目以及直接从分发文件进行安装。pip是一个命令行程序。安装pip后，会向系统添加一个pip命令，该命令可以从命令提示符运行。目前，pip 是The Python Packaging Authority (PyPA) 推荐的 Python 包管理工具！
python本身的标准库很优秀，但开发中时常需要使用其他的库，比如wxPython、Twisted和Python图像库等等，此时就需要使用pip指令进行安装。

这是Python标准库：
![展示](https://img-blog.csdnimg.cn/20210706161356227.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)这是pip指令运行结果：
![展示](https://img-blog.csdnimg.cn/20210706161034442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)从中可以看出pip的使用语法：
```python
pip <command> [options]
```
以及pip命令：
![展示](https://img-blog.csdnimg.cn/20210706163049332.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
#### 1.安装包
直接使用install操作时，程序默认会从python官网的库里下载，因为那是国外网站，所以下载极为缓慢。

下图是在默认的官网的库里下载，很慢。
![展示](https://img-blog.csdnimg.cn/20210706173735891.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

因此推荐从国内的库中下载，国内可用的源如下：(其中清华大学的最好，因为清华大学的pip源是官网pypi的镜像，每隔5分钟同步一次)
```
清华大学：https://pypi.tuna.tsinghua.edu.cn/simple  
阿里云：http://mirrors.aliyun.com/pypi/simple/
中国科技大学 https://pypi.mirrors.ustc.edu.cn/simple/
华中理工大学：http://pypi.hustunique.com/
山东理工大学：http://pypi.sdutlinux.org/
豆瓣：http://pypi.douban.com/simple/
```

永久修改pip镜像：直接在C盘的user目录中创建一个pip目录，如：C:\Users\xx\pip，并新建文件pip.ini文件，pip文件内容如下：
```
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
[install]
trusted-host=pypi.tuna.tsinghua.edu.cn
```
![展示](https://img-blog.csdnimg.cn/20210706174724114.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

还有临时使用其他镜像的方法，就是直接加参数 -i [镜像地址]，比如下面指令：

```python
pip install requests -i https://pypi.tuna.tsinghua.edu.cn/simple
```
![展示](https://img-blog.csdnimg.cn/20210706171958414.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)下载完后的包可以在Lib文件夹下的site-packages文件夹下找到。
![展示](https://img-blog.csdnimg.cn/20210706174931534.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
#### 2.更新pip

```python
python -m pip install --upgrade pip
```
```python
pip install --upgrade pip
```

#### 3.更新包

```python
pip install --upgrade package_name
```

#### 4.删除包

```python
pip uninstall package_name
```
#### 5.查看已安装的包

```python
pip list
```
#### 6.查看可升级的包

```python
pip list --outdated
```
#### 7.查看包的具体信息

```python
pip show package_name
```
#### 8.搜索包

```python
pip search keyword
```
