---
title: 利用php的mail函数发送邮件
date: 2021-06-01 12:30:39
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/24.jpg
tags: 
  - 邮件
  - PHP
categories: PHP
---


```
   利用PHP的mail函数发送邮件
   mail()函数的作用：连接到邮件服务器，利用smtp协议，与该服务器交互并投邮件
   注意：
       1.mail函数不支持esmtp协议，即只能直投不能登录
       2.由上条知，我们只能直投至最终的收件服务器地址，而该地址需要在php.ini中指定
```
此处以网易邮箱为例，先找到服务器地址：
![展示](https://img-blog.csdnimg.cn/20210531195902561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
再把该地址写到php.ini中，打开php.ini，找到mail function，将其中一个地址填进去，并开启发件人设置：
![展示](https://img-blog.csdnimg.cn/20210601122616885.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

再重启apache,以管理员身份打开命令提示符:

```powershell
httpd -k restart
```
![展示](https://img-blog.csdnimg.cn/20210531200407783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)此时php.ini修改生效。现在开始使用mail函数：
语法：mail(to,subject,message,headers,parameters) 
|参数|  	描述 |
|--|--|
| to | 必需。规定 email 接收者。 |
| subject | 必需。规定 email 的主题。注释：该参数不能包含任何新行字符。 |
| message | 必需。定义要发送的消息。应使用 LF (\n) 来分隔各行。每行应该限制在 70 个字符内。 |
| headers |  	可选。规定附加的标题，比如 From、Cc 和 Bcc。应当使用 CRLF (\r\n) 分隔附加的标题。 |
| parameters | 可选。对邮件发送程序规定额外的参数。 |
以下为代码部分：

```php
<?php
$to = 'eyes7927@163.com';
$sub = 'say hello';
$msg = 'hello, php!';
$from = "From: <eyes++>";
mail($to,$sub,$msg,$from);
```
效果展示：
![展示](https://img-blog.csdnimg.cn/20210601123223421.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/20210601123257664.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

注：因为上面演示时是用了网易邮箱的服务器地址，而其不提供转发服务，因此只能给网易邮箱发邮件，如果想要给QQ邮箱发邮件，需要在php.ini设置QQ邮箱服务器地址，如果需要给任何邮箱都能发邮件，就需要安装一台匿名的smtp服务器作为中转，或许我以后会在这篇博客里更新出相关内容。如果确实有小伙伴现在就需要往任何邮箱发邮件的话，可以看看我的另一篇博客：[使用PHPMailer-master发送邮件](https://blog.csdn.net/tongkongyu/article/details/117414108?spm=1001.2014.3001.5501)，这是另一种发邮件的思路。如果想要了解一些有趣的东西，也可以看看这篇博客：[利用smtp协议实现命令行发送邮件](https://blog.csdn.net/tongkongyu/article/details/117416246?spm=1001.2014.3001.5501)，这种方式就有点涉及原理了。
