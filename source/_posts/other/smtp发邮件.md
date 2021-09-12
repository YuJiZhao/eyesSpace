---
title: 利用smtp协议实现命令行发送邮件
date: 2021-05-31 15:44:07
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/25.jpg
tags: 
  - smtp协议
  - 命令行
  - 邮件
categories: 其他
---

事实上只要知道邮件准确服务器的地址，即使没有邮箱也能给它发邮件，这里使用smtp协议演示使用电脑黑框发邮件。
先介绍一下smtp协议，smtp协议是简单邮件传输协议，在本例中相当于中转服务器。

## 任务
用黑框给eyes7927@163.com发送邮件。
## 思路
   1. 找到这个email储存邮件的准确服务器
   2. 连接该服务器，用smtp协议与之对话 
## 步骤
先找到这个email储存邮件的准确服务器，使用nslookup命令，nslookup命令用于查询DNS的记录，查看域名解析是否正常，在网络故障的时候用来诊断网络问题。

这个指令是查询网页服务器地址，而不是邮箱服务器地址，故错误。
```powershell
nslookup 163.com
```

![展示](https://img-blog.csdnimg.cn/20210531143925418.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)以下是正确指令：

```powershell
nslookup -q=mx 163.com
```

![展示](https://img-blog.csdnimg.cn/20210531144106280.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)上面四个地址都是邮箱服务器地址，任选一个使用，以第一个为例。

```powershell
163mx01.mxmail.netease.com
```

使用telnet远程连接该邮箱服务器(25是端口号)：

```powershell
telnet 163mx01.mxmail.netease.com 25
```
![展示](https://img-blog.csdnimg.cn/20210531144832617.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)连接成功后出现下列现象
![展示](https://img-blog.csdnimg.cn/20210531144330216.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)用helo打个招呼，注意是helo不是hello
![展示](https://img-blog.csdnimg.cn/20210531145140753.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)以下为发送邮件的详细步骤，由于我在尝试发邮件时连接不稳定多次与163邮箱服务器断开连接，便以燕十八老师的成功案例截图，并加上注释。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210531152645388.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/20210531152721951.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
## 总结：
个人认为这种方法了解就好，因为与服务器连接时很容易断开连接，用这种方法发邮件十分耗耐心。
如果确实有发邮件相关的开发需要的话，可以看看我的另一篇博客：[使用PHPMailer-master发邮件](https://blog.csdn.net/tongkongyu/article/details/117414108?spm=1001.2014.3001.5501)，这种方法可以给任何邮箱发邮件，有兴趣的话还可以看看这一篇：[使用PHP的mail()函数发邮件](https://blog.csdn.net/tongkongyu/article/details/117427268?spm=1001.2014.3001.5501)，不过这篇博客里的方法如果要给任何邮箱发邮件的话需要一台匿名的邮箱服务器。

