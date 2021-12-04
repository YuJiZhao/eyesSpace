---
title: PHP会话技术(cookie与session)详解
date: 2021-07-26 01:47:31
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/66.jpg
tags: 
  - cookie
  - session
  - 会话
categories: PHP
---

## 一：会话技术初步认识
**会话技术介绍：** web会话可以简单理解为：用户打开一个浏览器，访问某一个web站点，在这个站点点击多个超链接，访问服务器多个web资源，然后关闭浏览器，整个过程称之为一个会话。

HTTP协议的特点是无状态/无连接，当一个浏览器连续多次请求同一个web服务器时，服务器是无法区分多个操作是否来自于同一个浏览器(用户)。会话技术就是通过HTTP协议想办法让服务器能够识别来自同一个浏览器的多次请求，从而方便浏览器(用户)在访问同一个网站的多次操作中，能够持续进行而不需要进行额外的身份验证。

**会话技术分类：**
1). cookie技术
cookie技术是在HTTP协议下，服务器或脚本可以维持客户工作站上信息的一种方式，cookie是由Web服务器保存在用户浏览器(客户端)上的小文本文件(HTTP协议响应头)，它可以包含有关用户的信息。无论何时用户链接到服务器，Web站点都可以访问cookie信息。

2). session技术
session直接翻译成中文比较困难，一般都翻译成时域，在计算机专业术语中，session是指一个终端用户与交互系统进行通信的时间间隔，通常指从注册进入系统到注销退出系统之间所经历的时间。以及如果需要的话，可能还有一定的操作空间。session技术是将数据保存到服务器端，无论何时用户链接到服务器，Web站点都可以访问session信息。session技术的实现是依赖cookie技术的。

3). 两种会话技术的区别
a). 安全性方面：
session存储在服务器端，安全性高
cookie存储在浏览器端，安全性低
b). 数据大小方面
session数据存储不限
cookie的数量和大小都有限制(一个网站最多20个cookie，长度最多4k)
c). 可用数据类型
session 可以存复杂数据(自动序列化)
cookie只能存储简单数据，数值或字符串

## 二：PHP 的 COOKIE 技术
### (1). COOKIE 工作原理
cookie技术：服务器将数据通过HTTP相应到浏览器上，浏览器可以在以后携带对应的cookie数据访问服务器。

1、第一次请求时，PHP通过setcookie函数将数据通过http协议响应头传输给浏览器
2、浏览器在第一次响应的时候将cookie数据保存到浏览器
3、浏览器后续请求同一个网站时，会自动检测是否存在cookie数据，如果存在，就将在请求头中将数据携带到服务器
4、PHP执行时会自动判断浏览器请求中是否携带cookie，如果携带，则自动保存在\$_COOKIE中
5、利用\$_COOKIE访问cookie数据

![展示](https://img-blog.csdnimg.cn/e0f152cb25574b80837f08e8f15231ce.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (2). COOKIE 基本使用
**设置COOKIE信息**
setcookie()函数用来设定cookie信息
其参数为： setcookie(名字, 值)

1). cookie名的设置： 字符串， 第一个参数
2). cookie值的设置： 第二个参数
3). cookie值的类型要求： 必须是简单类型中的整数或者字符串

以下是演示：
![展示](https://img-blog.csdnimg.cn/43ac1f6e5f184f999fd01d367afd82e0.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)打开浏览器设置，查看网站cookie信息，可见cookie已经设置成功了。
![展示](https://img-blog.csdnimg.cn/85d53223856546c6b5fa0f88388abea5.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/47a5b199fb634777ba57e60093a6f38b.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

**读取COOKIE信息**
可以使用\$_COOKIE数组获得COOKIE信息。

由下图可见，因为setcookie.php文件和getcookie.php文件在同一服务器下，所以getcookie.php可以拿到setcookie.php设置的cookie信息。
![展示](https://img-blog.csdnimg.cn/1c1923560d724871a06f48e9616d48cf.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (3). COOKIE 高级——生命周期
COOKIE生命周期：COOKIE在浏览器生存时间（浏览器在下次访问服务器时是否携带对应的COOKIE）

1). 默认(不设定)时的生命周期：不设定周期默认是关闭浏览器(即会话结束)
2).设定为一个常规日期戳的周期： 通过setcookie函数第三个参数可以限定生命周期，是用时间戳管理，从格林威治时间开始
3).设定为“0”的周期：设置生命周期时用0代替时间戳，表示普通设置，会话结束就过期
4).删除一个cookie的做法：服务器没有权限去操作浏览器上的内容，因此无法直接删除，但可以通过设置生命周期来让浏览器自动判定cookie是否有效，无效的话浏览器会自动清除。
![展示](https://img-blog.csdnimg.cn/367c2c99a0c54529b35ed8b6932c8f8c.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (4). COOKIE 高级——作用范围
作用范围：不同的文件夹层级中，设定的COOKIE默认是在不同文件夹下有访问限制，上层文件夹中设定的COOKIE可以在下层(子文件夹)中访问，而子文件夹中设定的COOKIE不能在上层文件夹中访问。
```php
setcookie(名字, 值, 生命周期, 作用范围)
```

1). 默认(不设定)的范围： 就是使用COOKIE默认的作用范围

2). 设定为 “/” 的含义： 告知浏览器当前COOKIE的作用范围是网站根目录，在setcookie的第四个参数里设置。例如下面的设置,该cookie名为password，值为12345，7天后过期，作用范围是网站根目录，即网站下所有页面都可以访问该cookie。

```php
setcookie('password', 12345, time() + 7*24*60*60, '/');
```

### (5). COOKIE 高级——跨子域
跨子域：在同一级别域名下，一个一级域名可以有多个子域名，他们之间是搭建在不同服务器上,比如腾讯官网是www.qq.com，腾讯公益是gongyi.qq.com。它们之间是搭建在不同服务器上的，但是可以通过COOKIE设置实现对应的COOKIE共享访问（默认是不允许的）。

1). 设定cookie的有效域名：不同域名(包含主机)之间不能共享COOKIE，可以通过设置setcookie的第五个参数来控制。
```php
setcookie(名字, 值, 生命周期, 作用范围, 有效域名)
```

2). 不设定时的默认有效域名是不跨域的。如下实例，在www.qq.com执行这行代码默认不共享COOKIE，即为默认值。
```php
setcookie('password', 12345, time() + 7*24*60*60, '/', 'www.qq.com');
```
3). 跨子域的设定方法：在设定域名访问的时候用设定上级域名即可。如下实例，这行代码执行后，所有qq.com的域名都可以共享该COOKIE。
```php
setcookie('password', 12345, time() + 7*24*60*60, '/', 'qq.com');
```
### (6). COOKIE 高级——数组数据
cookie本身只支持简单数据(数字或者字符串)，能够保留的数据本身有限，也不成体系，如果需要使用cookie来保留一组数据的话，就要想办法凑成数组(cookie不支持数组)。

下图可见，cookie本身不支持保存数组
![在这里插入图片描述](https://img-blog.csdnimg.cn/0136236f0ffe46458beb077756c93e79.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
因此需要伪装数组：

1). 设置形式：setcookie('c1[k1]', 值)

```php
<?php

// 伪装数组
setcookie('goods_ids[0]', 1);
setcookie('goods_ids[1]', 2);
setcookie('goods_ids[2]', 3);
setcookie('goods_ids[3]', 4);

echo '<pre>';
var_dump($_COOKIE);
```
效果如下，值得注意的是，我第一次打开并没有读取到goods_ids的cookie，刷新后拿到了，推测应该是虽然是先执行setcookie函数，但浏览器先执行完成var_dump(\$_COOKIE)。
![展示](https://img-blog.csdnimg.cn/6fa6ef16584a4193a05849d1f25030e2.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

由下图浏览器设置所示，推测应该在\$_COOKIE中以数组形式存在，但在浏览器中，依然是个体存在。
![在这里插入图片描述](https://img-blog.csdnimg.cn/7b44890eb96d4db9a5dd813ee4643e9d.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

2).读取形式： \$_COOKIE['c1']['k1']

![展示](https://img-blog.csdnimg.cn/cdfe04d253b5403ca7ca9d938ab77bb8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

## 三：PHP 的 SESSION 技术
### (1). SESSION 工作原理
session与浏览器无关，但是与cookie有关。
+ PHP碰到 session_start() 时开启 session 会话，会自动检测 sessionID
     - 如果 cookie 中存在，则使用现成的
     - 如果 cookie 中不存在，则创建一个 sessionID ，并通过响应头以 cookie 形式保存到浏览器上。
+ 初始化超全局变量 \$_SESSION 为一个空数组
+ PHP通过 sessionID 去指定位置( session 文件存储位置)匹配对应的文件
     + 不存在该文件，则创建一个 sessionID 命名文件
     + 存在该文件，则读取文件内容(反序列化)， 将数据存储到 \$_SESSION 中
+ 脚本执行结束，将 \$_SESSION 中保存的所有数据序列化存储到 sessionID 对应的文件中
![展示](https://img-blog.csdnimg.cn/a2a732f8189b414a9f46afa04121faae.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (2). SESSION 基本使用
启用 session ，任何时候都需要开启 session (脚本使用到\$_SESSION 就开启一次)
\$_SESSION 是通过 session_start() 函数的调用才会定义的，没有直接定义。因此下图用法是错误的。
![在这里插入图片描述](https://img-blog.csdnimg.cn/cbd9f3fa51e64bec9f4c5394867cda24.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
这才是正确的用法，session 的使用需要开启session_start()
![展示](https://img-blog.csdnimg.cn/bcc1e6aca0274baa9ae991fa9fd260d6.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

**设置 SESSION 信息**
如果想存储数据到 session 中， 那么只要不断给\$_SESSION 数组添加元素即可。
![展示](https://img-blog.csdnimg.cn/8ce99bc2ae8f4545a18e6fc08a6e5027.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

**读取 SESSION 信息**
\$_SESSION 就是一个数组，存储什么数据，什么方式存储的，就可以通过什么方式访问。
![在这里插入图片描述](https://img-blog.csdnimg.cn/58ffa775a682421caed3e30a46a8e411.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)会话技术的目的就是实现跨脚本共享数据：在一个脚本中定义数据，在另一个脚本中保存数据。
![展示](https://img-blog.csdnimg.cn/805f42f36cc54f76a6bc003ae233ee2a.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)由图中红色字体部分可知，为什么 session_use.php 脚本能拿到 session.php 设置的值了。
![展示](https://img-blog.csdnimg.cn/3e2f35c19b134199b9c3e994064a6970.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

### (3). SESSION 删除数据
删除 session 就是将 session 数据清理掉(\$_SESSION拿不到)
**删除一个SESSION信息**

```php
unset($_SESSION['名字'])
```
![展示](https://img-blog.csdnimg.cn/18716f222a9947a7abe032fee1a70ebb.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
**删除全部SESSION信息**
删除全部 session 就是让 \$_SESSION 变成一个空数组。下面方式只是清除数据，而非清除session文件。清除 session 文件的方法在后面的销毁session会介绍。

```php
$_SESSION = array();
```
![展示](https://img-blog.csdnimg.cn/6e8d68f619ab43df9c8801e88232beee.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

### (4). SESSION 相关配置
#### SESSION 的基础配置
1). session.name ： 指session名字，即保存到cookie中sessionID对应的名字
![展示](https://img-blog.csdnimg.cn/056a1b9aac454ccba212a2a04d2d4528.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

2). session.auto_start ：是否自动开启session(即无需手动session_start())，默认是关闭的。
![在这里插入图片描述](https://img-blog.csdnimg.cn/c76c50f9923144f68adfcdda2b8780c8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

3). session.save_handler ： session 数据的保存方式，默认是文件形式
![在这里插入图片描述](https://img-blog.csdnimg.cn/d11bac16dc814ebba91cf40a19c728f4.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

4). session.save_path ： session 文件默认存储的位置
如果你的session.save_path显示是在'\tmp'中，那么说明文件默认被存储在系统临时文件夹中，这是不安全的，因为有可能面临访问权限问题。因此建议修改路径。
![在这里插入图片描述](https://img-blog.csdnimg.cn/95abce898d374255b35edbd9e416f1cc.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
然后我们可以根据上面的存储路径找到服务器的session文件，如下所示
![在这里插入图片描述](https://img-blog.csdnimg.cn/ab966a7c349f4b39a4da2e872bdf54a8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

#### SESSION 的常用配置
1). session.cookie_lifetime ： sessionID在浏览器端对应的COOKIE的生命周期，默认是会话结束，即浏览器关闭。
![在这里插入图片描述](https://img-blog.csdnimg.cn/a6e7ead88b9847aea2e58db52a901c24.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

2). session.cookie_path ： sessionID 在浏览器存储之后允许服务器访问的路径(COOKIE有作用范围)。默认是网站根目录。
![在这里插入图片描述](https://img-blog.csdnimg.cn/f5d529fc1ee5452caf6f38a5cd51d710.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
3). session.cookie_domain ： COOKIE允许访问的子域(COOKIE可以跨子域)，默认是不能跨子域
![在这里插入图片描述](https://img-blog.csdnimg.cn/a68ca9e38a9849b4b490792a698c78b9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
#### SESSION 配置的两种形式
1). 在php.ini中配置，即全局配置， 配置方法见上图。
2). 在脚本中配置，指PHP可以通过 ini_set 函数在运行中设定某些配置项(只会对当前运行的脚本有效)， 一般把这种配置称之为项目级。

PHP ini_set 函数格式：
```php
string ini_set(string $varname, string $newvalue)  
```

以下是一个示例，考虑到早期版本可能没有ini_set函数，因此在前面加一个错误抑制符。
```php
@ini_set('session.save_path', 'E:/');
```

### (5). SESSION 销毁session
系统提供了函数： session_destroy()， 该函数会自动根据 session_start() 得到的 sessionID 去找到指定的 session 文件， 并把其删除。
![展示](https://img-blog.csdnimg.cn/360845b3de6c4c68bae682fc16308b1c.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/db3421463e5d4f29a2a54366ad352034.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

### (6). SESSION 垃圾回收
session会话技术诞生后， session文件并不会自动清除，如果每天有大量session文件产生但又都是失效的，那么会增加服务器的压力并且影响session效率。
垃圾回收，是指session机制提供了一种解决垃圾session文件的方式，给session文件指定周期，通过session文件最后更改时间与生命周期进行结合判定，如果已经过期则删除对应的session文件，如果没有过期则保留，这样就可以及时清理无效的僵尸文件，从而提升空间利用率和session工作效率。

1). 任何一次session开启(session_start)，session都会尝试去读取session文件
2). 读取session文件后，有可能触发垃圾回收机制(在session系统中也是一个函数：自己有一定几率调用)
3). 垃圾回收机制会自动读取所有session文件的最后编辑时间，然后加上生命周期(配置文件)与当前时间进行比较(所有session文件)，过期则删除，有效则保留。

**垃圾回收参数设置**
1). session.gc_maxlifetime = 1440 : 规定的session文件最大的生命周期是1440秒，即24分钟。
2). session.gc_probability = 1 ：垃圾回收概率因子(分子)
3). session.gc_divisor = 1000 : 垃圾回收概率分母
由session.gc_probability和session.gc_divisor知默认触发回收的概率是千分之一。实际开发中可以根据用户量进行调整。
![展示](https://img-blog.csdnimg.cn/58265dfa63104d7893e94b41904473c2.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

### (7). SESSION 禁用cookie时使用session
设置浏览器禁止cookie：
![在这里插入图片描述](https://img-blog.csdnimg.cn/a2ba0e723f17440b96238143458f96a7.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)依次执行 session.php 文件和 session_use.php 文件，发现 session_use.php 已经无法拿到 session
数据了。
![在这里插入图片描述](https://img-blog.csdnimg.cn/3c526986514a4605b6abbc45f92e8833.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)


**禁止COOKIE不能使用SESSION的原因**
session技术需要利用到cookie技术来保存sessionID，从而使得PHP能够在跨脚本的时候得到相同的sessionID，从而访问同一个session文件。

**禁止COOKIE后如何使用SESSION**
让session_start在开启之前 拿到原来的sessionID（另一个脚本的）

**实现无COOKIE使用SESSION**
方案一： 利用PHP提供的session函数： session_id 和 session_name 来获得和设置sessionID或者name从而解决session_start产生新的sessionID的情况(手动操作)
1). 在session保存数据的脚本中获取sessionID和名字

```php
<?php

// 开启 session
session_start();

// 获取sessionID和名字
$id = session_id();  // 获取是在session_start执行之后
$name = session_name();  // 拿到名字(即php.ini 中的 session.name)

echo $name . '=' . $id;
```

![展示](https://img-blog.csdnimg.cn/38354433804941a39faf750c63f5e64e.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)2). 接下来就是想办法把数据传递给另外一个脚本：表单传值(URL或form表单)

session.php 文件代码：
```php
<?php

// 开启 session
session_start();

// 设置内容
$_SESSION['name'] = 'eyes';

// 获取sessionID和名字
$id = session_id();  // 获取是在session_start执行之后
$name = session_name();  // 拿到名字(即php.ini 中的 session.name)

// 传递给另外一个脚本
echo "<a href='session_use.php?{$name}={$id}'>click</a>";
```
session_use.php 文件代码：
```php
<?php

// 接收数据
$name = session_name();
$id = $_GET[$name];

// 设定sessionID，该操作可以阻止session_start产生新的ID
session_id($id);

// 开启session
session_start();

// 访问
var_dump($_SESSION);
```

![展示](https://img-blog.csdnimg.cn/5088585f34f046628e6a434afa11263d.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

方案二： 利用session机制中的已经提供的解决方案自动操作（通过配置实现）
原因1：默认session配置只允许使用cookie保存sessionID： cookie_only
原因2: 默认关闭了其它能够传递数据的方式，只保留了cookie
1). 修改PHP配置文件，开启其他方式传输sessionID，关闭只允许使用cookie传输功能。
![在这里插入图片描述](https://img-blog.csdnimg.cn/9f315cca3954423989841fb363a93ee3.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/377ae02e3f2444598c9ff423ce3c7df8.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)2). 重启apache。一旦配置开启，PHP会自动将sessionID和session名字在其他位置绑定数据，同时还会在session_start的时候，考虑通过表单传递数据，而非只有cookie。

![展示](https://img-blog.csdnimg.cn/1932f52fded94aecb1d5a27231afecac.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)