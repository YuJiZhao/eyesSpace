---
title: Web Stotage——本地储存详解及案例
date: 2021-06-11 11:38:05
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/29.jpg
tags: 
  - Stotage
categories: javascript
---

        本地存储：客户端数据的存储
        本地储存应用有用户临时登录信息，用户页面配置，当前临时信息等等
        一些东西用户希望能存起来，以便下次访问继续使用，但服务器没必要浪费空间去存那些数据，就可以使用本地储存
        本地存储的实现方式：
            cookie：一般最多储存4kB数据，每个HTTP请求都会被传送回服务器，使用上安全策略浏览器行为跟踪，这方法兼容性好
            Web Stotage：大约5MB，类似于key-value的储存方式
            Web SQL Database：使用SQL语句操作，IE和FF不支持
            Indexed Database：索引数据库，类似NoSQL，很强大，支持索引，事务处理和健壮的查询功能
            
        Web Storage
            会话储存（session storage）
            本地存储（local storage）
            会话储存与本地区别：
                两者不同之处在于localStorage里面存储的数据没有过期时间限制，
                而存储在sessionStorage里面的数据在页面会话结束时会被清除。
                页面会话在浏览器打开期间一直保持，并且重新加载或恢复页面仍会保持原来的页面会话。
                sessionStorage只能在同一个域的同一页面使用。
            Web Storage和cookie的区别：
                Web Storage的概念和cookie相似，区别是它是为了更大容量存储设计的。Cookie的大小是受限的，并且每次你请求一个新的页面的时候Cookie都会被发送过去，这样无形中浪费了带宽
                cookie需要指定作用域，不可以跨域调用。
                Web Storage拥有setItem,getItem,removeItem,clear等方法，不像cookie需要前端开发者自己封装setCookie，getCookie。
                但是Cookie也是不可以或缺的：Cookie的作用是与服务器进行交互，作为HTTP规范的一部分而存在 ，而Web Storage仅仅是为了在本地“存储”数据而生
            兼容性：
                浏览器的支持除了IE７及以下不支持外，其他标准浏览器都完全支持(ie及FF需在web服务器里运行)，
                值得一提的是IE总是办好事，例如IE7、IE6中的UserData其实就是javascript本地存储的解决方案。
                通过简单的代码封装可以统一到所有的浏览器都支持web storage。
            操作方法：
                属性：
                    Storage.length 只读属性
                    返回一个整数，表示储存在Storage对象中的数据项数量
                方法：
                    Storage.key() ：该方法接受一个数值n作为参数，并返回储存中的第n个键名。
                    Storage.getItem ：该方法接受一个键名作为参数，返回键名对应的值。
                    Storage.setItem ：该方法接受一个键名和值作为参数，将会把键值对添加到存储中，如果键名存在，则更新对应的值
                    Storage.removeItem ：该方法接受一个键名作为参数，并把该键名从存储中删除。
                    Storage.clear ：该方法会清空存储中的所有键名。
 
 
操作方法示例：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <script>
        // 存储数据
        localStorage.setItem("name", "张三");
        // 获得数据
        console.log(localStorage.getItem("name"));

        // 数据存储设置
        localStorage.name2 = "李四";
        localStorage['name3'] = "王五"
        localStorage.num = 123;
        localStorage.obj = {name: "麻子"}; // 对象的话只能存类型，不能存值

        // 获得数据
        console.log(localStorage.name2);
        console.log(localStorage["name3"]);
        console.log(localStorage.num);
        console.log(typeof localStorage.num); // string 类型，该种键值存储只能存字符串类型
        console.log(localStorage.obj);
        console.log(typeof localStorage.obj); //string 类型，理由同上
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/2021061110403649.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/20210611104306402.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)比较localStorage和sessionStorage的不同：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <script>
        localStorage.setItem('v1','localStorage');
        sessionStorage.setItem('v1','sessionStorage');
        console.log(localStorage.getItem('v1'));
        console.log(sessionStorage.getItem('v1'));
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611110239853.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/20210611110257919.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)![展示](https://img-blog.csdnimg.cn/20210611110311676.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
然后注释掉设置Stotage的语句，再次打开，会发现localStorage仍存在，sessionStorage已经不存在了，因为存储在sessionStorage里面的数据在页面会话结束时会被清除。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <script>
        // localStorage.setItem('v1','localStorage');
        // sessionStorage.setItem('v1','sessionStorage');
        console.log(localStorage.getItem('v1'));
        console.log(sessionStorage.getItem('v1'));
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611110507436.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)利用Web Stotage实现关闭页面后重新打开数据仍在。

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <textarea name="" id="mytxt" cols="30" rows="10"></textarea>
    <script>
        var mytxt = document.getElementById('mytxt');
        mytxt.onkeyup = function(){
            localStorage.setItem('mytxt',mytxt.value);
        }
        if(localStorage.getItem('mytxt')){
            mytxt.value = localStorage.getItem('mytxt');
        }
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/20210611111653768.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)利用Web Stotage实现跨页面更新内容：
页面一：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <textarea name="" id="mytxt" cols="30" rows="10"></textarea>
    <script>
        var mytxt = document.getElementById('mytxt');
        mytxt.onkeyup = function(){
            localStorage.setItem('mytxt',mytxt.value);
        }
        if(localStorage.getItem('mytxt')){
            mytxt.value = localStorage.getItem('mytxt');
        }
    </script>
</body>
</html>
```

页面二：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p id="demo"></p>
    <script>
        var demo = document.getElementById('demo');
        setInterval(function(){
            demo.innerHTML = localStorage.getItem('mytxt');
        },500)
    </script>
</body>
</html>
```
![展示](https://img-blog.csdnimg.cn/2021061111345927.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)