---
title: PHP加密算法
date: 2021-09-19 20:39:55
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/102.jpg
tags: 
  - 加密解密
categories: PHP
---

# 零：密码外泄门
2011年12月，CSDN的安全系统遭到黑客攻击，600万用户的登录名、密码及邮箱遭到泄漏。随后，CSDN"密码外泄门"持续发酵，天涯、世纪佳缘等网站相继被曝用户数据遭泄密。泄密就算了，更让人无语的是密码等信息都是明文存储，导致黑客直接拿到了信息而无需破解，这一系列事件发生后，密文存储用户信息的方式开始流行。

# 一：前言
这篇博客会介绍一些经典的加密算法，其中md5、sha1、sha256等加密算法虽然是单向散列算法，是不可逆的，但是由于其太经典了，因此很可能被暴力破解，就是他们会把常见字符进行组合使用这些经典加密算法后存到数据库中，需要解密时就把需要解密的字符串与数据库中的进行比对，因此都有被破解的风险，因此这类算法并不适合用在密码保护场景。

破解网站如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/310c84133a2244cbbf5b22213b8845a8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)![在这里插入图片描述](https://img-blog.csdnimg.cn/c4c97272b79946df84af1b8fdb9521e5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
对于密码散列安全，php官方是这么说的：
> 为什么需要把应用程序中用户的密码进行散列化

当设计一个需要接受用户密码的应用时， 对密码进行散列是最基本的，也是必需的安全考虑。 如果不对密码进行散列处理，那么一旦应用的数据库受到攻击， 那么用户的密码将被窃取。 同时，窃取者也可以使用用户账号和密码去尝试其他的应用， 如果用户没有为每个应用单独设置密码，那么将面临风险。
通过对密码进行散列处理，然后再保存到数据库中， 这样就使得攻击者无法直接获取原始密码， 同时还可以保证你的应用可以对原始密码进行相同的散列处理， 然后比对散列结果。
需要着重提醒的是，密码散列只能保护密码 不会被从数据库中直接窃取， 但是无法保证注入到应用中的 恶意代码拦截到原始密码。

> 为何诸如 md5() 和 sha1() 这样的常见散列函数不适合用在密码保护场景

MD5，SHA1 以及 SHA256 这样的散列算法是面向快速、高效 进行散列处理而设计的。随着技术进步和计算机硬件的提升， 破解者可以使用“暴力”方式来寻找散列码 所对应的原始数据。
因为现代化计算机可以快速的“反转”上述散列算法的散列值， 所以很多安全专家都强烈建议 不要在密码散列中使用这些散列算法。

> 如果不建议使用常用散列函数保护密码， 那么我应该如何对密码进行散列处理

我们可以在进行散列处理的过程中加入的一些数据，用来避免从已计算的散列值表 （被称作“彩虹表”）中 对比输出数据从而获取明文密码的风险。
简单而言，“盐”就是为了提高散列值被破解的难度而加入的少量数据。 现在有很多在线服务都能够提供计算后的散列值以及其对应的原始输入的清单， 并且数据量极其庞大。 通过加“盐”就可以避免直接从清单中查找到对应明文的风险。
如果不提供“盐”，password_hash() 函数会随机生成“盐”。 非常简单，行之有效。

虽然这些算法都有可能被破解，但是这篇博客还是会介绍几种推荐保存的密码的算法的。

# 二：Md5加密算法(不推荐保存密码)
是单项散列加密算法，不可逆。MD5加密是php最常见的不可逆加密算法。来自 RFC 1321 的解释 - MD5 报文摘要算法：MD5 报文摘要算法将任意长度的信息作为输入值，并将其换算成一个 128 位长度的"指纹信息"或"报文摘要"值来代表这个输入值，并以换算后的值作为结果。MD5 算法主要是为数字签名应用程序而设计的；在这个数字签名应用程序中，较大的文件将在加密（这里的加密过程是通过在一个密码系统下[如：RSA]的公开密钥下设置私有密钥而完成的）之前以一种安全的方式进行压缩。

语法如下：

```php
string md5 ( string $str [, bool $raw_output = false ] )
```
参数一的`$str`是原始字符串
参数二的`$raw_output`是可选值，默认为false，如果设置为true，则md5报文摘要将以16字节长度的原始二进制格式返回。返回以32位字符十六进制数字形式返回散列值。

示例如下：

```php
<?php
$str = "eyes";
echo md5($str);
echo "<br>";
echo md5($str, true);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/65ac469513714024a532eb1875c0f83d.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 三：Sha1加密算法(不推荐保存密码)
sha1加密算法和MD5加密算法一样时不可逆的，有两个参数，一个是要加密的字符串，第二个是bool值，如果指定第二个参数为TRUE，则返回二进制格式的字符串，如果不指定则默认为FALSE，返回的是40位的16进制格式的字符串

语法如下：

```php
string sha1(string $str[, bool $raw_output=false])
```
参数一的`$str`是加密的字符串
参数二的`$raw_output`：如果被设置为TRUE，那么sha1摘要将以20字符长度的原始格式返回，否则返回值是一个40字符长度的十六进制数字
![在这里插入图片描述](https://img-blog.csdnimg.cn/ba2fdc2374f94231bcf216083afac6fe.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)


# 四：Crypt加密算法(推荐保存密码)
crypt()加密算法是一种不可逆的加密算法，他有两个参数，一个是需要加密的字符串，另外一个是盐值（或者成为干扰字符串）。这个函数在不同的操作系统上的表现形式也是不一样的，会自动检测。该算法可以在PHP5.3及后续版本使用， 它支持多种散列算法。 针对每种受支持的散列算法，PHP 都提供了对应的原生实现， 所以在使用此函数的时候， 你需要保证所选的散列算法是你的系统所能够支持的。

如果使用 crypt() 函数来进行密码验证， 那么你需要选择一种耗时恒定的字符串比较算法来避免时序攻击。就是说，字符串比较所消耗的时间恒定， 不随输入数据的多少变化而变化，PHP 中的 \=\=和\=\=\= 操作符 和 strcmp() 函数都不是耗时恒定的字符串比较， 但是 password_verify() 可以帮你完成这项工作。 我们鼓励你尽可能的使用原生密码散列API。

语法如下：

```php
string crypt(string $str[, string $salt])
```
返回一个基于UNIX DES算法或系统上其他可用的替代算法的散列字符串。
参数一的`str`: 需要加密的明文。
参数二的`$salt`：加密时的干扰串，使编码更安全，一般是两个字符
然而，如果没有salt的话，如果加密时没有加上这个`$salt`参数，则会随机生成一个干扰串，且crypt()创建出来的会是弱密码。 php 5.6及之后的版本会在没有它的情况下抛出一个 E_NOTICE 级别的错误。为了更好的安全性，所以需要确保指定一个足够强度的盐值。

```php
<?php
$str = "eyes";
echo crypt($str);
echo "<br>";
echo crypt($str, "eyes");
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/1bfb95fa8752494f9129d4cf9121ebde.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)图片中以\$1开头，\$结尾，中间不超过12个字符，是MD5的特色，这个在下面会提到。

在 crypt() 函数支持多重散列的系统上，下面的常量根据相应的类型是否可用被设置为 0 或 1：
+ CRYPT_STD_DES - 基于标准 DES 算法的散列使用 "./0-9A-Za-z" 字符中的两个字符作为盐值，即如果盐值超过2个字符，则截取前两个字符。在盐值中使用非法的字符将导致 crypt() 失败。  
+ CRYPT_EXT_DES - 扩展的基于 DES 算法的散列。其盐值为 9 个字符的字符串，由 1 个下划线后面跟着 4 字节循环次数和 4 字节盐值组成。它们被编码成可打印字符，每个字符 6 位，有效位最少的优先。0 到 63 被编码为 "./0-9A-Za-z"。在盐值中使用非法的字符将导致 crypt() 失败。  
+ CRYPT_MD5 - MD5 散列使用一个以 $1$ 开始的 12 字符的字符串盐值，注意盐值最多12个字符，超出则截取前8位。  
+ CRYPT_BLOWFISH - Blowfish 算法使用如下盐值："\$2a\$"，一个两位 cost 参数，"\$" 以及 64 位由 "./0-9A-Za-z" 中的字符组合而成的字符串。在盐值中使用此范围之外的字符将导致 crypt() 返回一个空字符串。两位 cost 参数是循环次数以 2 为底的对数，它的范围是 04-31，超出这个范围将导致 crypt() 失败。 PHP 5.3.7 之前只支持 "\$2a\$" 作为盐值的前缀，PHP 5.3.7 开始引入了新的前缀来修正一个在Blowfish实现上的安全风险。可以参考» this document来了解关于这个修复的更多信息。总而言之，开发者如果仅针对 PHP 5.3.7及之后版本进行开发，那应该使用 "\$2y\$" 而非 "\$2a\$"  
+ CRYPT_SHA256 - SHA-256 算法使用一个以\$5\$ 开头的 16 字符字符串盐值进行散列。如果盐值字符串以 "rounds=\<N\>\$" 开头，N 的数字值将被用来指定散列循环的执行次数，这点很像 Blowfish 算法的 cost 参数。默认的循环次数是 5000，最小是 1000，最大是 999,999,999。超出这个范围的 N 将会被转换为最接近的值。  
+ CRYPT_SHA512 - SHA-512 算法使用一个以 \$6\$ 开头的 16 字符字符串盐值进行散列。如果盐值字符串以 "rounds=\<N\>\$" 开头，N 的数字值将被用来指定散列循环的执行次数，这点很像 Blowfish 算法的 cost 参数。默认的循环次数是 5000，最小是 1000，最大是 999,999,999。超出这个范围的 N 将会被转换为最接近的值。 

```php
<?php
$str = "eyes";
echo crypt($str);
echo "<br>";
echo crypt($str, "eyes");
echo "<hr>";
if (CRYPT_STD_DES) {
    echo "标准DES算法: " . crypt($str, "eyes");
    echo "<br>";
    echo "标准DES算法: " . crypt($str, "eyes");
}
echo "<hr>";
if (CRYPT_MD5) {
    echo "MD5 散列: " . crypt($str, '$1$abcd$');
    echo "<br>";
    echo "MD5 散列: " . crypt($str, '$1$abcd$');
}
echo "<hr>";
if (CRYPT_SHA256) {
    echo "SHA-256 算法: " . crypt($str, '$6$1sas515');
    echo "<br>";
    echo "SHA-256 算法: " . crypt($str, '$6$1sas515');
}
echo "<hr>";
if (CRYPT_SHA512) {
    echo "SHA-512 算法: " . crypt($str, '$6$1sa151acacsas515');
    echo "<br>";
    echo "SHA-512 算法: " . crypt($str, '$6$1sa151acacsas515');
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/edf3ab3ed8344e8ab8a399781fcedc06.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
<!--
# 五：URL编码加密技术
对称加密，是可逆的
# 六：Base64编码加密技术
对称加密，是可逆的
# 七：自定义加密算法
-->
