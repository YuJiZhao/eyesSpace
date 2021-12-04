---
title: MySQL存储引擎
date: 2021-09-27 20:29:35
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/43.jpg
tags:
  - 存储引擎
categories: sql
---

# 一：存储引擎是什么
存储引擎其实就是对于数据库文件的一种存取机制，如何实现存储数据，如何为存储的数据建立索引以及如何更新，查询数据等技术实现的方法。MySQL中的数据用各种不同的技术存储在文件（或内存）中，这些技术中的每一种技术都使用不同的存储机制，索引技巧，锁定水平并且最终提供广泛的不同功能和能力。在MySQL中将这些不同的技术及配套的相关功能称为存储引擎。

数据库存储引擎是数据库底层软件组件，不同的存储引擎提供不同的存储机制、索引技巧、锁定水平等功能，使用不同的存储引擎还可以获得特定的功能。Oracle，SqlServer等数据库只有一种存储引擎，而MySQL提供了插件式的存储引擎架构。所以MySQL存在多种存储引擎，可以根据需要使用相应引擎，或者编写存储引擎。MySQL 的核心就是存储引擎。

<p style="color: red">另外要注意的是存储引擎是基于表的，而不是数据库</p>

# 二：MySQL的引擎
MySQL支持9种存储引擎(大型公司可能会自己开发)，分别是InnoDB，MyISAM，Memory，CSV，Archive，Blackhole，Merge，Federated，Example。

可以使用`SHOW ENGINES;`命令查看自己的mysql支持的引擎情况（我的mysql版本是8.0）：
![在这里插入图片描述](https://img-blog.csdnimg.cn/83f24e237d0943e98b83fc41d2585569.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)引擎很多，但是用得最广泛的只有两款：InnoDB和MyISAM，MySQL5.5之前的默认存储引擎是MyISAM，5.5之后就改为了InnoDB。

创建新表时如果不指定存储引擎，那么系统就会使用默认的存储引擎，可以使用: 

```sql
show variables like '%storage_engine%' ;
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/d15c1ea239ea433087aaf126aab503d2.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果要修改默认引擎，可以用这个命令：

```sql
SET default_storage_engine=< 存储引擎名 >
```

比如我想把默认引擎改为MEMORY，那我可以`SET default_storage_engine=MEMORY;`

如果想查看具体某一个表所使用的存储引擎，可以用这个命令：

```sql
show create table tablename;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/120e2456bffb41ebae96520588cce742.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

准确查看某个数据库中的某一表所使用的存储引擎

```sql
show table status from database where name="tablename";
```

# 三：引擎介绍
下面重点介绍几种常用的存储引擎， 并对比各个存储引擎之间的区别， 如下表所示：
<table>
<thead>
<tr>
<th>特点</th>
<th>InnoDB</th>
<th>MyISAM</th>
<th>MEMORY</th>
<th>MERGE</th>
<th>NDB</th>
</tr>
</thead>
<tbody>
<tr>
<td>存储限制</td>
<td>64TB</td>
<td>256TB</td>
<td>RAM</td>
<td>没有</td>
<td>128TB</td>
</tr>
<tr>
<td>事务安全</td>
<td><mark>支持</mark></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>锁机制</td>
<td><mark>行锁(适合高并发)</mark></td>
<td><mark>表锁</mark></td>
<td>表锁</td>
<td>表锁</td>
<td>行锁</td>
</tr>
<tr>
<td>B树索引</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
</tr>
<tr>
<td>哈希索引</td>
<td></td>
<td></td>
<td>支持</td>
<td></td>
<td></td>
</tr>
<tr>
<td>全文索引</td>
<td>支持(5.6版本之后)</td>
<td>支持</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>集群索引</td>
<td>支持</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>数据索引</td>
<td>支持</td>
<td></td>
<td>支持</td>
<td></td>
<td>支持</td>
</tr>
<tr>
<td>索引缓存</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
<td>支持</td>
</tr>
<tr>
<td>数据可压缩</td>
<td></td>
<td>支持</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td>空间使用</td>
<td>高</td>
<td>低</td>
<td>N/A</td>
<td>低</td>
<td>低</td>
</tr>
<tr>
<td>内存使用</td>
<td>高</td>
<td>低</td>
<td>中等</td>
<td>低</td>
<td>高</td>
</tr>
<tr>
<td>批量插入速度</td>
<td>低</td>
<td>高</td>
<td>高</td>
<td>高</td>
<td>高</td>
</tr>
<tr>
<td>支持外键</td>
<td><mark>支持</mark></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
</tbody>
</table>

## 1. InnoDB存储引擎
InnoDB存储引擎支持事务，其设计目标主要是面向在线事务处理的应用。其特点是行锁设计、支持外键，并支持类似于Oracle的非锁定读，即默认读取操作不会产生锁。InnoDB通过使用多版本并发控制(MVCC)来获得高并发性，并且实现了SQL标准的4种隔离级别，默认为Repeatable级别。同时使用一种被称为next-key locking的策略来避免幻读现象的产生。InnoDB存储引擎还提供了插入缓存、二次写、自适应哈希索引、预读等高性能和高可用的功能。

​对于表中数据的存储，InnoDB存储引擎采用了聚集的方式，因此每张表的存储都是按主键的顺序进行存放。如果没有显示地在表定义时指定主键，InnoDB存储引擎会为每一行生成一个6字节的ROWID，并以此作为主键。实践证明，InnoDB存储引擎具备高可用性、高性能以及高可扩展性。

## 2. MyISAM存储引擎
MyISAM是MySQL的ISAM扩展格式和缺省的数据库引擎。除了提供ISAM里所没有的索引和字段管理的大量功能，MyISAM还使用一种表格锁定的机制，来优化多个并发的读写操作，其代价是你需要经常运行OPTIMIZE TABLE命令，来恢复被更新机制所浪费的空间。MYISAM强调了快速读取操作，这可能就是为什么MySQL受到了WEB开发如此青睐的主要原因：在WEB开发中你所进行的大量数据操作都是读取操作。所以，大多数虚拟主机提供商和INTERNET平台提供商只允许使用MYISAM格式。MyISAM格式的一个重要缺陷就是不能在表损坏后恢复数据。

## 3.Memory存储引擎
​ Memory存储引擎(之前称HEAP存储引擎)将表中的数据存放在内存中，如果数据库重启或发生故障崩溃，表中的数据将会消失。它非常适合用于存储临时数据的临时表，以及数据仓库中的维度表。Memory存储引擎默认使用哈希索引，而不是我们熟悉的B+树索引。

​ Memory存储引擎非常快，但它只支持表锁，并发性能较差，并且不支持TEXT和BLOB。最重要的是，存储变长字段(varchar)时是按照定长字段(char)的方式进行的，因此会浪费内存，不过eBay工程师已解决此问题。

​ MySQL数据库使用Memory存储引擎作为临时表来存放查询的中间结果集(intermediate result)。如果中间结果集大于Memory存储引擎表的容量设置，又或者中间结果含有TEXT或BLOB列类型字段，则MySQL数据库会把其转换到MyISAM存储引擎表而存放到磁盘中。
Archive存储引擎

## 4.Archive存储引擎
只支持insert和select操作，从MySQL 5.1开始支持索引。Archive存储引擎使用zlib算法将数据行进行压缩后存储，压缩比一般可以达1:10。Archive存储引擎使用行锁来实现高并发的插入操作，但是其本身并不是事务安全的存储引擎，其设计目标主要是提供高速的插入和压缩功能。
Federated存储引擎

## 5.Federated存储引擎
并不存放数据，它只是指向一台远程MySQL数据库服务器上的表。目前只支持MySQL数据库表，不支持异构数据库表。

## 6.Maria存储引擎
Maria存储引擎是由MySQL创始人Michael Widenius新开发的引擎，设计目标主要是用来取代原有的MyISAM存储引擎，从而成为MySQL的默认存储引擎。Maria支持缓存数据和索引文件，应用了行锁设计，提供了MVCC功能，支持事务和非事务安全的选项，以及更好的BLOB字符类型的处理性能。

## 7. 其他
事实上也有很多大公司会自己开发优秀的存储引擎，比如Percona，它为MySQL数据库服务器进行了改进，在功能和性能上较MySQL有了明显提升，该版本提升了在高负载情况下的InnoDB的性能，为DBA提供了一些非常有用的性能诊断工具，另外有更多的参数和命令来控制服务器行为，该公司新建了一款存储引擎“xtradb”，完全可以替代InnoDB，并且在性能和并发上做的更好。

其他的如阿里的AliSql+AliRedis也十分优秀，据说性能有30-70左右的提升。