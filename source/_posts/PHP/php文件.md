---
title: PHP文件上传
date: 2021-07-24 15:55:12
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/70.jpg
tags: 
  - 文件
categories: PHP
---

## PHP文件上传
**文件上传的概念：** 文件从用户本地电脑通过传输方式(Web表单)保存到服务器所在电脑指定的目录下。

**文件上传的原理：**
1. 增加文件上传的表单：浏览器请求一个服务器的HTML脚本(包含文件上传表单)
2. 用户从本地选择一个文件(点击上传框按钮)
3. 用户点击上传：文件会通过物联网传输到服务器上
4. 服务器操作系统会将文件保存到临时目录：是以临时文件格式保存(windows下是tmp格式)
5. 服务器脚本开始工作：判断文件有效
6. 服务器脚本将有效文件从临时目录移动到指定的目录下

![展示](https://img-blog.csdnimg.cn/e813d15882274139a20ca546904f4c26.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (1). 表单制作
1). method属性：表单提交方式必须为POST，因为GET方式传输对文件大小有要求。
2). enctype属性：form表单属性，主要是规范表单数据的编码方式，一般选multipart/form-data
可选属性值如下：
<table>
<tbody><tr>
<th width="40%">值</th>
    <th width="60%">描述</th>
  </tr>
<tr>
<td>application/x-www-form-urlencoded</td>
    <td>默认。在发送前对所有字符进行编码（将空格转换为 "+" 符号，特殊字符转换为 ASCII HEX 值）。</td>
  </tr>
<tr>
<td>multipart/form-data</td>
    <td>不对字符编码。当使用有文件上传控件的表单时，该值是必需的。</td>
  </tr>
<tr>
<td>text/plain</td>
    <td>将空格转换为 "+" 符号，但不编码特殊字符。适用于GET传输</td>
  </tr>
</tbody></table>

3). 上传表单：file表单

```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--单文件上传-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="image" />
    <input type="submit" name="btn" value="upload" />
</form>

</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/b28b522370a0446a90f4486a2618edf1.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (2). $_FILES详解
\$_FILES这个预定义变量是专门用来储存用户上传的文件的。

先检查一下\$_FILES和\$_POST能接收到什么。
![展示](https://img-blog.csdnimg.cn/b04b3c3c16664bf9a4de5b056b61ca14.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/b57d8971eef544b7b431bdce0806bf09.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)因此可见$_FILES变量的详细内容如下：
1). name: 文件在用户(浏览器端)电脑上实际存在的名字(实际用来保留后缀)
2). tmp_name: 文件上传到服务器后操作系统保存的临时路径(实际用来给PHP后期使用)
3). type: MIME(多功能互联网邮件扩展)类型，用来在计算机中客户端识别文件类型(确定软件)
4). error: 文件上传的代号, 用来告知应用软件(php)文件接收过程出了什么问题(php后期根据代码进行文件判断)
5). size: 文件大小(php根据实际需求来确定是否应该保留)

文件上传错误信息说明：

```
从 PHP 4.2.0 开始，PHP 将随文件信息数组一起返回一个对应的错误代码。
该代码可以在文件上传时生成的文件数组中的 error 字段中被找到，也就是 $_FILES['userfile']['error']。
 
UPLOAD_ERR_OK
其值为 0，没有错误发生，文件上传成功。
 
UPLOAD_ERR_INI_SIZE
其值为 1，上传的文件超过了 php.ini 中 upload_max_filesize 选项限制的值。
 
UPLOAD_ERR_FORM_SIZE
其值为 2，上传文件的大小超过了 HTML 表单中 MAX_FILE_SIZE 选项指定的值。
MAX_FILE_SIZE是php设置的一种浏览器端限制大小的标准，但W3C没有承认。
 
UPLOAD_ERR_PARTIAL
其值为 3，文件只有部分被上传。可能是因为自身网络不稳定导致丢包，也有可能是系统不安全。
 
UPLOAD_ERR_NO_FILE
其值为 4，没有文件被上传。可能是用户没有选择需要上传的文件就点击上传了。
 
UPLOAD_ERR_NO_TMP_DIR
其值为 6，找不到临时文件夹。PHP 4.3.10 和 PHP 5.0.3 引进。即操作系统对应的临时文件夹不存在。
 
UPLOAD_ERR_CANT_WRITE
其值为 7，文件写入失败。PHP 5.1.0 引进。
通常是因为PHP没有权限将临时文件移动到指定目录，在windows中基本不存在，但在linux系统中经常发生。
```
### (3). 保存上传文件
文件上传后会保存在$_FILES中，那么访问文件信息的形式就是\$_FILES['name属性值']['元素信息']
保存上传文件即移动临时文件到目标位置，步骤如下：
1). 判断是否为上传的文件(保障数据安全)： is_uploaded_file()
2). 移动文件： move_uploaded_file()

is_uploaded_file()函数判断指定的文件是否是通过 HTTP POST 上传的。如果 file 所给出的文件是通过 HTTP POST 上传的则返回 TRUE。该函数可以用于确保恶意的用户无法欺骗脚本去访问本不能访问的文件，例如 /etc/passwd。这种检查显得格外重要，如果上传的文件有可能会造成对用户或本系统的其他用户显示其内容的话。另外，该函数的结果会被缓存。请使用 clearstatcache() 来清除缓存。

move_uploaded_file() 函数将上传的文件移动到新位置。若成功，则返回 true，否则返回 false。该函数有两个参数move_uploaded_file(file,newloc)，参数file规定要移动的文件，参数newloc规定文件的新位置，**且新位置需要是包括文件自身名字**。这种检查显得格外重要，如果上传的文件有可能会造成对用户或本系统的其他用户显示其内容的话。此函数仅用于通过 HTTP POST 上传的文件。注意：如果目标文件已经存在，将会被覆盖。

form_upload.html文件代码：
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--单文件上传-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="image" />
    <input type="submit" name="btn" value="upload" />
</form>

</body>
</html>
```
form_upload.php文件代码：
```php
<?php
header('Content-type:text/html;charset=utf-8');

// 1.获得文件信息
$file = $_FILES['image'];

// 2.判断是否是上传文件
if (is_uploaded_file($file['tmp_name'])){
    // 是上传文件
    if (move_uploaded_file($file['tmp_name'], 'newplace/'.$file['name'])){
        echo "文件保存成功!";
    } else{
        echo "文件保存失败!";
    }
} else{
    // 不是上传文件
    echo "文件上传失败!" . "<br>";
}
```
![展示](https://img-blog.csdnimg.cn/dc5f8262c25840ac93e46838f2a40de3.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/817a9675b76c40198f63e7c9c6f52cb3.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (4). 多文件上传
当商品需要上传多个图片进行展示的时候，那么需要使用多文件上传
&emsp;&emsp;&emsp;针对一个内容但是不同文件说明：同名表单
当商品需要进行多个维度图片说明的时后，需要使用多文件上传
&emsp;&emsp;&emsp;针对的是不同内容所以表单名字不一样：批量解决问题

<br>

#### 多文件上传的$_FILES变量的数据结构形式：

**同名表单：** 将表单名字形成一个数组，而且同时将文件对应的五个要素：name、Tmp_name、size、type、error都形成对应数量的数组。每个文件上传对应数组元素的下标都是一样的：

form_upload.html 文件代码如下：
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--多文件上传(同名)-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="images[]" />
    <input type="file" name="images[]" />
    <input type="file" name="images[]" />
    <input type="submit" name="btn" value="同名文件批量上传" />
</form>

</body>
</html>
```
form_upload.php 文件代码如下：

```php
<?php
header('Content-type:text/html;charset=utf-8');

// 查看文件信息
echo "<pre>";
var_dump($_FILES);
```
![展示](https://img-blog.csdnimg.cn/ab1fc555faf94c07bfb40722da00d3e4.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
**不同名表单：** 每个文件都会形成一个属于自己独立的5个元素数组
form_upload.html 文件代码如下：
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--多文件上传(不同名)-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="image1" />
    <input type="file" name="image2" />
    <input type="file" name="image3" />
    <input type="submit" name="btn" value="不同名文件批量上传" />
</form>

</body>
</html>
```
form_upload.php 文件代码如下：

```php
<?php
header('Content-type:text/html;charset=utf-8');

// 查看文件信息
echo "<pre>";
var_dump($_FILES);
```
![展示](https://img-blog.csdnimg.cn/5ee66c11be684a96974ae494ee3d0371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
#### 对多文件信息的遍历读取和处理
**同名多文件上传处理方式：** 想办法得到一个文件对应的五个元素数组。从\$_FILES中把对应的name \ tmp_name \ size \ error \ type 信息挨个取出。然后存放到不同的数组中。

form_upload.html 文件代码：
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--多文件上传(同名)-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="images[]" />
    <input type="file" name="images[]" />
    <input type="file" name="images[]" />
    <input type="submit" name="btn3" value="同名文件批量上传" />
</form>

</body>
</html>
```

form_upload.php 文件代码：
```php
<?php
header('Content-type:text/html;charset=utf-8');
// 判断元素存在而且是数组： 有name则代表是文件， name元素有多个(数组)则代表是同名批量上传
if (isset($_FILES['images']['name']) && is_array($_FILES['images']['name'])) {
    // 遍历构造数组元素
    $images = array();  // 存储所有文件信息，一个元素代表一个文件(数组)
    foreach ($_FILES['images']['name'] as $k => $file) {
        $images[] = array(
            'name' => $file,
            'tmp_name' => $_FILES['images']['tmp_name'][$k],
            'type' => $_FILES['images']['type'][$k],
            'error' => $_FILES['images']['error'][$k],
            'size' => $_FILES['images']['size'][$k]
        );
    }
// 处理完后执行长传操作
    foreach ($images as $files) {  // $files就是一个完整的上传文件信息
        // 找到临时路径，指定存放路径
        if (is_uploaded_file($files['tmp_name'])) {
            // 是上传文件，则执行存储操作
            if (move_uploaded_file($files['tmp_name'], 'newplace/' . $files['name'])) {
                echo "文件保存成功！" . "<br>";
            } else {
                echo "文件保存失败！" . "<br>";
            }
        } else {
            // 不是上传文件
            echo "文件上传失败！" . "<br>";
        }
    }
}
```
![展示](https://img-blog.csdnimg.cn/fdb50f3b9d654b4e948a950a4e9fb3df.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/731e5133ab324973a1f9ce7402a378ba.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)

**不同名多文件上传处理方式：** 按照表单名字从$_FILES中取出来就可以直接使用(明确知道表单中有多少个文件上传)，如果不确定表单中有多少个文件上传，就不适合挨个去取(效率不高)，可以通过遍历数组\$_FILES数组挨个取出。

form_upload.html文件代码:
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!--多文件上传(不同名)-->
<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="image1" />
    <input type="file" name="image2" />
    <input type="file" name="image3" />
    <input type="submit" name="btn3" value="不同名批量上传" />
</form>

</body>
</html>
```
form_upload.php文件代码:

```php
<?php
header('Content-type:text/html;charset=utf-8');

// 不同名文件遍历处理方式
foreach ($_FILES as $file){  // $file就是一个完整的上传文件信息
    // 找到临时路径，指定存放路径
    if(is_uploaded_file($file['tmp_name'])){
        // 是上传文件，则执行存储操作
        if (move_uploaded_file($file['tmp_name'], 'newplace/'.$file['name'])){
            echo "文件保存成功！" . "<br>";
        } else{
            echo "文件保存失败！" . "<br>";
        }
    } else{
        // 不是上传文件
        echo "文件上传失败！" . "<br>";
    }
}
```

效果展示：
![展示](https://img-blog.csdnimg.cn/ed3995585bde4c1481697b300c9d495a.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/9d9d4dd9d86e46c48dbaaff5191f1c98.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### (5). 函数封装
实现上传功能代码的重复利用：封装文件上传函数
功能： 上传文件
条件：条件判断
1. 文件类型是否合适？外部指定MIME类型
2. 文件存储到什么位置？外部指定
3. 文件格式限制(文件后缀)?外部限定
4. 文件大小限制？外部指定

过程：
1). 封装出一个上传函数
2). 判断文件是否有效
3). 判断保存路径是否有效
4). 判断文件本身上传的过程中是否有错误：error
5). 文件类型的处理：通过MIME匹配
6). 文件格式的处理：后缀名问题
7). 文件大小的处理
8). 命名冲突的处理：上传同名文件？名字里出现中文？
9). 移动到指定目录

结果：实现文件上传
5. 成功：结果能够在以后看到：需要将文件的路径和文件名字返回(存储到数据库)
6. 失败：返回false，指定错误原因(引用参数)

form_upload.html 文件代码：
```html
<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form method="POST" enctype="multipart/form-data" action="form_upload.php">
    <input type="file" name="image" />
    <input type="submit" name="btn" value="单文件上传" />
</form>

</body>
</html>
```

form_upload.php 文件代码：
```php
<?php
header('Content-type:text/html;charset=utf-8');

// PHP文件上传功能封装函数

/*
 * 实现文件上传(单文件)
 * @param1 array $file,  需要上传的文件信息：一维五元素数组(name\tmp_name\error\size\type)
 * @param2 array $allow_type， 允许上传的MIME类型
 * @param3 string $path,  存储的路径
 * @param4 string &$error,  如果出现错误的原因
 * @param5 array $allow_format = array(),  允许上传的文件格式
 * @param6 int $max_size = 2000000,  允许上传的最大值，单位为字节
 */

// 封装一个上传函数
function upload_single($file, $allow_type, $path, &$error, $allow_format = array(), $max_size = 2000000)
{
    // 判断文件是否有效
    if (!is_array($file) || !isset($file['error'])){
        // 文件无效
        $error = '不是一个有效的上传文件！';
        return false;
    }

    // 判断保存路径有效
    if (!is_dir($path)){
        // 路径不存在
        $error = '文件存储路径不存在！';
        return false;
    }

    // 判断文件上传过程是否出错
    switch ($file['error']){
        case 1:
        case 2:
            $error = '文件超出服务器允许的大小！';
            return false;
        case 3:
            $error = '文件上传过程中出现问题，只上传了一部分！';
            return false;
        case 4:
            $error = '用户没有选中要上传的文件！';
            return false;
        case 6:
        case 7:
            $error = '文件保存失败！';
            return false;
    }

    // 判断MIME类型
    if (!in_array($file['type'], $allow_type)){
        // 该文件类型不允许上传
        $error = '当前文件类型不允许上传!';
        return false;
    }

    // 判断后缀名是否允许
    $ext = ltrim(strrchr($file['name'], '.'), '.');  // 取出后缀名
    if (!empty($allow_format) && !in_array($ext, $allow_format)){
        // 不允许上传
        $error = '当前文件的格式不允许上传!';
        return false;
    }

    // 判断当前文件大小是否满足当前需求
    if ($file['size'] > $max_size){
        // 文件过大
        $error = '当前上传的文件超出大小，最大允许' . $max_size . '字节';
        return false;
    }

    // 构造文件名字，防止名字冲突以及中文乱码：类型_年月日_随机字符串.$ext
    $fullname = strstr($file['type'], '/', TRUE) . date('Ymd');
    for ($i = 0; $i < 4; $i++){  // 产生随机字符串
        $fullname .= chr(mt_rand(65, 90));
    }
    $fullname .= '.' . $ext;  // 拼接后缀

    // 能执行到这就说明文件满足需求，现在将文件移动到指定目录
    if (!is_uploaded_file($file['tmp_name'])){
        // 文件不是上传的
        return false;
    } else{
        if (move_uploaded_file($file['tmp_name'], $path . '/' . $fullname)){
            // 移动成功
            return $fullname;
        } else{
            // 移动失败
            $error = '文件上传失败!';
            return false;
        }
    }
}

// 提供数据
$file = $_FILES['image'];
$allow_type = array('image/jpg', 'image/jpeg', 'image/jpg', 'image/png', 'image/gif');
$path = 'newplace';
$allow_format = array('jpg', 'jpeg', 'png', 'gif');
$max_size = 8000000;

// 调用函数
if ($filename = upload_single($file, $allow_type, $path, $error, $allow_format = array(), $max_size = 2000000))
{
    echo $filename;
} else{
    echo $error;
}
```
效果展示：
![展示](https://img-blog.csdnimg.cn/175b4028a65f49e184163eda5ad021f6.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/8dcb3de2b75f41a59c810a5f05bc0b0e.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)