---
title: thinkphp的数据库操作(上)
date:  2021-08-27 15:51:15
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/8.jpg
tags: 
  - 数据库
  - thinkphp
categories: PHP
---
# 一：连接数据库与模型初探
ThinkPHP 采用内置抽象层将不同的数据库操作进行封装处理，数据抽象层基于 PDO 模式，无须针对不同的数据库编写相应的代码。使用数据库的第一步，就是连接数据库，在根目录的 config 下的 database.php 可以设置数据库连接信息，大部分系统已经给了默认值，你只需要修改和填写需要的值即可。
![在这里插入图片描述](https://img-blog.csdnimg.cn/cd44f832fe144d8b838a86b11d60fad1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)其中default配置用于设置默认使用的数据库连接配置。connections配置具体的数据库连接信息，default配置参数定义的连接配置必须要存在。
<table><thead><tr><th>type</th><th>数据库</th></tr></thead><tbody><tr><td>mysql</td><td>MySQL</td></tr><tr><td>sqlite</td><td>SqLite</td></tr><tr><td>pgsql</td><td>PostgreSQL</td></tr><tr><td>sqlsrv</td><td>SqlServer</td></tr><tr><td>mongo</td><td>MongoDb</td></tr><tr><td>oracle</td><td>Oracle</td></tr></tbody></table>

下面是默认支持的数据库连接信息：
<table><thead><tr><th>参数名</th><th>描述</th><th>默认值</th></tr></thead><tbody><tr><td>type</td><td>数据库类型</td><td>无</td></tr><tr><td>hostname</td><td>数据库地址</td><td>127.0.0.1</td></tr><tr><td>database</td><td>数据库名称</td><td>无</td></tr><tr><td>username</td><td>数据库用户名</td><td>无</td></tr><tr><td>password</td><td>数据库密码</td><td>无</td></tr><tr><td>hostport</td><td>数据库端口号</td><td>无</td></tr><tr><td>dsn</td><td>数据库连接dsn信息</td><td>无</td></tr><tr><td>params</td><td>数据库连接参数</td><td>空</td></tr><tr><td>charset</td><td>数据库编码</td><td>utf8</td></tr><tr><td>prefix</td><td>数据库的表前缀</td><td>无</td></tr><tr><td>deploy</td><td>数据库部署方式:0 集中式(单一服务器),1 分布式(主从服务器)</td><td>0</td></tr><tr><td>rw_separate</td><td>数据库读写是否分离 主从式有效</td><td>false</td></tr><tr><td>master_num</td><td>读写分离后 主服务器数量</td><td>1</td></tr><tr><td>slave_no</td><td>指定从服务器序号</td><td>无</td></tr><tr><td>fields_strict</td><td>是否严格检查字段是否存在</td><td>true</td></tr><tr><td>fields_cache</td><td>是否开启字段缓存</td><td>false</td></tr><tr><td>schema_cache_path</td><td>字段缓存目录</td><td>无</td></tr><tr><td>trigger_sql</td><td>是否开启SQL监听</td><td>true</td></tr><tr><td>auto_timestamp</td><td>自动写入时间戳字段</td><td>false</td></tr><tr><td>query</td><td>指定查询对象</td><td>think\db\Query</td></tr></tbody></table>
我们还可以调用Db::connect方法动态配置数据库连接信息，例如：

```php
\think\facade\Db::connect('demo')
	->table('user')
    ->find();
```
connect方法必须在查询的最开始调用，而且必须紧跟着调用查询方法，否则可能会导致部分查询失效或者依然使用默认的数据库连接。且动态连接数据库的connect方法仅对当次查询有效。这种方式的动态连接和切换数据库比较方便，经常用于多数据库连接的应用需求。

对于本地测试，会优先采用.env 的配置信息，可以通过删除改变.env 的配置，或删除.env 来验证 database 的执行优先级，操作中我们和 database 配置对应上即可：
![在这里插入图片描述](https://img-blog.csdnimg.cn/d736bd28493c415eab2c80c7c9be3630.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
现在来测试有没有成功连接：

![在这里插入图片描述](https://img-blog.csdnimg.cn/59b1812e8c6c491c8bca013ed8d5a3b1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 二：查询构造器(基础)
在学习查询之前可以先了解一下一个新方法：`Db::getLastSql()`，该方法可以返回最近一条SQL查询的原生语句。我在这里的演示是使用了我的myemployees库内容如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/7e016d4ca9fe47aea82485b8367ea1d9.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)![在这里插入图片描述](https://img-blog.csdnimg.cn/c38a4efe94044ef880f1322879ee95ab.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 1) 查询数据
> 查询单个数据

查询单个数据一般使用find方法。如果没有任何的查询条件，并且也没有调用order方法的话 ，find查询不会返回任何结果。

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result = json(Db::table('employees')->where('employee_id', 100)->find());
        return Db::getLastSql() . '<br>' . $result;
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/4ebf5b70bb134ec58d4a9541ab7b706c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)find方法查询结果不存在，返回 null，否则返回结果数组，如果希望查询数据不存在的时候返回空数组，可以如下修改：

```php
$result = Db::table('employees')->where('employee_id', 99)->findOrEmpty();
```
如果希望在没有找到数据后抛出异常可以如下使用，如果没有查找到数据，则会抛出一个`think\db\exception\DataNotFoundException`异常。
```php
$result = Db::table('employees')->where('employee_id', 99)->findOrFail();
```

> 查询数据集

查询多个数据（数据集）使用select方法：
![在这里插入图片描述](https://img-blog.csdnimg.cn/ff8e7968724e4f0a9e2187b45bf6cad6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
select 方法查询结果是一个数据集对象，如果需要转换为数组可以如下使用，但一般DB操作返回是默认是数组，这种情况下调用toArray()会报错，别问我是怎么知道的。。。。

```php
$result = Db::table('employees')->where('employee_id', 102)->select()->toArray();
```
如果希望在没有查找到数据后抛出异常可以如下使用，如果没有查找到数据，同样也会抛出一个`think\db\exception\DataNotFoundException`异常
```php
$result = Db::table('employees')->where('employee_id', 102)->select()->selectOrFail();
```
如果设置了数据表前缀参数的话，可以使用name替代table，如果表名为"tp_user"，然后在数据库设置那里添加了表前缀"tp_"，那么查询时可以用`name('user')`替代`table('tp_user')`。如果你的数据表没有设置表前缀的话，那么name和table方法效果一致。

> 值和列查询

查询某个字段的值可以用value()，如果不存在则返回null，如果有多个结果则只返回第一个。
![在这里插入图片描述](https://img-blog.csdnimg.cn/d377f87160db4d0bb7580c4200edcbad.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)查询某一列的值可以用column()，第一个参数是返回的值，第二个参数的值作为索引。
![在这里插入图片描述](https://img-blog.csdnimg.cn/54dd78da1f8b4f779094a43f4a5c4244.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 数据分批处理

如果处理的数据量巨大，成百上千那种，一次性读取有可能会导致内存开销过大，为了避免内存处理太多数据出错，可以使用 chunk()方法分批处理数据，该方法一次获取结果集的一小块，然后填充每一小块数据到要处理的闭包，该方法在编写处理大量数据库记录的时候非常有用。比如，我们可以全部员工表数据进行分批处理，每次处理 3 个员工记录，先展示chunk分批次处理的特性。

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        // function传入的$data代表所有数据
        $result = Db::table('employees')->chunk(3, function ($data){
            foreach ($data as $data) {
                dump($data);
            }
            echo 1;
        });
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/0b3f3d56b9744a13bf68a75e141455d6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果要给员工计算年薪，那就是 月薪\*12\*(1+奖金率)，那样就可以通过如下方法计算了：

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        // function传入的$data代表所有数据
        $result = Db::table('employees')->chunk(3, function ($data){
            foreach ($data as $data) {
                if (isset($data['commission_pct'])) {
                    echo $data['employee_id'] . ' ' . (12*$data['salary']*(1+$data['commission_pct'])) . "<br>";
                } else {
                    echo $data['employee_id'] . ' ' . (12*$data['salary']) . "<br>";
                }
            }
            echo 1 . "<br>";
        });
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/0fcaf7059739488581d3ac04cad12dd4.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)chunk方法的处理默认是根据主键查询，支持指定字段，并且支持指定处理数据的顺序。

```php
Db::table('think_user')->chunk(100, function($users) {
    // 处理结果集...
    return false;
},'create_time', 'desc');
```
> 游标查询

可以利用游标查询功能，可以大幅度减少海量数据的内存开销，它利用了 PHP 生 成器特性。每次查询只读一行，然后再读取时，自动定位到下一行继续读取，cursor方法返回的是一个生成器对象。

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result = Db::table('employees')->cursor();
        foreach ($result as $e){
            echo $e['last_name'] . "<br>";
        };
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/0e364407ab294e77a1d20b4a93f6b372.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2) 添加数据
> 单数据新增

单数据新增一般使用insert()方法插入，如果新增成功会返回一个1，没有指定的列的值默认为null。

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $data = [
            'employee_id'   =>  '99',
            'first_name'    =>  'eyes',
            'last_name'     =>  '++'
        ];
        return Db::table('employees')->insert($data);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/52c4cfeee5db469b92d29e9adb3fe4f0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果你添加一个不存在的字段数据，会抛出一个异常 Exception，如果想强行新增抛弃不存在的字段数据，则使用 strick(false)方法，忽略异常：

```php
return Db::table('employees')->strict(false)->insert($data);
```

我采用的数据库是 mysql，可以支持 replace 写入，insert 和 replace 写入的区别，前者遇到表中存在主键相同则报错，后者则修改。另外，使用 insertGetId()方法，可以在新增成功后返回当前数据 ID

> 批量数据新增

使用 insertAll()方法，可以添加二维数组批量新增数据，但要保持数组结构一致，成功则返回增加的行数

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $data =[
            [
                'employee_id'   =>  '98',
                'first_name'    =>  '虚哥',
                'last_name'     =>  'xx'
            ],
            [
                'employee_id'   =>  '97',
                'first_name'    =>  '鸡哥',
                'last_name'     =>  'jj'
            ]
        ];
        return Db::table('employees')->insertAll($data);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/d18696c816524dd9b97d10f238ec1bc5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
如果是mysql数据库则可以使用replay()批量添加或修改：

```php
return Db::table('employees')->replay()->insertAll($data);
```
如果批量插入的数据比较多，可以指定分批插入，使用limit方法指定每次插入的数量限制:

```php
//分批次写入，每次最多100条数据
Db::table('employees')
  ->replay()
  ->limit(100)
  ->insertAll($data);
```

> save()新增

save()方法是一个通用方法，可以自行判断是新增还是修改(更新)数据，判断的依据是是否存在主键，不存在即新增。用法如insert，此处略。

## 3) 更新数据
更新数据可以使用save()方法或者update()方法，如果修改数据包括了主键信息，则可以省略where条件

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $data =[
            'first_name'    =>  '东哥',
            'last_name'     =>  'hh'
        ];
        return Db::table('employees')
            ->where('employee_id', 97)
            ->save($data);
//        支持使用data方法传入要更新的数据，如果update方法和data方法同时传入更新数据，则以update方法为准。
//        return Db::table('employees')
//        ->where('employee_id', 97)
//        ->data($data)
//        ->save();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/ae7909c56009487ebaa25814d5ce8d71.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果要更新的数据需要使用SQL函数或者其它字段，可以使用下面的方式:

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        return Db::table('employees')
            ->where('employee_id', 99)
            ->exp('first_name', 'UPPER(first_name)')
            ->update();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/975d89407a5940cebb11d715a03ea5bf.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
可以使用inc/dec方法自增或自减一个字段的值（ 如不加第二个参数，默认步长为1）。

```php
// score 字段加 1
Db::table('employees')
    ->where('employee_id', 1)
    ->inc('salary')
    ->update();

// score 字段加 5
Db::table('employees')
    ->where('employee_id', 1)
    ->inc('salary', 5)
    ->update();

// score 字段减 1
Db::table('employees')
    ->where('employee_id', 1)
    ->dec('salary')
    ->update();

// score 字段减 5
Db::table('employees')
    ->where('employee_id', 1)
    ->dec('salary', 5)
    ->update();
```

## 4) 删除数据
+ 极简删除可以根据主键直接删除，删除成功返回影响行数，否则 0； 
`Db::table('employees')->delete(100); `
+ 根据主键，还可以删除多条记录； 
`Db::table('employees')->delete([97,98,99]); `
+ 正常情况下，通过 where()方法来删除； 
`Db::table('employees')->where('id', 100)->delete(); `
+ 通过 true 参数删除数据表所有数据 
`Db::table('employees')->delete(true);`

一般情况下，业务数据不建议真实删除数据，系统提供了软删除机制（模型中使用软删除更为方便），useSoftDelete方法表示使用软删除，并且指定软删除字段为delete_time，写入数据为当前的时间戳。。

```php
// 软删除数据 使用delete_time字段标记删除
Db::table('employees')
	->where('id', 100)
	->useSoftDelete('delete_time',time())
    ->delete();
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/04e3f85a3e394a4e888ca67684f43ac6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 5) 查询表达式
查询表达式支持大部分的SQL查询语法，也是ThinkPHP查询语言的精髓，查询表达式的使用格式：`where('字段名','查询表达式','查询条件')`。除了where方法外，还可以支持whereOr，用法是一样的。为了更加方便查询，大多数的查询表达式都提供了快捷查询方法。
<table><thead><tr><th>表达式</th><th>含义</th><th>快捷查询方法</th></tr></thead><tbody><tr><td>=</td><td>等于</td><td></td></tr><tr><td>&lt;&gt;</td><td>不等于</td><td></td></tr><tr><td>&gt;</td><td>大于</td><td></td></tr><tr><td>&gt;=</td><td>大于等于</td><td></td></tr><tr><td>&lt;</td><td>小于</td><td></td></tr><tr><td>&lt;=</td><td>小于等于</td><td></td></tr><tr><td>[NOT] LIKE</td><td>模糊查询</td><td><code>whereLike/whereNotLike</code></td></tr><tr><td>[NOT] BETWEEN</td><td>（不在）区间查询</td><td><code>whereBetween/whereNotBetween</code></td></tr><tr><td>[NOT] IN</td><td>（不在）IN 查询</td><td><code>whereIn/whereNotIn</code></td></tr><tr><td>[NOT] NULL</td><td>查询字段是否（不）是NULL</td><td><code>whereNull/whereNotNull</code></td></tr><tr><td>[NOT] EXISTS</td><td>EXISTS查询</td><td><code>whereExists/whereNotExists</code></td></tr><tr><td>[NOT] REGEXP</td><td>正则（不）匹配查询（仅支持Mysql）</td><td></td></tr><tr><td>[NOT] BETWEEN TIME</td><td>时间区间比较</td><td>whereBetweenTime</td></tr><tr><td>&gt; TIME</td><td>大于某个时间</td><td><code>whereTime</code></td></tr><tr><td>&lt; TIME</td><td>小于某个时间</td><td><code>whereTime</code></td></tr><tr><td>&gt;= TIME</td><td>大于等于某个时间</td><td><code>whereTime</code></td></tr><tr><td>&lt;= TIME</td><td>小于等于某个时间</td><td><code>whereTime</code></td></tr><tr><td>EXP</td><td>表达式查询，支持SQL语法</td><td><code>whereExp</code></td></tr><tr><td>find in set</td><td>FIND_IN_SET查询</td><td><code>whereFindInSet</code></td></tr></tbody></table>

> 比较查询

+ 查询表达式支持大部分常用的 SQL 语句，语法格式如下： 
`where('字段名','查询表达式','查询条件');` 
+ 在查询数据进行筛选时，我们采用 where()方法，比如 id=80； 
`Db::name('user')->where('id', 80)->find();`
`Db::name('user')->where('id','=',80)->find();` 
+ 使用<>、>、<、>=、<=可以筛选出各种符合比较值的数据列表； `Db::name('user')->where('id','>',80)->select();`

> 区间查询

+ 使用 like 表达式进行模糊查询； 
`Db::name('user')->where('email','like','xiao%')->select();` 
+ like 表达式还可以支持数组传递进行模糊查询； 
`Db::name('user')->where('email','like',['xiao%','wu%'], 'or')->select(); `
`SELECT * FROM tp_user WHERE (email LIKE xiao% OR email LIKE 'wu%')` 
+ like 表达式具有两个快捷方式 whereLike()和 whereNoLike()； `Db::name('user')->whereLike('email','xiao%')->select(); `
`Db::name('user')->whereNotLike('email','xiao%')->select(); `
+ between 表达式具有两个快捷方式 whereBetween()和 whereNotBetween()； `Db::name('user')->where('id','between','19,25')->select(); `
`Db::name('user')->where('id','between',[19, 25])->select();`
`Db::name('user')->whereBetween('id','19,25')->select();` `Db::name('user')->whereNotBetween('id','19,25')->select();` 
+ in 表达式具有两个快捷方式 whereIn()和 whereNotIn()； 
`Db::name('user')->where('id','in', '19,21,29')->select();`
`Db::name('user')->where('id','in', [19, 21, 29])->select();`
`Db::name('user')->whereIn('id','19,21,29')->select();` 
`Db::name('user')->whereNotIn('id','19,21,29')->select();`
+ null 表达式具有两个快捷方式 whereNull()和 whereNotNull()； `Db::name('user')->where('uid','null')->select();`
`Db::name('user')->where('uid','not null')->select();`
`Db::name('user')->whereNull('uid')->select();`
`Db::name('user')->whereNotNull('uid')->select();`

> EXP查询

EXP表达式支持更复杂的查询：

```php
Db::name('user')->where('id','in','1,3,8')->select();
```
可以改成：

```php
Db::name('user')->where('id','exp',' IN (1,3,8) ')->select();
```
exp查询的条件不会被当成字符串，所以后面的查询条件可以使用任何SQL支持的语法，包括使用函数和字段名称。因此推荐使用whereExp方法查询。

```php
Db::name('user')->whereExp('id', 'IN (1,3,8) ')->select();
```

# 三：查询构造器(链式)
## 1)．查询规则 
1. 前面课程中我们通过指向符号“->”多次连续调用方法称为：链式查询
2. 当 Db::name('user')时，返回查询对象(Query)，即可连缀数据库对应的方法
3. 而每次执行一个数据库查询方法时，比如 where()，还将返回查询对象(Query)
4. 只要还是数据库对象，那么就可以一直使用指向符号进行链式查询
5. 再利用 find()、select()等方法返回数组(Array)或数据集对象(Colletion)
6. 而 find()和 select()是结果查询方法（放在最后），并不是链式查询方法
`Db::name('user')->where('id', 27)->order('id', 'desc')->find() `
7. 除了查询方法可以使用链式连贯操作，CURD 操作也可以使用（后续课程研究)

## 2)．更多查询 
1. 如果多次使用数据库查询，那么每次静态创建都会生成一个实例，造成浪费； 
2. 我们可以把对象实例保存下来，再进行反复调用即可； 
`$userQuery = Db::name('user'); `
`$dataFind = $userQuery->where('id', 27)->find();`
`$dataSelect = $userQuery->select(); `
3. 当同一个对象实例第二次查询后，会保留第一次查询的值；
`$data1 = $userQuery->order('id', 'desc')->select();`
`$data2 = $userQuery->select(); `
`return Db::getLastSql(); `
`SELECT * FROM tp_user ORDER BY id DESC` 
4. 使用 removeOption()方法，可以清理掉上一次查询保留的值； `$userQuery->removeOption('where')->select();`
## 3). 链式查询方法
> where

+ 表达式查询，即where()方法的基础查询方式

```php
 $result=Db::table('employees')->where('salary', '<', '10000')->select();
```

+ 关联数组查询，通过键值对来数组键值对匹配的查询方式

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where([
                      'job_id'     =>      'IT_PROG',
                      'salary'     =>       6000
                  ])
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/c6e81a82a067468d85edf632983316b1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
+ 索引数组查询，通过数组里的数组拼装方式来查询

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where([
                      ['employee_id', '<', 110],
                      ['salary', '<', 10000]
                  ])
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8b76eb78049b4d6dad35caa45893e240.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
+ 将复杂的数组组装后，通过变量传递，将增加可读性

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $map[] = ['employee_id', '<', 110];
        $map[] = ['salary', 'in', [6000, 4200, 24000]];
        $result=Db::table('employees')
                  ->where($map)
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b8f85b61ddf84f46913615907af2dd02.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
+ 字符串形式传递，简单粗暴的查询方式，whereRaw()支持复杂字符串格式，也支持SQL预处理模式

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->whereRaw("employee_id<110 AND salary IN (24000, 6000, 4200)")
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/37972e97295b48f5857c9ab221530b84.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> field

+ 使用 field()方法，可以指定要查询的字段

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where('employee_id', '<', 105)
                  ->field('employee_id, first_name, salary')
//                  ->field(['employee_id', 'first_name', 'salary']) 或者这种数组形式
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/53753907ba864b4abd21b469bbb441f3.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 使用 field()方法，给指定的字段设置别名

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where('employee_id', '<', 105)
                  ->field('employee_id as id, first_name as name, salary as 薪水')
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b2d7c18571cb45ca8593485641e7688f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 在 fieldRaw()方法里，可以直接给字段设置 MySQL 函数

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where('employee_id', '<', 105)
                  ->field('job_id, LENGTH(job_id)')
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/be296325d5d340e3905f0345e6166afd.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 使用 field(true)的布尔参数，可以显式的查询获取所有字段，而不是*

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
                  ->where('employee_id', '<', 105)
                  ->field(true)
                  ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/5409f59a808046cca1f56d89db7fddfc.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 使用 withoutField()方法中字段排除，可以屏蔽掉想要不显示的字段

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->where('employee_id', '<', 105)
            ->withoutField('email, phone_number')
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/92e26f2fee2944489ba4911ccd5a022e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 使用 field()方法在新增时，验证字段的合法性

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->where('employee_id', '<', 105)
            ->field('employee_id, first_name, last_name')
            ->insert([
                'first_name'       =>      '济钢',
                'last_name'        =>       'aa'
            ]);
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/59a075a1239b475ca2f07cd15f66087b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> alias

使用 alias()方法，给数据库起一个别名:
![在这里插入图片描述](https://img-blog.csdnimg.cn/5a91a2f4743f47d09edfd0eea55b18c6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> limit

+ 使用 limit()方法，限制获取输出数据的个数

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name')
            ->limit(5)
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/c963644518d141778524a4ac9fc26461.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 分页模式，即传递两个参数，比如从第 3 条开始显示 5 条 limit(2,5)

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name')
            ->limit(2, 5)
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/7751a3f98ed04a8d8aca6e32bf5a709f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> page

page()分页方法，优化了 limit()方法，无须计算分页条数

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name')
            ->page(2, 5)  // 每页显示五条，第二页
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b324a307f6244e7292f6d7011b8572f7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> order

+ 使用 order()方法，可以指定排序方式，没有指定第二参数，默认 asc

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name, salary')
            ->order('salary', 'desc')
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/04567b9d8c4a4798ba9efce62d8a3d74.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 支持数组的方式，对多个字段进行排序

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name, salary')
            // 按工资升序，工资相同则按员工编号降序
            ->order([
                'salary'        =>       'asc',
                'employee_id'   =>       'desc'
            ])
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/f4c3390934fb49769cf91aaad46a8654.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 使用 orderRaw()方法，支持排序的时候指定 MySQL 函数

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('employee_id, last_name, salary')
            ->orderRaw('length(first_name) DESC')
            ->select();
        return Db::getLastSQL();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/ff956ec3a0be4248af88cd6179f0ea77.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> group

+ 使用 group()方法，给性别不同的人进行 price 字段的总和统计

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('job_id, SUM(salary)')
            ->group('job_id')
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/574cfa3bf1f24df7bb83c470a4f413f1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

+ 也可以进行多字段分组统计

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('job_id, manager_id, SUM(salary)')
            ->group('job_id, manager_id')
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/c27a8e90bf174de49fdd582ff55dcd3a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> having

使用 group()分组之后，再使用 having()进行筛选

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $result=Db::table('employees')
            ->field('job_id, manager_id, SUM(salary)')
            ->group('job_id, manager_id')
            ->having('SUM(salary)>10000')
            ->select();
        return json($result);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/4fb4f87e747a4805941d6f85cfab57a5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 四：查询构造器(进阶)
## 1) 聚合查询
在应用中我们经常会用到一些统计数据，例如当前所有（或者满足某些条件）的用户数、所有用户的最大积分、用户的平均成绩等等，ThinkPHP为这些统计操作提供了一系列的内置方法，包括以下部分：
<table><thead><tr><th>方法</th><th>说明</th></tr></thead><tbody><tr><td>count</td><td>统计数量，参数是要统计的字段名（可选）</td></tr><tr><td>max</td><td>获取最大值，参数是要统计的字段名（必须）</td></tr><tr><td>min</td><td>获取最小值，参数是要统计的字段名（必须）</td></tr><tr><td>avg</td><td>获取平均值，参数是要统计的字段名（必须）</td></tr><tr><td>sum</td><td>获取总分，参数是要统计的字段名（必须）</td></tr></tbody></table>

1. 使用 count()方法，可以求出所查询数据的数量
`Db::name('user')->count();` 
2. count()可设置指定 id，比如有空值(Null)的 uid，不会计算数量； 
`Db::name('user')->count('uid');` 
3. 使用 max()方法，求出所查询数据字段的最大值； 
`Db::name('user')->max('price');`
4. 如果 max()方法，求出的值不是数值，则通过第二参数强制转换； 
`Db::name('user')->max('price', false);` 
5. 使用 min()方法，求出所查询数据字段的最小值，也可以强制转换； 
`Db::name('user')->min('price');`
6. 使用 avg()方法，求出所查询数据字段的平均值； 
`Db::name('user')->avg('price');` 
7. 使用 sum()方法，求出所查询数据字段的总和； 
`Db::name('user')->sum('price');`
## 2) 分页查询

## 3) 时间查询
> 传统方式

+ 可以使用>、<、>=、<=来筛选匹配时间的数据； 
`Db::name('user')->where('create_time', '>', '2018-1-1')->select();` 
+ 可以使用 between 关键字来设置时间的区间
`Db::name('user')->where('create_time', 'between', ['2018-1-1', '2019-12-31'])->select();` 
`Db::name('user')->where('create_time', 'not between', ['2018-1-1', '2019-12-31'])->select();`

> 快捷方式

+ 时间查询的快捷方法为 whereTime()，直接使用>、<、>=、<=； `Db::name('user')->whereTime('create_time', '>', '2018-1-1')->select();`
+ 快捷方式也可以使用 between 和 not between；
`Db::name('user')->whereBetween('create_time', ['2018-1-1', '2019-12-31'])->select();` 
+ 还有一种快捷方式为：whereBetweenTime()和 whereNotBetweenTime()； `Db::name('user')->whereBetweenTime('create_time', '2018-1-1', '2019-12-31')->select(); `
+ 默认的大于>，可以省略；
`Db::name('user')->whereTime('create_time', '2018-1-1')->select();`

> 固定查询

+ 使用 whereYear 查询今年的数据、去年的数据和某一年的数据
`Db::name('user')->whereYear('create_time')->select(); `
`Db::name('user')->whereYear('create_time', 'last year')->select(); `
`Db::name('user')->whereYear('create_time', '2016')->select();`
+ 使用 whereMonth 查询当月的数据、上月的数据和某一个月的数据
`Db::name('user')->whereMonth('create_time')->select(); `
`Db::name('user')->whereMonth('create_time', 'last month')->select(); `
`Db::name('user')->whereMonth('create_time', '2016-6')->select();`
+ 使用 whereDay 查询今天的数据、昨天的数据和某一个天的数据
`Db::name('user')->whereDay('create_time')->select(); `
`Db::name('user')->whereDay('create_time', 'last day')->select(); `
`Db::name('user')->whereDay('create_time', '2016-6-27')->select();`
> 其它查询

+ 查询指定时间的数据，比如两小时内的
`Db::name('user')->whereTime('create_time', '-2 hours')->select();`
+ 查询两个时间字段时间有效期的数据，比如会员开始到结束的期间
`Db::name('user')->whereBetweenTimeField('start_time', 'end_time')->select();`
## 4) 高级查询
> 快捷查询

快捷查询方式是一种多字段相同查询条件的简化写法，可以进一步简化查询条件的写法，在多个字段之间用|分割表示OR查询，用&分割表示AND查询，可以实现下面的查询，例如：

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        Db::table('employees')
            ->where('first_name|last_name', 'like', '%a%')
            ->where('employee_id&manager_id', '>', 103)
            ->select();
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/205fa6298d764be481ad99727c824e37.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)![在这里插入图片描述](https://img-blog.csdnimg.cn/0f756f69b62a4364bafb1d7a21130459.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> 闭包查询

```php
<?php
namespace app\controller;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $jobId='IT_PROG';
        $salary=11000;

        $res=Db::table('employees')->where(function ($query) use($jobId, $salary) {
            $query->where('job_id', $jobId)
                  ->whereOr('salary', '>', $salary);
        })->select();

        return json($res);
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/b8981d5bc8f34c668af9b2015c0e8617.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 字符串条件查询

对于一些实在复杂的查询，也可以直接使用原生SQL语句进行查询，例如:

```php
Db::table('think_user')
    ->whereRaw('id > 0 AND name LIKE "thinkphp%"')
    ->select();
```
为了安全起见，我们可以对字符串查询条件使用参数绑定，例如：
```php
Db::table('think_user')
    ->whereRaw('id > :id AND name LIKE :name ', ['id' => 0, 'name' => 'thinkphp%'])
    ->select();
```

> 快捷方法

系统封装了一系列快捷方法，用于简化查询
<table><thead><tr><th>方法</th><th>作用</th></tr></thead><tbody><tr><td><code>whereOr</code></td><td>字段OR查询</td></tr><tr><td><code>whereXor</code></td><td>字段XOR查询</td></tr><tr><td><code>whereNull</code></td><td>查询字段是否为Null</td></tr><tr><td><code>whereNotNull</code></td><td>查询字段是否不为Null</td></tr><tr><td><code>whereIn</code></td><td>字段IN查询</td></tr><tr><td><code>whereNotIn</code></td><td>字段NOT IN查询</td></tr><tr><td><code>whereBetween</code></td><td>字段BETWEEN查询</td></tr><tr><td><code>whereNotBetween</code></td><td>字段NOT BETWEEN查询</td></tr><tr><td><code>whereLike</code></td><td>字段LIKE查询</td></tr><tr><td><code>whereNotLike</code></td><td>字段NOT LIKE查询</td></tr><tr><td><code>whereExists</code></td><td>EXISTS条件查询</td></tr><tr><td><code>whereNotExists</code></td><td>NOT EXISTS条件查询</td></tr><tr><td><code>whereExp</code></td><td>表达式查询</td></tr><tr><td><code>whereColumn</code></td><td>比较两个字段</td></tr></tbody></table>

> 动态查询

查询构造器还提供了动态查询机制，用于简化查询条件
<table><thead><tr><th>动态查询</th><th>描述</th></tr></thead><tbody><tr><td><code>whereFieldName</code></td><td>查询某个字段的值</td></tr><tr><td><code>whereOrFieldName</code></td><td>查询某个字段的值</td></tr><tr><td><code>getByFieldName</code></td><td>根据某个字段查询</td></tr><tr><td><code>getFieldByFieldName</code></td><td>根据某个字段获取某个值</td></tr></tbody></table>

```php
// 根据邮箱（email）查询用户信息
$user = Db::table('user')
	->whereEmail('thinkphp@qq.com')
    ->find();

// 根据昵称（nick_name）查询用户
$email = Db::table('user')
    ->whereNickName('like', '%流年%')
    ->select();
    
// 根据邮箱查询用户信息
$user = Db::table('user')
    ->getByEmail('thinkphp@qq.com');
    
// 根据昵称（nick_name）查询用户信息
$user = Db::table('user')
    ->field('id,name,nick_name,email')
    ->getByNickName('流年');
    
// 根据邮箱查询用户的昵称
$nickname = Db::table('user')
    ->getFieldByEmail('thinkphp@qq.com', 'nick_name');
    
// 根据昵称（nick_name）查询用户邮箱
$email = Db::table('user')
    ->getFieldByNickName('流年', 'email');

```

## 5) 子查询
使用 fetchSql()方法，可以设置不执行 SQL，而返回 SQL 语句，默认 true；
`Db::name('user')->fetchSql(true)->select();`
使用 buildSql()方法，也是返回 SQL 语句，不需要再执行 select()，且有括号
`Db::name('user')->buildSql(true);`
结合以上方法，我们实现一个子查询：

```php
$subQuery = Db::table('think_user')
    ->field('id,name')
    ->where('id', '>', 10)
    ->buildSql();
Db::table($subQuery . ' a')
    ->where('a.name', 'like', 'thinkphp')
    ->order('id', 'desc')
    ->select();

```

子查询还有闭包模式，`IN/NOT IN`和`EXISTS/NOT EXISTS`之类的查询可以直接使用闭包作为子查询，例如：

```php
Db::table('think_user')
    ->where('id', 'IN', function ($query) {
        $query->table('think_profile')->where('status', 1)->field('id');
    })
    ->select();
```

## 6) 原生查询
注意：V6.0.3+版本开始，原生查询仅支持Db类操作，不支持在模型中调用原生查询方法（包括query和execute方法）。
Db类支持原生SQL查询操作，主要包括query方法和execute方法

query方法用于执行SQL查询操作，返回查询结果数据集（数组）。

```php
Db::query("select * from think_user where status=:id", ['id' => 1]);
```
execute用于更新和写入数据的sql操作，如果数据非法或者查询错误则返回false，否则返回影响的记录数。

```php
Db::execute("update think_user set name='thinkphp' where status=1");
```
支持在原生查询的时候使用参数绑定，包括问号占位符或者命名占位符，例如：

```php
Db::query("select * from think_user where id=? AND status=?", [8, 1]);
// 命名绑定
Db::execute("update think_user set name=:name where status=:status", ['name' => 'thinkphp', 'status' => 1]);
```
