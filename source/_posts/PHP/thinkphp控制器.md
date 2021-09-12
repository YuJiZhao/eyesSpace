---
title: thinkphp控制器
date: 2021-08-26 19:56:48
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/17.jpg
tags: 
  - thinkphp
  - 控制器
categories: PHP
---
# 一：控制器定义
控制器，即 `controller`，控制器文件存放在 controller 目录下。

控制器有以下若干知识点：
+ 控制器下的每一个php类文件一般只有一个类，每个类必须有一个index函数，否则报错。
+ 如果想改变系统默认的控制器文件目录，可以在 config 目录下的 `route.php` 配置：` 'controller_layer' => '修改后的名称'`。
+ 类名和文件名大小写保持一致，并采用驼峰式（首字母大写）
+ 如果使用的是单应用模式，那么控制器的类的定义如下，控制器类文件的实际位置是`app\controller\HelloWorld.php`，在没有定义路由的情况下访问的URL地址是：`http://localhost/index.php/HelloWorld/hello`
，对于路径中的大写可以使用_换成小写，如`http://localhost/hello_world/hello`，事实上`http://localhost/index.php/helloworld/hello`也可以，但不推荐。

```php
<?php
namespace app\controller;

class HelloWorld 
{
    public function hello()
    {
        return 'hello，world！';
    }
}
```
+ 如果希望避免引入同名模型类的时候冲突，可以在`route.php`配置文件中设置`'controller_suffix'     => true`，那样的话就意味着使用控制器后缀，于是类名就需要添加Controller后缀，如下，相应的类文件也要改为`app\controller\HelloWorldController.php`

```php
<?php
namespace app\controller;

class HelloWorldController
{
    public function login()
    {
        return 'login';
    }
}
```
+ 控制器一般不需要任何输出，直接return即可。并且控制器在json请求会自动转换为json格式输出。也可以调用`json($data)`手动转化为json格式。
+ 不要在控制器中使用包括die、exit在内的中断代码。如果你需要调试并中止执行，可以使用系统提供的halt助手函数：`halt('输出测试')`

# 二：基础控制器
大多数情况下，官方建议给你的控制器继承一个基础控制器。默认安装后，系统提供了一个`app\BaseController`基础控制器类，你可以对该基础控制器进行修改。该基础控制器仅仅提供了控制器验证功能，并注入了`think\App`和`think\Request`对象，因此你可以直接在控制器中使用app和request属性调用think\App和think\Request对象实例。至于控制器验证我水平有限，可能在以后的博客中添加相关内容。。。
![在这里插入图片描述](https://img-blog.csdnimg.cn/4f76b564cc35407f8b829ef067c0bd2c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 三：空控制器
空控制器的概念是指当系统找不到指定的控制器名称的时候，系统会尝试定位当前应用下的空控制器(Error)类，利用这个机制我们可以用来定制错误页面和进行URL的优化。
![在这里插入图片描述](https://img-blog.csdnimg.cn/263e3a1f5cde48568780257790107cfb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 四：多级控制器
所谓多级控制器，就是在控制器 controller 目录下再建立目录并创建控制器，我们在 controller 目录下建立 group 目录，并创建 Blog.php 控制器，而此时，我们需要访问的地址为：`http://localhost:8000/group.blog`，这样可能有点别扭，但是可以使用路由的知识改变该路径。

![在这里插入图片描述](https://img-blog.csdnimg.cn/b5bcce7f53404ece8d050ccb03262bf1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)由于URL访问不能访问默认的多级控制器（可能会把多级控制器名误识别为URL后缀），因此建议所有的多级控制器都通过路由定义后访问，如果要在路由定义中使用多级控制器，可以使用`Route::get('group/blog','group.blog/index')`
![在这里插入图片描述](https://img-blog.csdnimg.cn/27674e8a52a049368533bdbced3938ad.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 五：资源控制器
资源控制器可以让你轻松的创建RESTFul资源控制器，可以通过命令行生成需要的资源控制器，例如生成index应用的Blog资源控制器使用：

> php think make:controller index@Blog

如果不想新建一个文件夹而是在原本的controller下创建，可以使用以下命令：

> php think make:controller Blog

或者使用完整的命名空间生成：

> php think make:controller app\index\controller\Blog

如果只是用于接口开发，可以使用：

> php think make:controller index@Blog --api

然后你只需要为资源控制器注册一个资源路由：

> Route::resource('blog', 'Blog');

这个资源路由表示注册了一个名称为blog的资源路由到Blog控制器，系统会自动注册7个路由规则，如下：
<table><thead><tr><th>标识</th><th>请求类型</th><th>生成路由规则</th><th>对应操作方法（默认）</th></tr></thead><tbody><tr><td>index</td><td>GET</td><td><code>blog</code></td><td>index</td></tr><tr><td>create</td><td>GET</td><td><code>blog/create</code></td><td>create</td></tr><tr><td>save</td><td>POST</td><td><code>blog</code></td><td>save</td></tr><tr><td>read</td><td>GET</td><td><code>blog/:id</code></td><td>read</td></tr><tr><td>edit</td><td>GET</td><td><code>blog/:id/edit</code></td><td>edit</td></tr><tr><td>update</td><td>PUT</td><td><code>blog/:id</code></td><td>update</td></tr><tr><td>delete</td><td>DELETE</td><td><code>blog/:id</code></td><td>delete</td></tr></tbody></table>

关于资源路由的相关介绍将会在后续博客内展开。
![在这里插入图片描述](https://img-blog.csdnimg.cn/271a9f5dd7c84efe80186955bb5a3d5e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

