---
title: PHP表单汇总
date: 2021-07-23 20:52:54
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/27.jpg
tags: 
  - 表单
categories: PHP
---

### PHP 表单
#### (1). PHP表单处理
PHP 中的 $_GET 和 $_POST 变量用于检索表单中的信息，比如用户输入。有一点很重要的事情值得注意，当处理 HTML 表单时，PHP 能把来自 HTML 页面中的表单元素自动变成可供 PHP 脚本使用。

base.html 文件代码：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="base.php" method="post">
    名字: <input type="text" name="fname">
    年龄: <input type="text" name="age">
    <input type="submit" value="提交">
</form>
</body>
</html>
```
base.php 文件代码：

```php
欢迎<?php echo $_POST["fname"]; ?>!<br>
你的年龄是 <?php echo $_POST["age"]; ?>  岁。
```
![展示](https://img-blog.csdnimg.cn/7b8c7bdd6e9a46049667346aac6ebf89.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
#### (2). PHP获取下拉菜单的数据
**PHP下拉菜单单选**

```php
<?php
$q = isset($_GET['q']) ? htmlspecialchars($_GET['q']) : '';
if ($q) {
    if ($q == 'RUNOOB') {
        echo '菜鸟教程<br>http://www.runoob.com';
    } else if ($q == 'GOOGLE') {
        echo 'Google 搜索<br>http://www.google.com';
    } else if ($q == 'TAOBAO') {
        echo '淘宝<br>http://www.taobao.com';
    }
} else {
    ?>.
    <form action="" method="get">  <!--action 属性值为空表示提交到当前脚本-->
        <select name="q">
            <option value="">选择一个站点:</option>
            <option value="RUNOOB">Runoob</option>
            <option value="GOOGLE">Google</option>
            <option value="TAOBAO">Taobao</option>
        </select>
        <input type="submit" value="提交">
    </form>
    <?php
}
?>
```
现对代码第二行做解释：

```php
$q = isset($_GET['q']) ? htmlspecialchars($_GET['q']) : '';
```
isset ()函数是用于确定变量是否已设置且不为空；换句话说，仅当变量不为null时才返回true。因此如果$_GET['q']为空，则\$q为空，那么直接回到else的语句中，即返回空表单。如果不为空，则\$q被赋值htmlspecialchars($_GET['q'])， htmlspecialchars()函数能把预定义的字符转换为 HTML 实体，用以绕过XSS攻击。XSS又叫 CSS (Cross-Site Script) ,跨站脚本攻击。恶意攻击者往Web页面里插入恶意html代码，当用户浏览该页之时，嵌入其中Web里面的html代码会被执行，从而达到恶意用户的特殊目的。 如需进一步了解，可查看大佬博客：[XSS绕过之PHP htmlspecialchars() 函数](https://blog.csdn.net/Yoo_666/article/details/107319239)

**PHP下拉菜单多选**
如果下拉菜单是多选的（ multiple="multiple"），我们可以通过将设置 select name="q[]" 以数组的方式获取。
```php
<?php
$q = isset($_POST['q'])? $_POST['q'] : '';
if(is_array($q)) {
    $sites = array(
        'RUNOOB' => '菜鸟教程: http://www.runoob.com',
        'GOOGLE' => 'Google 搜索: http://www.google.com',
        'TAOBAO' => '淘宝: http://www.taobao.com',
    );
    foreach($q as $val) {
        // PHP_EOL 为常量，用于换行
        echo $sites[$val] . "<br>";
    }

} else {
    ?>
    <form action="" method="post">
        <select multiple="multiple" name="q[]">
            <option value="">选择一个站点:</option>
            <option value="RUNOOB">Runoob</option>
            <option value="GOOGLE">Google</option>
            <option value="TAOBAO">Taobao</option>
        </select>
        <input type="submit" value="提交">
    </form>
    <?php
}
?>
```
#### (3). 单选按钮表单
PHP 单选按钮表单中 name 属性的值是一致的，value 值是不同的

```php
<?php
$q = isset($_GET['q'])? htmlspecialchars($_GET['q']) : '';
if($q) {
        if($q =='RUNOOB') {
                echo '菜鸟教程<br>http://www.runoob.com';
        } else if($q =='GOOGLE') {
                echo 'Google 搜索<br>http://www.google.com';
        } else if($q =='TAOBAO') {
                echo '淘宝<br>http://www.taobao.com';
        }
} else {
?><form action="" method="get"> 
    <input type="radio" name="q" value="RUNOOB" />Runoob
    <input type="radio" name="q" value="GOOGLE" />Google
    <input type="radio" name="q" value="TAOBAO" />Taobao
    <input type="submit" value="提交">
</form>
<?php
}
?>
```
#### (4). checkbox复选框
```php
<?php
$q = isset($_POST['q'])? $_POST['q'] : '';
if(is_array($q)) {
    $sites = array(
            'RUNOOB' => '菜鸟教程: http://www.runoob.com',
            'GOOGLE' => 'Google 搜索: http://www.google.com',
            'TAOBAO' => '淘宝: http://www.taobao.com',
    );
    foreach($q as $val) {
        echo $sites[$val] . "<br>";
    }
      
} else {
?><form action="" method="post"> 
    <input type="checkbox" name="q[]" value="RUNOOB"> Runoob<br> 
    <input type="checkbox" name="q[]" value="GOOGLE"> Google<br> 
    <input type="checkbox" name="q[]" value="TAOBAO"> Taobao<br>
    <input type="submit" value="提交">
</form>
<?php
}
?>
```

### PHP 表单验证
我们应该尽可能的对用户的输入进行验证（通过客户端脚本）。浏览器验证速度更快，并且可以减轻服务器的压力。如果用户输入需要插入数据库，就应该考虑使用服务器验证。在服务器验证表单的一种好的方式是，把表单的数据传给当前页面（异步提交的方式更好），而不是跳转到不同的页面。这样用户就可以在同一张表单页面得到错误信息。用户也就更容易发现错误了。

```php
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>demo</title>
</head>
<body>

<?php
// 定义变量并默认设置为空值
$nameErr = $emailErr = $genderErr = $websiteErr = "";
$name = $email = $gender = $comment = $website = "";

if ($_SERVER["REQUEST_METHOD"] == "POST")
{
    if (empty($_POST["name"]))
    {
        $nameErr = "名字是必需的";
    }
    else
    {
        $name = test_input($_POST["name"]);
        // 检测名字是否只包含字母跟空格
        if (!preg_match("/^[a-zA-Z ]*$/",$name))
        {
            $nameErr = "只允许字母和空格";
        }
    }

    if (empty($_POST["email"]))
    {
        $emailErr = "邮箱是必需的";
    }
    else
    {
        $email = test_input($_POST["email"]);
        // 检测邮箱是否合法
        if (!preg_match("/([\w\-]+\@[\w\-]+\.[\w\-]+)/",$email))
        {
            $emailErr = "非法邮箱格式";
        }
    }

    if (empty($_POST["website"]))
    {
        $website = "";
    }
    else
    {
        $website = test_input($_POST["website"]);
        // 检测 URL 地址是否合法
        if (!preg_match("/\b(?:(?:https?|ftp):\/\/|www\.)[-a-z0-9+&@#\/%?=~_|!:,.;]*[-a-z0-9+&@#\/%=~_|]/i",$website))
        {
            $websiteErr = "非法的 URL 的地址";
        }
    }

    if (empty($_POST["comment"]))
    {
        $comment = "";
    }
    else
    {
        $comment = test_input($_POST["comment"]);
    }

    if (empty($_POST["gender"]))
    {
        $genderErr = "性别是必需的";
    }
    else
    {
        $gender = test_input($_POST["gender"]);
    }
}

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>

<h2>PHP 表单验证实例</h2>
<p><span style="color: red">* 必需字段。</span></p>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">

    名字: <input type="text" name="name" value="<?php echo $name;?>">
    <span class="error">* <?php echo $nameErr;?></span>
    <br><br>

    E-mail: <input type="text" name="email" value="<?php echo $email;?>">
    <span class="error">* <?php echo $emailErr;?></span>
    <br><br>

    网址: <input type="text" name="website" value="<?php echo $website;?>">
    <span class="error"><?php echo $websiteErr;?></span>
    <br><br>

    备注: <textarea name="comment" rows="5" cols="40"><?php echo $comment;?></textarea>
    <br><br>

    性别:
    <input type="radio" name="gender" <?php if (isset($gender) && $gender=="female") echo "checked";?>  value="female">女
    <input type="radio" name="gender" <?php if (isset($gender) && $gender=="male") echo "checked";?>  value="male">男
    <span class="error">* <?php echo $genderErr;?></span>

    <br><br>
    <input type="submit" name="submit" value="Submit">

</form>

<?php
echo "<h2>您输入的内容是:</h2>";
echo $name;
echo "<br>";
echo $email;
echo "<br>";
echo $website;
echo "<br>";
echo $comment;
echo "<br>";
echo $gender;
?>

</body>
</html>
```
<table class="reference">
  <tbody><tr>
    <th style="width:25%">字段</th>
    <th style="width:75%">验证规则</th>
  </tr>
  <tr>
    <td>名字</td>
    <td>必须。 只能包含字母和空格</td>
  </tr>
  <tr>
    <td>E-mail</td>
    <td>必须。  必须是一个有效的电子邮件地址（包含'@'和'.'）</td>
  </tr>
  <tr>
    <td>网址</td>
    <td>可选。如果存在，它必须包含一个有效的URL</td>
  </tr>
  <tr>
    <td>备注</td>
    <td>可选。多行输入字段（文本域）</td>
  </tr>
  <tr>
    <td>性别</td>
    <td>必须。 必须选择一个</td>
  </tr>
  </tbody></table>
  
![展示](https://img-blog.csdnimg.cn/18662a27148b44f5bbc6218d984e5554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/2897a22f073a41a8b4893d7ba4f454ee.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### $_GET 变量
在 PHP 中，预定义的 $_GET 变量用于收集来自 method="get" 的表单中的值。从带有 GET 方法的表单发送的信息，对任何人都是可见的（会显示在浏览器的地址栏），并且对发送信息的量也有限制。

base.html文件代码：
```html
<html>
<head>
<meta charset="utf-8">
<title>demo</title>
</head>
<body>

<form action="base.php" method="get">
名字: <input type="text" name="fname">
年龄: <input type="text" name="age">
<input type="submit" value="提交">
</form>

</body>
</html>
```

base.php文件代码如下：

```php
<?php
echo $_GET['fname'] . '<br>' . $_GET['age'];
```

![展示](https://img-blog.csdnimg.cn/d5743dfb6ca043deb7ddcd9f5f70c9d4.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/670d7a1aae514075aef89e7c77ae68e0.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
当用户点击 "Submit" 按钮时，发送到服务器的 URL 如下所示：

```cpp
http://localhost:63342/web/review/base.php?fname=小王&age=18
```

在 HTML 表单中使用 method="get" 时，所有的变量名和值都会显示在 URL 中。所以在发送密码或其他敏感信息时，不应该使用这个方法！然而，正因为变量显示在 URL 中，因此可以在收藏夹中收藏该页面。在某些情况下，这是很有用的。
另外，HTTP GET 方法不适合大型的变量值。它的值是不能超过 2000 个字符的。

### $_POST 变量
预定义的 $_POST 变量用于收集来自 method="post" 的表单中的值。从带有 POST 方法的表单发送的信息，对任何人都是不可见的（不会显示在浏览器的地址栏），并且对发送信息的量也没有限制。然而，默认情况下，POST 方法的发送信息的量最大值为 8 MB（可通过设置 php.ini 文件中的 post_max_size 进行更改）。

```html
<html>
<head>
<meta charset="utf-8">
<title>demo</title>
</head>
<body>

<form action="base.php" method="post">
名字: <input type="text" name="fname">
年龄: <input type="text" name="age">
<input type="submit" value="提交">
</form>

</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/4789e2214f514175a32576b92c1df785.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
![展示](https://img-blog.csdnimg.cn/c33249aa311f4dbc8b2cef184a25ab2d.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
从带有 POST 方法的表单发送的信息，对任何人都是不可见的，并且对发送信息的量也没有限制。然而，由于变量不显示在 URL 中，所以无法把页面加入书签。