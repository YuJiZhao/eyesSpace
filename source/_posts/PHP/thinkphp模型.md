---
title: thinkphp模型
date: 2021-08-28 17:19:35
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/15.jpg
tags: 
  - thinkphp
  - 模型
categories: PHP
---
# 一：定义
> 模型定义

模型会自动对应数据表，模型类的命名规则是除去表前缀的数据表名称，采用驼峰法命名，并且首字母大写。
模型自动对应的数据表名称都是遵循小写+下划线规范，如果你的表名有大写的情况，必须通过设置模型的table属性。
<table><thead><tr><th>模型名</th><th>约定对应数据表（假设数据库的前缀定义是 <code>think_</code>）</th></tr></thead><tbody><tr><td>User</td><td>think_user</td></tr><tr><td>UserType</td><td>think_user_type</td></tr></tbody></table>

![在这里插入图片描述](https://img-blog.csdnimg.cn/7902f3ffed664a1e812ca60e9fc5b2d7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 模型设置

模型的默认主键为id，如果你没有使用id作为主键名，需要在模型中设置属性，常用的模型设置属性包括（以下属性都不是必须设置）：
<table><thead><tr><th>属性</th><th>描述</th></tr></thead><tbody><tr><td>name</td><td>模型名（相当于不带数据表前后缀的表名，默认为当前模型类名）</td></tr><tr><td>table</td><td>数据表名（默认自动获取）</td></tr><tr><td>suffix</td><td>数据表后缀（默认为空）</td></tr><tr><td>pk</td><td>主键名（默认为<code>id</code>）</td></tr><tr><td>connection</td><td>数据库连接（默认读取数据库配置）</td></tr><tr><td>query</td><td>模型使用的查询类名称</td></tr><tr><td>field</td><td>模型允许写入的字段列表（数组）</td></tr><tr><td>schema</td><td>模型对应数据表字段及类型</td></tr><tr><td>type</td><td>模型需要自动转换的字段及类型</td></tr><tr><td>strict</td><td>是否严格区分字段大小写（默认为true）</td></tr><tr><td>disuse</td><td>数据表废弃字段（数组）</td></tr></tbody></table>

设置方式如下：

```php
<?php
namespace app\model;
use think\Model;

class Employees extends Model
{
    // 设置模型名(相当于不带数据表前后缀的表名，默认为当前模型类名)
    protected $name = 'Employees';

    // 设置对应的表名
    protected $table = 'employees';

    // 设置主键(默认为id)
    protected $pk = 'employee_id';

    // 严格区分字段大小写
    protected $strict = true;
}
```
> 模型初始化

init必须是静态方法，并且只在第一次实例化的时候执行，并且只会执行一次
```php
protected static function init()
{
    //TODO:初始化内容
}
```

> 模型操作

在模型中除了可以调用数据库类的方法之外（换句话说，数据库的所有查询构造器方法模型中都可以支持），可以定义自己的方法，所以也可以把模型看成是数据库的增强版。模型的操作方法无需和数据库查询一样调用必须首先调用table或者name方法，因为模型会按照规则自动匹配对应的数据表，例如：

```php
Db::name('user')->where('id','>',10)->select();
```
改成模型操作的话就变成:

```php
User::where('id','>',10)->select();
```
虽然看起来是相同的查询条件，但一个最明显的区别是查询结果的类型不同。第一种方式的查询结果是一个（二维）数组，而第二种方式的查询结果是包含了模型（集合）的数据集。不过，在大多数情况下，这二种返回类型的使用方式并无明显区别。

模型操作和数据库操作的另外一个显著区别是模型支持包括获取器、修改器、自动时间写入在内的一系列自动化操作和事件，简化了数据的存取操作，自动帮你处理了一些原本需要手动处理的操作。

> 动态切换后缀

新版模型增加了一个数据表后缀属性，可以用于多语言或者数据分表的模型查询，省去为多个相同结构的表定义多个模型的麻烦(默认的数据表后缀可以在模型类里面直接定义suffix属性)。

模型提供了动态切换方法suffix和setSuffix

```php
// suffix方法用于静态查询
$blog = Blog::suffix('_en')->find();
$blog->name = 'test';
$blog->save();

// setSuffix用于动态设置
$blog = new Blog($data);
$blog->setSuffix('_en')->save();
```
>
# 二：模型字段
+ 模型的数据字段和表字段是对应关系，默认会自动获取，包括字段的类型
+ 自动获取会导致增加一次查询，如果在模型中配置字段信息，会减少内存开销
+ 可以在模型设置$schema 字段，明确定义字段信息，字段需要对应表**写完整**；
![在这里插入图片描述](https://img-blog.csdnimg.cn/94d8ff39fb9f4afc9489a26cbdd75c86.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
+ 字段类型的定义可以使用PHP类型或者数据库的字段类型都可以，字段类型定义的作用主要用于查询的参数自动绑定类型。
+ 时间字段尽量采用实际的数据库类型定义，便于时间查询的字段自动识别。如果是json类型直接定义为json即可
+ schema属性一旦定义，就必须定义完整的数据表字段类型。如果你只希望对某个字段定义需要自动转换的类型，可以使用type属性

```php
<?php
namespace app\model;

use think\Model;

class User extends Model
{
    // 设置字段自动转换类型
    protected $type = [
        'score'       => 'float',
    ];
}
```

# 三：新增
模型数据的新增和数据库的新增数据有所区别，数据库的新增只是单纯的写入给定的数据，而模型的数据写入会包含修改器、自动完成以及模型事件等环节。

> 通过数组添加数据

示例是批量新增，所以\$data是二维数组，增加单个数据只要换成一维数组就行了，另外，不要在同一个实例里面多次新增数据，如果确实需要多次新增，可以使用后面的静态方法处理。
```php
<?php
namespace app\controller;
use app\model\Employees;

class DbTest
{
    public function index()
    {
        $data=[
            [
                'employee_id'  =>  96,
                'first_name'   =>  '鸡哥',
                'last_name'    =>  'aa'
            ],
            [
                'employee_id'  =>  95,
                'first_name'   =>  '超哥',
                'last_name'    =>  'cc'
            ]
        ];
        $res=new Employees();
        return $res->saveAll($data);
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/2a6bf50dca55477799878af839a6071f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> replace写入

save方法可以支持replace写入:
```php
<?php
namespace app\controller;
use app\model\Employees;

class DbTest
{
    public function index()
    {
        $data=[
            'employee_id'  =>  94,
            'first_name'   =>  '李四',
            'last_name'    =>  'ab'
        ];
        $res=new Employees();
        return $res->replace()->save($data);
    }
}
```

> 获取自增ID

这里其实获取自增ID就是获取模型的主键，如果你的主键不是id，而是employee_id的话，其实获取自增ID就变成这样：

```php
<?php
namespace app\controller;
use app\model\Employees;

class DbTest
{
    public function index()
    {
        $data=[
            'first_name'   =>  '王五',
            'last_name'    =>  'df'
        ];
        $res=new Employees();
        $res->replace()->save($data);
        return $res->employee_id;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/d54cc562ba594dca9fd4e7ea89907a34.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 静态方法

还可以直接静态调用create方法创建并写入

```php
<?php
namespace app\controller;
use app\model\Employees;

class DbTest
{
    public function index()
    {
//        貌似create方法不支持批量添加数据
//        $data=[
//            [
//                'first_name'   =>  '赵六',
//                'last_name'    =>  'db'
//            ],
//            [
//                'first_name'   =>  '麻子',
//                'last_name'    =>  'cs'
//            ]
//        ];
        $data=[
            'first_name'   =>  '赵六',
            'last_name'    =>  'db'
        ];
        $res = Employees::create($data);
        return $res;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/cd13c48accbf42d6b5996409c60fc95e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
create方法默认会过滤不是数据表的字段信息，可以在第二个参数可以传入允许写入的字段列表，同时也支持replace操作，使用下面的方法：

```php
<?php
namespace app\controller;
use app\model\Employees;

class DbTest
{
    public function index()
    {
        $data=[
            'first_name'   =>  '麻子',
            'last_name'    =>  'cs'
        ];
        // 第二个参数是允许写入的字段，第三个参数是使用replace操作。
        $res = Employees::create($data,['first_name', 'last_name'],true);
        return $res;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/1d9142a004df453ea1827c63907b75c6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 总结

新增数据的最佳实践原则：**使用create方法新增数据，使用saveAll批量新增数据**。

# 四：更新
和模型新增一样，更新操作同样也会经过修改器、自动完成以及模型事件等处理，并不等同于数据库的数据更新，而且更新方法和新增方法使用的是同一个方法，通常系统会自动判断需要新增还是更新数据。

> 查找并更新

在取出数据后，**更改字段内容后使用save方法更新数据**。这种方式是最佳的更新方式。

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $employee = Employees::where('first_name', '鸡哥')->find();
        $employee->first_name = '阿鸡';
        $employee->last_name = 'aj';
        $employee->save();
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2f720814c2f240a49aa6edb209953ecd.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
> 批量更新

以使用saveAll方法批量更新数据，只需要在批量更新的数据中包含主键即可。

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $data = [
            [
                'employee_id'       =>      97,
                'first_name'        =>      '大东'
            ],
            [
                'employee_id'       =>      98,
                'first_name'        =>      '阿虚'
            ]
        ];
//        这么调用是错的
//        Employees::saveAll($data);
        $res = new Employees();
        $res->saveAll($data);
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/ceff335488364679bf0d36465cfb2323.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> 直接更新（静态方法）

默认情况下会过滤非数据表字段的数据，如果你通过外部提交赋值给模型，并且希望指定某些字段写入，可以使用：

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $data = [
            'employee_id'       =>      99,
            'first_name'        =>      'eyes'
        ];
        Employees::update($data);
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/bfede5049416440482995fc33dfd6d3d.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
事实上update静态方法可以传入三个参数：

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        // 第一个参数是更新内容，第二个参数是更新条件(如果第一个参数包含主键，则无需填入)，第三个参数是允许更新字段
        Employees::update(['first_name'=>'李五'],['employee_id'=>94],['first_name']);
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8e0ae5fdc7c64ff8a78944ccf42cced4.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> 总结

不要调用save方法进行多次数据写入。更新的最佳实践原则是：**如果需要使用模型事件，那么就先查询后更新，如果不需要使用事件或者不查询直接更新，直接使用静态的Update方法进行条件更新，如非必要，尽量不要使用批量更新**。

# 五：删除
模型的删除和数据库的删除方法区别在于，模型的删除会包含模型的事件处理

> 删除当前模型

删除模型数据，可以在查询后调用delete方法

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        $employee = new Employees();
        // 先查找
        $res = $employee->find(94);
        // 再删除(因为返回值是布尔型，所以返回报错，但执行正常)
        return $res->delete();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/427c96034b31406497cf6ee68a49a64e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> 根据主键删除

或者直接调用静态方法（根据主键删除），当destroy方法传入空值（包括空字符串和空数组）的时候不会做任何的数据删除操作，但传入0则是有效的

```php
User::destroy(1);
// 支持批量删除多个数据
User::destroy([1,2,3]);
```

> 条件删除

还支持使用闭包删除:

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        Employees::destroy(function ($query){
            $query->where('first_name', '麻子');
        });
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8b9dfe2e87b84f5ca87ddb5c75e8ca58.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
或者通过数据库类的查询条件删除(直接调用数据库的delete方法的话无法调用模型事件):

```php
<?php
namespace app\controller;
use app\model\Employees;
use think\facade\Db;

class DbTest
{
    public function index()
    {
        Employees::where('first_name', '赵六')->limit(1)->delete();
        return Db::getLastSql();
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/84109042cfab496db846a7f7810a8e39.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

> 总结

删除的最佳实践原则是：**如果删除当前模型数据，用delete方法，如果需要直接删除数据，使用destroy静态方法**。

# 六：查询
如果你是在模型内部获取数据，请不要使用`$this->name`的方式来获取数据，请使用`$this->getAttr('name')` 替代。
> 获取单个数据

```php
// 取出主键为1的数据
$user = User::find(1);
echo $user->name;

// 使用查询构造器查询满足条件的数据
$user = User::where('name', 'thinkphp')->find();
echo $user->name;
```
或者可以先判断是否为空模型再输出：

```php
$user = User::where('name', 'thinkphp')->findOrEmpty();
if (!$user->isEmpty()) {
    echo $user->name;
}
```

> 获取多个数据

```php
// 根据主键获取多个数据
$list = User::select([1,2,3]);
// 对数据集进行遍历操作
foreach($list as $key=>$user){
    echo $user->name;
}
```
要更多的查询支持，一样可以使用查询构造器(查询构造器方式的查询可以支持更多的连贯操作，包括排序、数量限制等):

```php
// 使用查询构造器查询
$list = User::where('status', 1)->limit(3)->order('id', 'asc')->select();
foreach($list as $key=>$user){
    echo $user->name;
}
```