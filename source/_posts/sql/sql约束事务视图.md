---
title: sql之约束、事务与视图
date:  2021-09-28 19:43:03
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/46.jpg
tags:
  - 约束
  - 事务
  - 视图
categories: sql
---

# 一：约束
```
含义：一种限制，用于限制表中的数据，为了保证表中数据的准确和可靠性
分类：六大约束
    NOT NULL：非空约束，用于保证该字段的值不能为空
    DEFAULT：默认约束，用于保证该字段有默认值
    PRIMARY KEY：主键约束，用于保证该字段唯一且非空
    UNIQUE：唯一约束，用于保证该字段具有唯一性，但可以为空
    CHECK：检查约束（mysql中不支持，如果写了也不会报错）
    FOREIGN KEY：外键约束，用于限制两个表的关系，保证该字段的值必须来自于主表关联列的值

添加约束的时机：
    1. 创建表时
    2. 修改表时
    
约束的添加分类：
    列级约束：六大约束语法都支持，但外键约束没有效果
    表级约束：除了非空和默认约束，其他都支持
```
事实上对于外键约束，有的大佬有一些看法，如下，大佬的csdn博客账号是：[去吧猫头夜鹰](https://blog.csdn.net/qq_25274377)

<img src="https://img-blog.csdnimg.cn/059ab53f82454657ace40d7fe23e7fb3.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16"  width="500px"/>
<img src="https://img-blog.csdnimg.cn/15d3efdd574348bfa452a4bb513f9868.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16"  width="300px"/>
<img src="https://img-blog.csdnimg.cn/1dd243b1f0a24c4eb100f08794546cd5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16" width="300px"/>

## 1. 创建表时添加列级约束
依次执行后创建两个表。
```sql
CREATE DATABASE students;

CREATE TABLE major(
  id INT PRIMARY KEY,
  majorName VARCHAR(20)
);

CREATE TABLE stuinfo (
  id INT PRIMARY KEY, # 主键约束
  stuName VARCHAR (20) NOT NULL, # 非空约束
  gender CHAR(1) CHECK (gender = "男" OR gender = "女"), # 检查约束
  seat INT UNIQUE, # 唯一约束
  age INT DEFAULT 18, # 默认约束
  majorId INT REFERENCES major(id) # 外键约束
) ;
```
查看stuinfo表详情如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/ca67be20e29c410f958ddd71f96ecf73.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
## 2. 创建表时添加表级约束
表级约束在各个字段的最下面
语法：constraint 约束名 约束类型(字段名)
```sql
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo(
  id INT,
  stuname VARCHAR(20),
  gender CHAR(1),
  seat INT,
  age INT,
  majorid INT,
  
  CONSTRAINT pk PRIMARY KEY(id), # 主键
  CONSTRAINT uq UNIQUE(seat), # 唯一键
  CONSTRAINT ck CHECK(gender = '男' OR gender = '女'), # 检查
  CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id) # 外键
);

SHOW INDEX FROM stuinfo;
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/3f32c16ed0944d9ba62a88c370f1e176.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
事实上约束名不是必须的，因此还可以这样：

```sql
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo(
  id INT,
  stuname VARCHAR(20),
  gender CHAR(1),
  seat INT,
  age INT,
  majorid INT,
  
  PRIMARY KEY(id), # 主键
  UNIQUE(seat), # 唯一键
  CHECK(gender = '男' OR gender = '女'), # 检查
  FOREIGN KEY(majorid) REFERENCES major(id) # 外键
);

SHOW INDEX FROM stuinfo;
```
由下可见此时除主键外字段名成为了约束名。
![在这里插入图片描述](https://img-blog.csdnimg.cn/35244ae8e1b2473394be07edeec970ad.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
## 3. 创建表时添加约束的通用写法

```sql
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo (
  id INT PRIMARY KEY,
  stuName VARCHAR (20) NOT NULL,
  gender CHAR(1) CHECK (gender = "男" OR gender = "女"),
  seat INT UNIQUE,
  age INT DEFAULT 18,
  majorId INT,
  CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id)
) ;

SHOW INDEX FROM stuinfo;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/c89f7dd822024668ab9b138ff95c81c1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/25262d3575f646ad9bff63adbb174158.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)


## 4. 修改表时添加约束
添加列级约束：

```sql
alter table 表名 modify column 字段名 字段类型 新约束;
```

添加表级约束：

```sql
alter table 表名 [constraint 约束名] 约束类型(字段名) [外键的引用];
```
示例代码如下：

```sql
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo(
  id INT,
  stuname VARCHAR(20),
  gender CHAR(1),
  seat INT,
  age INT,
  majorid INT
);

# 1.添加非空约束
ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NOT NULL;

# 2.添加默认约束
ALTER TABLE stuinfo MODIFY COLUMN age INT DEFAULT 18;

# 3.添加主键约束
ALTER TABLE stuinfo MODIFY COLUMN id INT PRIMARY KEY;  # 列级约束
ALTER TABLE stuinfo ADD PRIMARY KEY(id);  # 表级约束

# 4.添加唯一约束
ALTER TABLE stuinfo MODIFY COLUMN seat INT UNIQUE;  # 列级约束
ALTER TABLE stuinfo ADD UNIQUE(seat);  # 表级约束

# 5.添加外键
ALTER TABLE stuinfo ADD FOREIGN KEY(majorid) REFERENCES major(id);  # 默认约束名
ALTER TABLE stuinfo ADD CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id);  # 自定义约束名
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/a5c17d6863794dbcae582af76b04d76b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/c063457f5d5745078595a64f46bb240e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 5. 修改表时删除约束

```sql
# 1.删除非空约束
ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NULL;

# 2.删除默认约束 
ALTER TABLE stuinfo MODIFY COLUMN age INT;

# 3.删除主键约束
ALTER TABLE stuinfo DROP PRIMARY KEY;

# 4.删除唯一约束
ALTER stuinfo DROP INDEX seat;

# 5.删除外键
ALTER TABLE stuinfo DROP FOREIGN KEY majorid;
```
# 二：事务
事务是在数据库上按照一定的逻辑顺序执行的任务序列，既可以由用户手动执行，也可以由某种数据库程序自动执行。

事务实际上就是对数据库的一个或者多个更改。当你在某张表上创建更新或者删除记录的时，你就已经在使用事务了。控制事务以保证数据完整性，并对数据库错误做出处理，对数据库来说非常重要。

实践中，通常会将很多 SQL 查询组合在一起，并将其作为某个事务一部分来执行。

事务具有以下四个标准属性，通常用缩略词 ACID 来表示：
+ 原子性：保证任务中的所有操作都执行完毕；否则，事务会在出现错误时终止，并回滚之前所有操作到原始状态。
+ 一致性：如果事务成功执行，则数据库的状态得到了进行了正确的转变。
+ 隔离性：保证不同的事务相互独立、透明地执行。
+ 持久性：即使出现系统故障，之前成功执行的事务的结果也会持久存在。
## 1.事务的使用
> 事务的创建

隐式事务：事务没有明显的开启和结束的标记，比如insert、update、delete语句。

显式事务：事务具有明显的开启和结束的标记
前提：必须先设置自动提交功能为禁用，禁用语句为：`set autocommit=0`

一个标准的事务流程如下：
```sql
# 步骤一：开启事务
set autocommit=0;
start transaction;  # 可选
# 步骤二：编写事务中的sql语句(select insert update delete)
语句一;
语句二;
...
# 步骤三：结束事务
commit; # 提交事务
rollback; # 回滚事务
```

示例如下：
先插入数据，方便事务操作：

```sql
ALTER TABLE stuinfo ADD money INT;
INSERT INTO stuinfo(id, stuname, money)
VALUE (1, "张三", 200);
INSERT INTO stuinfo(id, stuname, money)
VALUE (2, "罗老师", 600);
SELECT * FROM stuinfo;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/8ed995ef3307458886b08df06de43ed8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)然后张三和罗老师进行转账业务，罗老师给张三150元拿到张三事迹的使用权。转账流程如下：

```sql
# 开始事务操作
SET autocommit = 0;
START TRANSACTION;
UPDATE stuinfo SET money = 350 WHERE id = 1;
UPDATE stuinfo SET money = 450 WHERE id = 2;
COMMIT;
SELECT * FROM stuinfo;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/8e30a5fa5f5541be80478285520ab557.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果没有执行到`commit`，那么操作是不会真正被执行的，比如下面的操作，在commit之前执行回滚，那么数据就不会发生变化。

```sql
SET autocommit = 0;
START TRANSACTION;
UPDATE stuinfo SET money = 5000 WHERE id = 1;
UPDATE stuinfo SET money = 4500 WHERE id = 2;
ROLLBACK;
#commit;
SELECT * FROM stuinfo;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/4fe9beaa989e411d9dacd25449256405.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2.事务的隔离级别
对于同时运行的多个事务，当这些事务访问数据库中相同的数据时，如果没有采取必要的隔离机制，就会导致各种并发问题：
+ 脏读：对于两个事务T1、T2，T1读取了已经被T2更新但还没有被提交的字段之后，若T2回滚，则T1读取的内容就是临时且无效的。
+ 不可重复读：对于两个事务T1、T2，T1读取了一个字段，然后T2更新了该字段之后，T1再次读取了同一个字段，值就不同了。
+ 幻读：对于两个事务T1、T2，T1从一个表中读取了一个字段，然后T2在该表中插入了一些新的行，之后如果T1再次读取同一个表，就会多出几行。

不可重复读和幻读比较：两者有些相似，但是前者针对的是update或delete，后者针对的insert。

鉴于上述并发问题，人们就为事务设置了隔离性，即数据库系统必须具有隔离并发运行各个事务的能力，使他们不会互相影响，避免各种并发问题。

数据库提供四种事务隔离级别：
![在这里插入图片描述](https://img-blog.csdnimg.cn/ddcfe66fe9734b2aa0bfdbe26a114d8b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
+ **Read uncommitted**：读未提交，顾名思义，就是可以读到未提交的内容。因此，在这种隔离级别下，查询是不会加锁的，也由于查询的不加锁，所以这种隔离级别的一致性是最差的，可能会产生“脏读”、“不可重复读”、“幻读”。如无特殊情况，基本是不会使用这种隔离级别的。
+ **Read committed**：读提交，顾名思义，就是只能读到已经提交了的内容。这是各种系统中最常用的一种隔离级别，保证了一个事务不会读到另一个并行事务已修改但未提交的数据，避免了“脏读取”，但不能避免“幻读”和“不可重复读取”。该级别适用于大多数系统。这里多说点：那为什么“读提交”同“读未提交”一样，都没有查询加锁，但是却能够避免脏读呢？这就要说道另一个机制“快照（snapshot）”，而这种既能保证一致性又不加锁的读也被称为“快照读（Snapshot Read）”假设没有“快照读”，那么当一个更新的事务没有提交时，另一个对更新数据进行查询的事务会因为无法查询而被阻塞，这种情况下，并发能力就相当的差。而“快照读”就可以完成高并发的查询，不过，“读提交”只能避免“脏读”，并不能避免“不可重复读”和“幻读”。
+ **Repeatable read**：可重复读，顾名思义，就是专门针对“不可重复读”这种情况而制定的隔离级别，自然，它就可以有效的避免“不可重复读”。在这个级别下，普通的查询同样是使用的“快照读”，但是，和“读提交”不同的是，当事务启动时，就不允许进行“修改操作（Update）”了，而“不可重复读”恰恰是因为两次读取之间进行了数据的修改，因此，“可重复读”能够有效的避免“不可重复读”，但却避免不了“幻读”，因为幻读是由于“插入或者删除操作（Insert or Delete）”而产生的。
+ **Serializable**：这是数据库最高的隔离级别，这种级别下，事务“串行化顺序执行”，也就是一个一个排队执行。这种级别下，“脏读”、“不可重复读”、“幻读”都可以被避免，但是执行效率奇差，性能开销也最大，所以基本没人会用。

Oracle支持的2种事务隔离级别：READ COMMITED和SERIALIZABLE，Oracle默认的事务隔离级别是READ COMMITED。而MySQL支持四种事务隔离级别，MySQL的默认事务隔离级别是：REPEATABLE READ。

查看当前的事务隔离级别：
```sql
# 以下四种方法都可以查看
SHOW VARIABLES LIKE 'transaction%';
SHOW VARIABLES LIKE '%iso%';
SELECT @@GLOBAL.transaction_isolation;
SELECT @@SESSION.transaction_isolation;
```
注意`show variables like 'tx_isolation%';`已经在MySQL5.7.20后被弃用。

设置隔离级别：
```sql
# 设置全局隔离级别
set global transaction isolation level REPEATABLE READ;
set global transaction isolation level READ COMMITTED;
set global transaction isolation level READ UNCOMMITTED;
set global transaction isolation level SERIALIZABLE;

# 设置会话隔离级别 
set session transaction isolation level REPEATABLE READ;
set session transaction isolation level READ COMMITTED;
set session transaction isolation level READ UNCOMMITTED;
set session transaction isolation level SERIALIZABLE;

#通过配置文件设置隔离级别（重启生效）
[mysqld]
transaction-isolation = REPEATABLE-READ
transaction-isolation = READ-COMMITTED
transaction-isolation = READ-UNCOMMITTED
transaction-isolation = SERIALIZABLE
```

## 3.回滚点的演示
事务里面还有一个关键词`savepoint`，功能类似于保存点，用法是：

```sql
savepoint 节点名;
```
示例如下：

```sql
SET autocommit=0;
START TRANSACTION;
UPDATE stuinfo SET money=300 WHERE id=1;
SAVEPOINT a; # 设置保存点
UPDATE stuinfo SET money=100 WHERE id=2;
ROLLBACK TO a; # 回滚到保存点

SELECT * FROM stuinfo;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/a4251bbc661d47678601ea58774c1f39.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 三：视图
MySQL 从5.0.1版本开始提供视图功能，视图（View）是一种虚拟存在的表，同真实表一样，视图也由列和行构成，但视图并不实际存在于数据库中。行和列的数据来自于定义视图的查询中所使用的表，并且还是在使用视图时动态生成的。

数据库中只存放了视图的定义，并没有存放视图中的数据，这些数据都存放在定义视图查询所引用的真实表中。使用视图查询数据时，数据库会从真实表中取出对应的数据。因此，视图中的数据是依赖于真实表中的数据的。一旦真实表中的数据发生改变，显示在视图中的数据也会发生改变。

视图可以从原有的表上选取对用户有用的信息，那些对用户没用，或者用户没有权限了解的信息，都可以直接屏蔽掉，作用类似于筛选。这样做既使应用简单化，也保证了系统的安全。

视图的应用场景：
+ 多个地方用到同样的查询结果
+ 该查询结果使用的sql语句较为复杂

视图不同于数据表，他们的区别在于以下几点：
+ 视图不是数据库中真实的表，而是一张虚拟表，其结构和数据是建立在对数据中真实表的查询基础上的。
+ 存储在数据库中的查询操作 SQL 语句定义了视图的内容，列数据和行数据来自于视图查询所引用的实际表，引用视图时动态生成这些数据。
+ 视图没有实际的物理记录，不是以数据集的形式存储在数据库中的，它所对应的数据实际上是存储在视图所引用的真实表中的。
+ 视图是数据的窗口，而表是内容。表是实际数据的存放单位，而视图只是以不同的显示方式展示数据，其数据来源还是实际表。
+ 视图是查看数据表的一种方法，可以查询数据表中某些字段构成的数据，只是一些 SQL 语句的集合。从安全的角度来看，视图的数据安全性更高，使用视图的用户不接触数据表，不知道表结构。
+ 视图的建立和删除只影响视图本身，不影响对应的基本表。

视图与表在本质上虽然不相同，但视图经过定义以后，结构形式和表一样，可以进行查询、修改、更新和删除等操作。同时，视图具有如下优点：
+ **定制用户数据，聚焦特定的数据**：在实际的应用过程中，不同的用户可能对不同的数据有不同的要求。例如，当数据库同时存在时，如学生基本信息表、课程表和教师信息表等多种表同时存在时，可以根据需求让不同的用户使用各自的数据。学生查看修改自己基本信息的视图，安排课程人员查看修改课程表和教师信息的视图，教师查看学生信息和课程信息表的视图。
+ **简化数据操作**：在使用查询时，很多时候要使用聚合函数，同时还要显示其他字段的信息，可能还需要关联到其他表，语句可能会很长，如果这个动作频繁发生的话，可以创建视图来简化操作。
+ **提高数据的安全性**：视图是虚拟的，物理上是不存在的。可以只授予用户视图的权限，而不具体指定使用表的权限，来保护基础数据的安全。
+ **共享所需数据**：通过使用视图，每个用户不必都定义和存储自己所需的数据，可以共享数据库中的数据，同样的数据只需要存储一次。
+ **更改数据格式**：通过使用视图，可以重新格式化检索出的数据，并组织输出到其他应用程序中。
+ **重用 SQL 语句**：视图提供的是对查询操作的封装，本身不包含数据，所呈现的数据是根据视图定义从基础表中检索出来的，如果基础表的数据新增或删除，视图呈现的也是更新后的数据。视图定义后，编写完所需的查询，可以方便地重用该视图。

使用视图的时候，还应该注意以下几点：
+ 创建视图需要足够的访问权限。
+ 创建视图的数目没有限制。
+ 视图可以嵌套，即从其他视图中检索数据的查询来创建视图。
+ 视图不能索引，也不能有关联的触发器、默认值或规则。
+ 视图可以和表一起使用。
+ 视图不包含数据，所以每次使用视图时，都必须执行查询中所需的任何一个检索操作。如果用多个连接和过滤条件创建了复杂的视图或嵌套了视图，可能会发现系统运行性能下降得十分严重。因此，在部署大量视图应用时，应该进行系统测试。

## 1.视图的创建
创建视图的语法：

```sql
create view 视图名
as
查询语句;
```
使用示例：

```sql
USE myemployees;
# 查询邮箱中包含a字符的员工名、部门名和工种信息
CREATE VIEW myv1  # 创建视图
AS
SELECT last_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id;

SELECT * FROM myv1 WHERE last_name LIKE '%a%';
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/6c7a50272c0d4f44b2fa956865572c82.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)![在这里插入图片描述](https://img-blog.csdnimg.cn/5978b68430954b6fb4a97e00c7e9239c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2.视图的修改
方式一：

```sql
create or replace view 视图名
as
查询语句;
```
使用示例：

```sql
CREATE OR REPLACE VIEW myv1
AS
SELECT last_name, first_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id;

SELECT * FROM myv1 WHERE last_name LIKE '%a%';
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/cfa51b36bcb24eb5adc103126aeb2efc.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

方式二：

```sql
alter view 视图名
as
查询语句;
```
使用示例：

```sql
ALTER VIEW myv1
AS
SELECT first_name, last_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id
WHERE e.first_name LIKE '%s%';

SELECT * FROM myv1 WHERE last_name LIKE '%a%';
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/16261ec448d54c589d0f74b1646a7d5b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

## 3.视图的查看和删除
查看语法：

```sql
DESC myv1;  # 查看视图结构
SHOW CREATE VIEW myv1;  # 可以查看视图的创建语句
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/30f8ab4509704c328b3ebed0b3a5d797.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)查看得到的sql如下：

```sql
CREATE ALGORITHM = UNDEFINED DEFINER = `root` @`localhost` SQL SECURITY DEFINER VIEW `myv1` AS 
SELECT 
  `e`.`first_name` AS `first_name`,
  `e`.`last_name` AS `last_name`,
  `d`.`department_name` AS `department_name`,
  `j`.`job_title` AS `job_title` 
FROM
  (
    (
      `employees` `e` 
      JOIN `departments` `d` 
        ON (
          (
            `e`.`department_id` = `d`.`department_id`
          )
        )
    ) 
    JOIN `jobs` `j` 
      ON ((`j`.`job_id` = `e`.`job_id`))
  ) 
WHERE (`e`.`first_name` LIKE '%s%')
```

删除语法：

```sql
drop view 视图名, 视图名,...
```

以下是所有的SQL代码，可以复制下来自行调试：

```sql
# 常见约束

/*
含义：一种限制，用于限制表中的数据，为了保证表中数据的准确和可靠性
分类：六大约束
    NOT NULL：非空约束，用于保证该字段的值不能为空
    DEFAULT：默认约束，用于保证该字段有默认值
    PRIMARY KEY：主键约束，用于保证该字段唯一且非空
    UNIQUE：唯一约束，用于保证该字段具有唯一性，但可以为空
    CHECK：检查约束（mysql中不支持，如果写了也不会报错）
    FOREIGN KEY：外键约束，用于限制两个表的关系，保证该字段的值必须来自于主表关联列的值

添加约束的时机：
    1. 创建表时
    2. 修改表时
    
约束的添加分类：
    列级约束：六大约束语法都支持，但外键约束没有效果
    表级约束：除了非空和默认约束，其他都支持
*/


# 创建表时添加列级约束
CREATE DATABASE students;

CREATE TABLE stuinfo (
  id INT PRIMARY KEY, # 主键约束
  stuName VARCHAR (20) NOT NULL, # 非空约束
  gender CHAR(1) CHECK (gender = "男" OR gender = "女"), # 检查约束
  seat INT UNIQUE, # 唯一约束
  age INT DEFAULT 18, # 默认约束
  majorId INT REFERENCES major(id) # 外键约束
) ;

CREATE TABLE major(
  id INT PRIMARY KEY,
  majorName VARCHAR(20)
);

# 创建表时添加表级约束
/*
表级约束在各个字段的最下面
语法：constraint 约束名 约束类型(字段名)
*/
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo(
  id INT,
  stuname VARCHAR(20),
  gender CHAR(1),
  seat INT,
  age INT,
  majorid INT,
  
  PRIMARY KEY(id), # 主键
  UNIQUE(seat), # 唯一键
  CHECK(gender = '男' OR gender = '女'), # 检查
  FOREIGN KEY(majorid) REFERENCES major(id) # 外键
);

SHOW INDEX FROM stuinfo;

# 通用的写法
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo (
  id INT PRIMARY KEY,
  stuName VARCHAR (20) NOT NULL,
  gender CHAR(1) CHECK (gender = "男" OR gender = "女"),
  seat INT UNIQUE,
  age INT DEFAULT 18,
  majorId INT,
  CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id)
) ;

SHOW INDEX FROM stuinfo;

# 修改表时添加约束
/*
添加列级约束：
alter table 表名 modify column 字段名 字段类型 新约束;
添加表级约束：
alter table 表名 [constraint 约束名] 约束类型(字段名) [外键的引用]
*/
DROP TABLE IF EXISTS stuinfo;

CREATE TABLE stuinfo(
  id INT,
  stuname VARCHAR(20),
  gender CHAR(1),
  seat INT,
  age INT,
  majorid INT
);

# 1.添加非空约束
ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NOT NULL;

# 2.添加默认约束
ALTER TABLE stuinfo MODIFY COLUMN age INT DEFAULT 18;

# 3.添加主键约束
ALTER TABLE stuinfo MODIFY COLUMN id INT PRIMARY KEY;  # 列级约束
ALTER TABLE stuinfo ADD PRIMARY KEY(id);  # 表级约束

# 4.添加唯一约束
ALTER TABLE stuinfo MODIFY COLUMN seat INT UNIQUE;  # 列级约束
ALTER TABLE stuinfo ADD UNIQUE(seat);  # 表级约束

# 5.添加外键
ALTER TABLE stuinfo ADD FOREIGN KEY(majorid) REFERENCES major(id);  # 默认约束名
ALTER TABLE stuinfo ADD CONSTRAINT fk_stuinfo_major FOREIGN KEY(majorid) REFERENCES major(id);  # 自定义约束名

SHOW INDEX FROM stuinfo;
# 修改表时删除约束

# 1.删除非空约束
ALTER TABLE stuinfo MODIFY COLUMN stuname VARCHAR(20) NULL;

# 2.删除默认约束 
ALTER TABLE stuinfo MODIFY COLUMN age INT;

# 3.删除主键约束
ALTER TABLE stuinfo DROP PRIMARY KEY;

# 4.删除唯一约束
ALTER stuinfo DROP INDEX seat;

# 5.删除外键
ALTER TABLE stuinfo DROP FOREIGN KEY majorid;

/*
事务的特性：

*/
# 插入数据，为事务操作做准备
ALTER TABLE stuinfo ADD money INT;
INSERT INTO stuinfo(id, stuname, money)
VALUE (1, "张三", 200);
INSERT INTO stuinfo(id, stuname, money)
VALUE (2, "罗老师", 600);
SELECT * FROM stuinfo;
# 开始事务操作
SET autocommit = 0;
START TRANSACTION;
UPDATE stuinfo SET money = 30 WHERE id = 1;
UPDATE stuinfo SET money = 40 WHERE id = 3;
COMMIT;
SELECT * FROM stuinfo;

# 事务隔离级别
# 查看当前事务隔离级别
SHOW VARIABLES LIKE 'transaction%';
SELECT @@GLOBAL.transaction_isolation;
SELECT @@SESSION.transaction_isolation;
SHOW VARIABLES LIKE '%iso%';
# 设置全局隔离级别
SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE;
# 设置会话隔离级别 
SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE;

SET autocommit=0;
START TRANSACTION;
UPDATE stuinfo SET money=300 WHERE id=1;
SAVEPOINT a; # 设置保存点
UPDATE stuinfo SET money=100 WHERE id=2;
ROLLBACK TO a; # 回滚到保存点

SELECT * FROM stuinfo;


# 视图
USE myemployees;
# 查询邮箱中包含a字符的员工名、部门名和工种信息
CREATE VIEW myv1  # 创建视图
AS
SELECT last_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id;

SELECT * FROM myv1 WHERE last_name LIKE '%a%';


CREATE OR REPLACE VIEW myv1
AS
SELECT last_name, first_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id;

SELECT * FROM myv1 WHERE last_name LIKE '%a%';

ALTER VIEW myv1
AS
SELECT first_name, last_name, department_name, job_title
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN jobs j ON j.job_id = e.job_id
WHERE e.first_name LIKE '%s%';

SELECT * FROM myv1 WHERE last_name LIKE '%a%';

DESC myv1;
SHOW CREATE VIEW myv1;

CREATE ALGORITHM = UNDEFINED DEFINER = `root` @`localhost` SQL SECURITY DEFINER VIEW `myv1` AS 
SELECT 
  `e`.`first_name` AS `first_name`,
  `e`.`last_name` AS `last_name`,
  `d`.`department_name` AS `department_name`,
  `j`.`job_title` AS `job_title` 
FROM
  (
    (
      `employees` `e` 
      JOIN `departments` `d` 
        ON (
          (
            `e`.`department_id` = `d`.`department_id`
          )
        )
    ) 
    JOIN `jobs` `j` 
      ON ((`j`.`job_id` = `e`.`job_id`))
  ) 
WHERE (`e`.`first_name` LIKE '%s%')
```
