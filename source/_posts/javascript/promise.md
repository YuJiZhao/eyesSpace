---
title: Promise的一些使用
date:  2021-08-17 15:25:46
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/1.jpg
tags: 
  - promise
  - ES6
categories: javascript
---

![在这里插入图片描述](https://img-blog.csdnimg.cn/7a52cf65d09340d1aa60832bbadf3ffb.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rvbmdrb25neXU=,size_16,color_FFFFFF,t_70)
### 1). 封装AJAX GET请求

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
        // 封装一个函数sendAJAX发送ajax GET请求
        function sendAJAX(url) {
            return new Promise((resolve, reject)=>{
                const xhr = new XMLHttpRequest();
                xhr.open("GET", url);
                xhr.send();
                xhr.onreadystatechange = ()=>{
                    if(xhr.status >= 200 && xhr.status < 300) {
                        resolve(xhr.response);
                    } else {
                        reject(xhr.status);
                    }
                };
            });
        }

        // 使用该封装函数
        sendAJAX('https://api.apiopen.top/getJoke')
        .then(value => {
            console.log(value);
        }, reason => {
            console.log(reason);
        })
    </script>
</body>
</html>
```

### 2).抽奖案例

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
    <h2>Promise 抽奖模拟</h2>
    <button id="btn">点击抽奖</button>
    <script>
        // 生成从m到n的随机数
        let rand = (m, n) => {
            return Math.ceil(Math.random() * (n - m + 1)) + m - 1;
        }

        // 获取元素对象
        const btn = document.querySelector('#btn');
        btn.addEventListener('click', () => {
            const p = new Promise((resolve, reject) => {
                setTimeout(() => {
                    let n = rand(1, 100);
                    if (n <= 30) {
                        resolve(n);
                    } else {
                        reject(n);
                    }
                }, 500);
            });
            // 调用then方法
            p.then(value => {
                alert(`您中奖了！您的号码是：${value}`);
            }, reason => {
                alert(`谢谢惠顾！您的号码是：${reason}`);
            });
        })
    </script>
</body>

</html>
```

### 3). 解决回调地狱

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
        const p = new Promise((resolve, reject) => {
            resolve(1);
        }).then(value => { 
            console.log(value); // 1
            return value * 2;
        }).then(value => { 
            console.log(value); // 2
        }).then(value => { 
            console.log(value); // undefined
            return Promise.resolve('resolve');
        }).then(value => {
            console.log(value); // resolve
            return Promise.reject('reject');
        }).then(value => {
            console.log('resolve:' + value); //reject:reject
        }, err => {
            console.log('reject:' + err);
        });
    </script>
</body>

</html>
```

### 4). async与await

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
        function testAwait() {
            return new Promise((resolve) => {
                setTimeout(function () {
                    console.log("testAwait");
                    resolve();
                }, 1000);
            });
        }

        async function helloAsync() {
            await testAwait();
            console.log("helloAsync");
        }
        helloAsync();
        // testAwait
        // helloAsync
    </script>
</body>

</html>
```


