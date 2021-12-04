---
title: Tomcat的安装配置与使用，及常用端口大全
date: 2021-09-07 23:19:01
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/article/5.jpg
tags: 
  - tomcat
  - 端口
categories: java
---

# 零：Tomcat的介绍
Tomcat是Apache 软件基金会的Jakarta 项目中的一个核心项目，由Apache、Sun 和其他一些公司及个人共同开发而成。由于有了Sun 的参与和支持，最新的Servlet 和JSP 规范总是能在Tomcat 中得到体现，Tomcat 5支持最新的Servlet 2.4 和JSP 2.0 规范。因为Tomcat 技术先进、性能稳定，而且免费，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web 应用服务器。

Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用服务器，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP 程序的首选。对于一个初学者来说，可以这样认为，当在一台机器上配置好Apache 服务器，可利用它响应HTML页面的访问请求。实际上Tomcat是Apache 服务器的扩展，但运行时它是独立运行的，所以当你运行tomcat 时，它实际上作为一个与Apache 独立的进程单独运行的。

诀窍是，当配置正确时，Apache 为HTML页面服务，而Tomcat 实际上运行JSP 页面和Servlet。另外，Tomcat和IIS等Web服务器一样，具有处理HTML页面的功能，另外它还是一个Servlet和JSP容器，独立的Servlet容器是Tomcat的默认模式。不过，Tomcat处理静态HTML的能力不如Apache服务器。目前Tomcat最新版本为10.0.5。

# 一：下载Tomcat
首先前往官网：[tomcat下载](https://tomcat.apache.org/)
![在这里插入图片描述](https://img-blog.csdnimg.cn/5eabd2463b624bf091d8c0dbef83dd3b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)我这里是下载了Tomcat9，点进Tomcat9，我使用的是64位绿色版。
![在这里插入图片描述](https://img-blog.csdnimg.cn/6ac4f8bd57af42a693e5683b23076f99.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 二：安装Tomcat
因为我是下载了绿色版的，直接解压就行。
![在这里插入图片描述](https://img-blog.csdnimg.cn/1f9ca1560921497895f37a31f209173e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)目录介绍：
```
bin					专门用来存放Tomcat服务器的可执行程序
conf                专门用来存放Tomcat服务器的配置文件
lib			        专门用来存放Tomcat服务器的jar包
logs				专门用来存放Tomcat服务器的运行时输出的日记信息
temp				专门用来存放Tomcat服务器运行时产生的临时数据
webapps			    专门用来存放部署的Web工程
work				是Tomcat工作时的目录，用来存放Tomcat运行时jsp翻译为Servlet的源码，和Session钝化的目录
```


# 三：启动Tomcat服务器
找到Tomcat目录下的bin目录下的startup.bat文件，双击启动。
![在这里插入图片描述](https://img-blog.csdnimg.cn/5a4fd549dc814c249b5724d7a6404f88.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
发现出现了乱码，这时候可以在配置文件修改编码方式。打开conf文件夹下的logging.properties文件，找到`java.util.logging.ConsoleHandler.encoding = UTF-8`，将UTF-8改为GBK
![在这里插入图片描述](https://img-blog.csdnimg.cn/bbe9b574762d4b89b5c4719b7a49ac8b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
然后保存修改，重启服务器就好了：
![在这里插入图片描述](https://img-blog.csdnimg.cn/b7d489e0e78e4144b87ed05dcedb8424.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)如果点击startup.bat时黑窗一闪而过，那就是因为没有配置好JAVA_HOME环境变量，JAVA_HOME时JDK的安装目录。
![在这里插入图片描述](https://img-blog.csdnimg.cn/8aefc2d067874a849113e01a7cc4c875.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

除了双击startup.bat启动，还可以用命令行的方式启动，命令行进入Tomcat的bin目录：输入命令`catalina run`
![在这里插入图片描述](https://img-blog.csdnimg.cn/a272c5c7e6704b75ad89c23dc4c8dbb9.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 四：停止Tomcat服务器
+ 直接关闭tomcat窗口
+ 使用快捷键ctrl+c
+ 找到Tomcat的bin目录下的shutdown.bat，双击停止服务器(该方法为主要方法)

# 五：修改Tomcat的端口号
Tomcat默认端口号是8080，但8080端口是被用于WWW代理服务的，可以实现网页浏览，Vue项目的预览就是开启8080端口，因此该端口经常被占用，因此通常都需要修改端口号。

端口号范围是1-65535，1000以内的端口号是系统使用的，修改端口尽量不要使用1000以内的，但是我老师要求修改为端口号为88。修改前还可以看看端口占用情况，打开命令提示符，输入`netstat -ano`。
![在这里插入图片描述](https://img-blog.csdnimg.cn/d12842b23f034b0ab832d8f9f6b09450.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
现在来修改Tomcat端口号，找到Tomcat目录下的conf目录，找到server.xml并打开，找到Connector标签修改端口号。
![在这里插入图片描述](https://img-blog.csdnimg.cn/34e05a82f35e48459743cbc4a3c63ded.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
修改完端口号后需要重启才能生效，成功后效果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/8199c3ddf8fb4f60bbe9c06773c7aace.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 六：在idea中整合Tomcat
打开idea的设置，打开应用程序服务器，点击加号添加：
![在这里插入图片描述](https://img-blog.csdnimg.cn/5cbc65ea639e44a8b7345fe86167b9a7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)选中文件夹即可，选中一个，其余的会自动填写：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f50d3586951242c583518624fab12772.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/b47c4132ea6d4214b6a8d3c19fdea6a5.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)


# 七：常用端口
端口：0
服务：Reserved
说明：通常用于分析操作系统。这一方法能够工作是因为在一些系统中“0”是无效端口，当你试图使用通常的闭合端口连接它时将产生不同的结果。一种典型的扫描，使用IP地址为0.0.0.0，设置ACK位并在以太网层广播。

端口：1
服务：tcpmux
说明：这显示有人在寻找SGI Irix机器。Irix是实现tcpmux的主要提供者，默认情况下tcpmux在这种系统中被打开。Irix机器在发布是含有几个默认的无密码的帐户，如：IP、GUEST UUCP、NUUCP、DEMOS 、TUTOR、DIAG、OUTOFBOX等。许多管理员在安装后忘记删除这些帐户。因此HACKER在INTERNET上搜索tcpmux并利用这些帐户。

端口：7
服务：Echo
说明：能看到许多人搜索Fraggle放大器时，发送到X.X.X.0和X.X.X.255的信息。

端口：19
服务：Character Generator
说明：这是一种仅仅发送字符的服务。UDP版本将会在收到UDP包后回应含有垃圾字符的包。TCP连接时会发送含有垃圾字符的数据流直到连接关闭。HACKER利用IP欺骗可以发动DoS攻击。伪造两个chargen服务器之间的UDP包。同样Fraggle DoS攻击向目标地址的这个端口广播一个带有伪造受害者IP的数据包，受害者为了回应这些数据而过载。

端口：21
服务：FTP
说明：FTP服务器所开放的端口，用于上传、下载。最常见的攻击者用于寻找打开anonymous的FTP服务器的方法。这些服务器带有可读写的目录。木马Doly Trojan、Fore、Invisible FTP、WebEx、WinCrash和Blade Runner所开放的端口。

端口：22
服务：ssh
说明：PcAnywhere建立的TCP和这一端口的连接可能是为了寻找ssh。这一服务有许多弱点，如果配置成特定的模式，许多使用RSAREF库的版本就会有不少的漏洞存在。

端口：23
服务：Telnet
说明：远程登录，**者在搜索远程登录UNIX的服务。大多数情况下扫描这一端口是为了找到机器运行的操作系统。还有使用其他技术，**者也会找到密码。木马Tiny Telnet Server就开放这个端口。

端口：25
服务：SMTP
说明：SMTP服务器所开放的端口，用于发送邮件。**者寻找SMTP服务器是为了传递他们的SPAM。**者的帐户被关闭，他们需要连接到高带宽的E-MAIL服务器上，将简单的信息传递到不同的地址。木马Antigen、Email Password Sender、Haebu Coceda、Shtrilitz Stealth、WinPC、WinSpy都开放这个端口。

端口：31
服务：MSG Authentication
说明：木马Master Paradise、Hackers Paradise开放此端口。

端口：42
服务：WINS Replication
说明：WINS复制

端口：53
服务：Domain Name Server（DNS）
说明：DNS服务器所开放的端口，**者可能是试图进行区域传递（TCP），欺骗DNS（UDP）或隐藏其他的通信。因此防火墙常常过滤或记录此端口。

端口：67
服务：Bootstrap Protocol Server
说明：通过DSL和Cable modem的防火墙常会看见大量发送到广播地址255.255.255.255的数据。这些机器在向DHCP服务器请求一个地址。HACKER常进入它们，分配一个地址把自己作为局部路由器而发起大量中间人（man-in-middle）攻击。客户端向68端口广播请求配置，服务器向67端口广播回应请求。这种回应使用广播是因为客户端还不知道可以发送的IP地址。

端口：69
服务：Trival File Transfer
说明：许多服务器与bootp一起提供这项服务，便于从系统下载启动代码。但是它们常常由于错误配置而使**者能从系统中窃取任何 文件。它们也可用于系统写入文件。

端口：79
服务：Finger Server
说明：**者用于获得用户信息，查询操作系统，探测已知的缓冲区溢出错误，回应从自己机器到其他机器Finger扫描。

端口：80
服务：HTTP
说明：用于网页浏览。木马Executor开放此端口。

端口：99
服务：gram Relay
说明：后门程序ncx99开放此端口。

端口：102
服务：Message transfer agent(MTA)-X.400 over TCP/IP
说明：消息传输代理。

端口：109
服务：Post Office Protocol -Version3
说明：POP3服务器开放此端口，用于接收邮件，客户端访问服务器端的邮件服务。POP3服务有许多公认的弱点。关于用户名和密码交 换缓冲区溢出的弱点至少有20个，这意味着**者可以在真正登陆前进入系统。成功登陆后还有其他缓冲区溢出错误。

端口：110
服务：SUN公司的RPC服务所有端口
说明：常见RPC服务有rpc.mountd、NFS、rpc.statd、rpc.csmd、rpc.ttybd、amd等

端口：113
服务：Authentication Service
说明：这是一个许多计算机上运行的协议，用于鉴别TCP连接的用户。使用标准的这种服务可以获得许多计算机的信息。但是它可作为许多服务的记录器，尤其是FTP、POP、IMAP、SMTP和IRC等服务。通常如果有许多客户通过防火墙访问这些服务，将会看到许多这个端口的连接请求。记住，如果阻断这个端口客户端会感觉到在防火墙另一边与E-MAIL服务器的缓慢连接。许多防火墙支持TCP连接的阻断过程中发回RST。这将会停止缓慢的连接。

端口：119
服务：Network News Transfer Protocol
说明：NEWS新闻组传输协议，承载USENET通信。这个端口的连接通常是人们在寻找USENET服务器。多数ISP限制，只有他们的客户才能访问他们的新闻组服务器。打开新闻组服务器将允许发/读任何人的帖子，访问被限制的新闻组服务器，匿名发帖或发送SPAM。

端口：135
服务：Location Service
说明：Microsoft在这个端口运行DCE RPC end-point mapper为它的DCOM服务。这与UNIX 111端口的功能很相似。使用DCOM和RPC的服务利用计算机上的end-point mapper注册它们的位置。远端客户连接到计算机时，它们查找end-point mapper找到服务的位置。HACKER扫描计算机的这个端口是为了找到这个计算机上运行Exchange Server吗？什么版本？还有些DOS攻击直接针对这个端口。

端口：137、138、139
服务：NETBIOS Name Service
说明：其中137、138是UDP端口，当通过网上邻居传输文件时用这个端口。而139端口：通过这个端口进入的连接试图获得NetBIOS/SMB服务。这个协议被用于windows文件和打印机共享和SAMBA。还有WINS Regisrtation也用它。

端口：143
服务：Interim Mail Access Protocol v2
说明：和POP3的安全问题一样，许多IMAP服务器存在有缓冲区溢出漏洞。记住：一种LINUX蠕虫（admv0rm）会通过这个端口繁殖，因此许多这个端口的扫描来自不知情的已经被感染的用户。当REDHAT在他们的LINUX发布版本中默认允许IMAP后，这些漏洞变的很流行。这一端口还被用于IMAP2，但并不流行。

端口：161
服务：SNMP
说明：SNMP允许远程管理设备。所有配置和运行信息的储存在数据库中，通过SNMP可获得这些信息。许多管理员的错误配置将被暴露在Internet。Cackers将试图使用默认的密码public、private访问系统。他们可能会试验所有可能的组合。SNMP包可能会被错误的指向用户的网络。

端口：177
服务：X Display Manager Control Protocol
说明：许多**者通过它访问X-windows操作台，它同时需要打开6000端口。

端口：389
服务：LDAP、ILS
说明：轻型目录访问协议和NetMeeting Internet Locator Server共用这一端口。

端口：443
服务：Https
说明：网页浏览端口，能提供加密和通过安全端口传输的另一种HTTP。

端口：456
服务：[NULL]
说明：木马HACKERS PARADISE开放此端口。

端口：513
服务：Login,remote login
说明：是从使用cable modem或DSL登陆到子网中的UNIX计算机发出的广播。这些人为**者进入他们的系统提供了信息。

端口：544
服务：[NULL]
说明：kerberos kshell

端口：548
服务：Macintosh,File Services(AFP/IP)
说明：Macintosh,文件服务。

端口：553
服务：CORBA IIOP （UDP）
说明：使用cable modem、DSL或VLAN将会看到这个端口的广播。CORBA是一种面向对象的RPC系统。**者可以利用这些信息进入系统。

端口：555
服务：DSF
说明：木马PhAse1.0、Stealth Spy、IniKiller开放此端口。

端口：568
服务：Membership DPA
说明：成员资格 DPA。

端口：569
服务：Membership MSN
说明：成员资格 MSN。

端口：635
服务：mountd
说明：Linux的mountd Bug。这是扫描的一个流行BUG。大多数对这个端口的扫描是基于UDP的，但是基于TCP的mountd有所增加（mountd同时运行于两个端口）。记住mountd可运行于任何端口（到底是哪个端口，需要在端口111做portmap查询），只是Linux默认端口是635，就像NFS通常运行于2049端口。

端口：636
服务：LDAP
说明：SSL（Secure Sockets layer）

端口：666
服务：Doom Id Software
说明：木马Attack FTP、Satanz Backdoor开放此端口

端口：993
服务：IMAP
说明：SSL（Secure Sockets layer）

端口：1001、1011
服务：[NULL]
说明：木马Silencer、WebEx开放1001端口。木马Doly Trojan开放1011端口。

# 八：计算机端口大全
0端口：无效端口,通常用于分析操作系统
1端口：传输控制协议端口服务多路开关选择器
2端口：管理实用程序
3端口：压缩进程
5端口：远程作业登录
7端口：回显
9端口：丢弃
11端口：在线用户
13端口：时间
17端口：每日引用
18端口：消息发送协议
19端口：字符发生器
20端口：FTP文件传输协议(默认数据口)
21端口：FTP文件传输协议(控制)
22端口：SSH远程登录协议
23端口：telnet(终端仿真协议),木马Tiny Telnet Server开放此端口
24端口：预留给个人用邮件系统
25端口：SMTP服务器所开放的端口，用于发送邮件
27端口：NSW 用户系统 FE
29端口：MSG ICP
31端口：MSG验证,木马Master Paradise、HackersParadise开放此端口
33端口：显示支持协议
35端口：预留给个人打印机服务
37端口：时间
38端口：路由访问协议
39端口：资源定位协议
41端口：图形
42端口：主机名服务
43端口：who is服务
44端口：MPM(消息处理模块)标志协议
45端口：消息处理模块
46端口：消息处理模块(默认发送口)
47端口：NI FTP
48端口：数码音频后台服务
49端口：TACACS登录主机协议
50端口：远程邮件检查协议
51端口：IMP(接口信息处理机)逻辑地址维护
52端口：施乐网络服务系统时间协议
53端口：dns域名服务器
54端口：施乐网络服务系统票据交换
55端口：ISI图形语言
56端口：施乐网络服务系统验证
57端口：预留个人用终端访问
58端口：施乐网络服务系统邮件
59端口：预留个人文件服务
60端口：未定义
61端口：NI邮件
62端口：异步通讯适配器服务
63端口：whois++
64端口：通讯接口
65端口：TACACS数据库服务
66端口：Oracle SQL*NET
67端口：引导程序协议服务端
68端口：引导程序协议客户端
69端口：小型文件传输协议
70端口：信息检索协议
71端口：远程作业服务
72端口：远程作业服务
73端口：远程作业服务
74端口：远程作业服务
75端口：预留给个人拨出服务
76端口：分布式外部对象存储
77端口：预留给个人远程作业输入服务
78端口：修正TCP
79端口：查询远程主机在线用户等信息
80端口：http,用于网页浏览,木马Executor开放此端口
81端口：HOST2名称服务
82端口：传输实用程序
83端口：模块化智能终端ML设备
84端口：公用追踪设备
85端口：模块化智能终端ML设备
86端口：Micro Focus Cobol编程语言
87端口：预留给个人终端连接
88端口：Kerberros安全认证系统
89端口：SU/MIT telnet(终端仿真网关)
90端口：DNSIX 安全属性标记图
91端口：MIT Dover假脱机
92端口：网络打印协议
93端口：设备控制协议
94端口：Tivoli对象调度
96端口：DIXIE协议规范
97端口：快速远程虚拟文件协议
98端口：TAC新闻协议
99端口：后门程序ncx99开放此端口
100端口：未知用途
101端口：NIC 主机名称服务
102端口：消息传输代理
103端口：Genesis 点对点传输网络
105端口：信箱名称服务
106端口：3COM-TSMUX开放端口
107端口：远程Telnet服务
108端口：SNA 网关访问服务
109端口：POP2服务器开放此端口,用于接收邮件
110端口：POP3服务器开放此端口,用于接收邮件
111端口：SUN公司的RPC服务所有端口
112端口：McIDAS 数据传输协议
113端口：认证服务，用于鉴别TCP连接的用户
114端口：音频新闻多点服务
115端口：简单文件传输服务
116端口：ANSA REX 通知
117端口：UUCP 路径服务
118端口：SQL 服务
119端口：NEWS新闻组传输协议，承载USENET通信
121端口：木马BO jammerkillahV开放端口
122端口：SMAKY网络
123端口：网络时间协议，蠕虫病毒会利用，一般关闭
128端口：GSS X许可认证
129端口：密码生成器协议
130端口：Cisco软件开放端口
131端口：Cisco软件开放端口
132端口：Cisco软件开放端口
133端口：统计服务
134端口：INGRES-网络服务
135端口：DCOM服务，冲击波病毒利用，不能关闭
136端口：命名系统
137端口：NETBIOS协议应用，为共享开放
138端口：NETBIOS协议应用，为共享开放
139端口：NETBIOS协议应用，为共享开放
140端口：EMFIS数据服务
141端口：EMFIS控制服务
143端口：Interim邮件访问协议
144端口：UMA软件开放端口
145端口：UAAC协议
149端口：AED 512仿真服务
150端口：SQL(结构化查询语言)-网络
152端口：后台文件传输协议
156端口：SQL(结构化查询语言)服务
158端口：PC邮件服务器
159端口：NSS-路由
160端口：SGMP-陷阱
161端口：简单网络管理协议
162端口：SNMP陷阱
163端口：CMIP/TCP 管理
164端口：CMIP/TCP 代理
166端口：Sirius系统
169端口：发送
170端口：网络附言
177端口：x显示管理控制协议，入侵者通过它访问X-windows操作台
178端口：NextStep Window 服务
179端口：边界网关协议
180端口：图表
181端口：统一
184端口：OC服务器
185端口：远程-KIS
186端口：KIS 协议
187端口：应用通信接口
189端口：队列文件传输
190端口：网关进入控制协议
191端口：Prospero 目录服务
192端口：OSU 网络监视系统
193端口：Spider 远程控制协议
194端口：多线交谈协议
197端口：目录地址服务
198端口：目录地址服务监视器
200端口：IBM系统资源控制器
201端口：AppleTalk(Mac机所用的网络协议)路由保证
202端口：AppleTalk(Mac机所用的网络协议)Name Binding
203端口：AppleTalk(Mac机所用的网络协议)未用端口
204端口：AppleTalk(Mac机所用的网络协议)回显
205端口：AppleTalk(Mac机所用的网络协议)未用端口
206端口：AppleTalk(Mac机所用的网络协议)区信息
207端口：AppleTalk(Mac机所用的网络协议)未用端口
208端口：AppleTalk(Mac机所用的网络协议)未用端口
209端口：快速邮件传输协议
210端口：ANSI(美国国家标准协会)Z39.50
211端口：Texas Instruments 914C/G终端
213端口：IPX(以太网所用的协议)
218端口：Netix消息记录协议
219端口：Unisys ARPs
220端口：交互邮件访问协议 v3
223端口：证书分发中心
224端口：masq拨号器
241端口：预留端口 (224-241)
245端口：链接
246端口：显示系统协议
257端口：安全电子交易系统
258端口：Yak Winsock 个人聊天
259端口：有效短程遥控
260端口：开放端口
261端口：IIOP 基于TLS/SSL的命名服务
266端口：SCSI(小型计算机系统接口)on ST
267端口：Tobit David服务层
268端口：Tobit David复制
281端口：个人连结
282端口：Cable端口A/X
286端口：FXP通信
308端口：Novastor备份
313端口：Magenta逻辑
318端口：PKIX时间标记
333端口：Texar安全端口
344端口：Prospero数据存取协议
345端口：Perf分析工作台
346端口：Zebra服务器
347端口：Fatmen服务器
348端口：Cabletron管理协议
358端口：Shrink可上网家电协议
359端口：网络安全风险管理协议
362端口：SRS发送
363端口：RSVP隧道
372端口：列表处理
373端口：Legend公司
374端口：Legend公司
376端口：AmigaEnvoy网络查询协议
377端口：NEC公司
378端口：NEC公司
379端口：TIA/EIA/IS-99调制解调器客户端
380端口：TIA/EIA/IS-99调制解调器服务器
381端口：hp(惠普)性能数据收集器
382端口：hp(惠普)性能数据控制节点
383端口：hp(惠普)性能数据警报管理
384端口：远程网络服务器系统
385端口：IBM应用程序
386端口：ASA信息路由器定义文件.
387端口：Appletalk更新路由.
389端口：轻型目录访问协议
395端口：网络监视控制协议
396端口：Novell(美国Novell公司)Netware(Novell公司出的网络操作系统)over IP
400端口：工作站解决方案
401端口：持续电源
402端口：Genie协议
406端口：交互式邮件支持协议
408端口：Prospero资源管理程序
409端口：Prospero资源节点管理.
410端口：DEC(数据设备公司)远程调试协议
411端口：远程MT协议
412端口：陷阱协定端口
413端口：存储管理服务协议
414端口：信息查询
415端口：B网络
423端口：IBM操作计划和控制开端
424端口：IBM操作计划和控制追踪
425端口：智能计算机辅助设计
427端口：服务起位置
434端口：移动ip代理
435端口：移动ip管理
443端口：基于TLS/SSL的网页浏览端口，能提供加密和通过安全端口传输的另一种HTTP
444端口：简单网络内存分页协议
445端口：Microsoft-DS，为共享开放，震荡波病毒利用，一般应关闭
446端口：DDM-远程关系数据库访问
447端口：DDM-分布式文件管理
448端口：DDM-使用安全访问远程数据库
456端口：木马HACKERS PARADISE开放此端口
458端口：apple quick time软件开放端口
459端口：ampr-rcmd命令
464端口：k密码服务
469端口：广播控制协议
470端口：scx-代理
472端口：ljk-登陆
481端口：Ph服务
487端口：简单异步文件传输
489端口：nest-协议
491端口：go-登陆
499端口：ISO ILL协议
500端口：Internet密钥交换，Lsass开放端口，不能关闭
509端口：陷阱
510端口：FirstClass协议
512端口：远程进程执行
513端口：远程登陆
514端口：cmd命令
515端口：spooler
516端口：可视化数据
518端口：交谈
519端口：unix时间
520端口：扩展文件名称服务器
525端口：时间服务
526端口：新日期
529端口：在线聊天系统服务
530端口：远程过程调用
531端口：聊天
532端口：读新闻
533端口：紧急广播端口
534端口：MegaMedia管理端
537端口：网络流媒体协议
542端口：商业
543端口：Kerberos(软件)v4/v5
544端口：krcmd命令
546端口：DHCPv6 客户端
547端口：DHCPv6 服务器
552端口：设备共享
554端口：Real Time Stream控制协议
555端口：木马PhAse1.0、Stealth Spy、IniKiller开放此端口
556端口：远距离文件服务器
563端口：基于TLS/SSL的网络新闻传输协议
564端口：plan 9文件服务
565端口：whoami查询
566端口：streettalk
567端口：banyan-rpc(远程过程调用)
568端口：DPA成员资格
569端口：MSN成员资格
570端口：demon(调试监督程序)
571端口：udemon(调试监督程序)
572端口：声纳
573端口：banyan-贵宾
574端口：FTP软件代理系统
581端口：Bundle Discovery 协议
582端口：SCC安全
583端口：Philips视频会议
584端口：密钥服务器
585端口：IMAP4+SSL (Use 993 instead)
586端口：密码更改
587端口：申请
589端口：Eye连结
595端口：CAB协议
597端口：PTC名称服务
598端口：SCO网络服务器管理3
599端口：Aeolon Core协议
600端口：Sun IPC(进程间通讯)服务器
601端口：可靠系统登陆服务
604端口：通道
606端口：Cray统一资源管理
608端口：发送人-传递/提供 文件传输器
609端口：npmp-陷阱
610端口：npmp-本地
611端口：npmp-gui( 图形用户界面)
612端口：HMMP指引
613端口：HMMP操作
614端口：SSL(加密套接字协议层)shell(壳)
615端口：Internet配置管理
616端口：SCO(Unix系统)系统管理服务器
617端口：SCO桌面管理服务器
619端口：Compaq(康柏公司)EVM
620端口：SCO服务器管理
623端口：ASF远程管理控制协议
624端口：Crypto管理
631端口：IPP (Internet打印协议)
633端口：服务更新(Sterling软件)
637端口：局域网服务器
641端口：repcmd命令
647端口：DHCP(动态主机配置协议)Failover
648端口：注册登记协议(RRP)
649端口：Cadview-3d软件协议
666端口：木马Attack FTP、Satanz Backdoor开放此端口
808端口：ccproxy http/gopher/ftp (over http)协议
1001端口：木马Silencer，WebEx开放端口
1011端口：木马Doly开放端口
1024端口：动态端口的开始,木马yai开放端口
1025端口：inetinfo.exe(互联网信息服务)木马netspy开放端口
1026端口：inetinfo.exe(互联网信息服务)
1027端口：应用层网关服务
1030端口：应用层网关服务
1031端口：BBN IAD
1033端口：本地网络信息端口
1034端口：同步通知
1036端口：安全部分传输协议
1070端口：木马Psyber Stream，Streaming Audio开放端口
1071端口：网络服务开放端口
1074端口：网络服务开放端口
1080端口：Socks这一协议以通道方式穿过防火墙，允许防火墙后面的人通过一个IP地址访问INTERNET
1110端口：卡巴斯基反病毒软件开放此端口
1125端口：卡巴斯基反病毒软件开放此端口
1203端口：许可证生效端口
1204端口：登陆请求监听端口
1206端口：Anthony数据端口
1222端口：SNI R&D网络端口
1233端口：普遍的附录服务器端口
1234端口：木马SubSeven2.0、Ultors Trojan开放此端口
1243端口：木马SubSeven1.0/1.9开放此端口
1245端口：木马Vodoo，GabanBus，NetBus，Vodoo开放此端口
1273端口：EMC-网关端口
1289端口：JWalk服务器端口
1290端口：WinJa服务器端口
1333端口：密码策略(网络服务)(svchost.exe)
1334端口：网络服务(svchost.exe)
1335端口：数字公正协议
1336端口：即时聊天协议(svchost.exe)
1349端口：注册网络协议端口
1350端口：注册网络协议端口
1371端口：富士通配置协议端口
1372端口：富士通配置协议端口
1374端口：EPI软件系统端口
1376端口：IBM个人-个人软件端口
1377端口：Cichlid许可证管理端口
1378端口：Elan许可证管理端口
1380端口：Telesis网络许可证管理端口
1381端口：苹果网络许可证管理端口
1386端口：CheckSum 许可证管理端口
1387端口：系统开放端口(rundll32.exe)
1388端口：数据库高速缓存端口
1389端口：文档管理端口
1390端口：存储控制器端口
1391端口：存储器存取服务器端口
1392端口：打印管理端口
1393端口：网络登陆服务器端口
1394端口：网络登陆客户端端口
1395端口：PC工作站管理软件端口
1396端口：DVL活跃邮件端口
1397端口：音频活跃邮件端口
1398端口：视频活跃邮件端口
1399端口：Cadkey许可证管理端口
1433端口：Microsoft的SQL服务开放端口
1434端口：Microsoft的SQL服务监视端口
1492端口：木马FTP99CMP开放此端口
1509端口：木马Psyber Streaming Server开放此端口
1512端口：Microsoft Windows网络名称服务
1524端口：许多攻击脚本安装一个后门SHELL于这个端口
1600端口：木马Shivka-Burka开放此端口
1645端口：远程认证拨号用户服务
1701端口：第2层隧道协议
1731端口：NetMeeting音频调用控制
1801端口：Microsoft消息队列服务器
1807端口：木马SpySender开放此端口
1900端口：可被利用ddos攻击，一般关闭
1912端口：金山词霸开放此端口
1981端口：木马ShockRave开放此端口
1999端口：木马BackDoor,yai开放此端口
2000端口：木马GirlFriend 1.3、Millenium 1.0开放此端口
2001端口：木马Millenium 1.0、Trojan Cow,黑洞2001开放此端口
2003端口：GNU 查询
2023端口：木马Pass Ripper开放此端口
2049端口：NFS程序常运行于此端口
2115端口：木马Bugs开放此端口
2140端口：木马Deep Throat 1.0/3.0，The Invasor开放此端口
2500端口：应用固定端口会话复制的RPC客户
2504端口：网络平衡负荷
2565端口：木马Striker开放此端口
2583端口：木马Wincrash 2.0开放此端口
2801端口：木马Phineas Phucker开放此端口
2847端口：诺顿反病毒服务开放此端口
3024端口：木马WinCrash开放此端口
3128端口：squid http代理服务器开放此端口
3129端口：木马Master Paradise开放此端口
3150端口：木马The Invasor,deep throat开放此端口
3210端口：木马SchoolBus开放此端口
3306端口：MySQL开放此端口
3333端口：木马Prosiak开放此端口
3389端口：WINDOWS 2000终端开放此端口
3456端口：inetinfo.exe(互联网信息服务)开放端口，VAT默认数据
3457端口：VAT默认控制
3527端口：Microsoft消息队列服务器
3700端口：木马Portal of Doom开放此端口
3996端口：木马RemoteAnything开放此端口
4000端口：腾讯QQ客户端开放此端口
4060端口：木马RemoteAnything开放此端口
4092端口：木马WinCrash开放此端口
4133端口：NUTS Bootp服务器
4134端口：NIFTY-Serve HMI协议
4141端口：Workflow服务器
4142端口：文档服务器
4143端口：文档复制
4145端口：VVR控制
4321端口：远程Who Is查询
4333端口：微型sql服务器
4349端口：文件系统端口记录
4350端口：网络设备
4351端口：PLCY网络服务
4453端口：NSS警报管理
4454端口：NSS代理管理
4455端口：PR聊天用户
4456端口：PR聊天服务器
4457端口：PR注册
4480端口：Proxy+ HTTP代理端口
4500端口：Lsass开放端口，不能关闭
4547端口：Lanner许可管理
4555端口：RSIP端口
4590端口：木马ICQTrojan开放此端口
4672端口：远程文件访问服务器
4752端口：简单网络音频服务器
4800端口：Icona快速消息系统
4801端口：Icona网络聊天
4802端口：Icona许可系统服务器
4848端口：App服务器-Admin HTTP
4849端口：App服务器-Admin HTTPS
4950端口：木马IcqTrojan开放5000端口
5000端口：木马blazer5，Sockets de Troie开放5000端口，一般应关闭
5001端口：木马Sockets de Troie开放5001端口
5006端口：wsm服务器
5007端口：wsm服务器ssl
5022端口：mice服务器
5050端口：多媒体会议控制协议
5051端口：ITA代理
5052端口：ITA管理
5137端口：MyCTS服务器端口
5150端口：Ascend通道管理协议
5154端口：BZFlag游戏服务器
5190端口：America-Online(美国在线)
5191端口：AmericaOnline1(美国在线)
5192端口：AmericaOnline2(美国在线)
5193端口：AmericaOnline3(美国在线)
5222端口：Jabber客户端连接
5225端口：HP(惠普公司)服务器
5226端口：HP(惠普公司)
5232端口：SGI绘图软件端口
5250端口：i网关
5264端口：3Com网络端口1
5265端口：3Com网络端口2
5269端口：Jabber服务器连接
5306端口：Sun MC组
5321端口：木马Sockets de Troie开放5321端口
5400端口：木马Blade Runner开放此端口
5401端口：木马Blade Runner开放此端口
5402端口：木马Blade Runner开放此端口
5405端口：网络支持
5409端口：Salient数据服务器
5410端口：Salient用户管理
5415端口：NS服务器
5416端口：SNS网关
5417端口：SNS代理
5421端口：网络支持2
5423端口：虚拟用户
5427端口：SCO-PEER-TTA(Unix系统)
5432端口：PostgreSQL数据库
5550端口：木马xtcp开放此端口
5569端口：木马Robo-Hack开放此端口
5599端口：公司远程安全安装
5600端口：公司安全管理
5601端口：公司安全代理
5631端口：pcANYWHERE(软件)数据
5632端口：pcANYWHERE(软件)数据
5673端口：JACL消息服务器
5675端口：V5UA应用端口
5676端口：RA管理
5678端口：远程复制代理连接
5679端口：直接电缆连接
5720端口：MS-执照
5729端口：Openmail用户代理层
5730端口：Steltor's日历访问
5731端口：netscape(网景)suiteware
5732端口：netscape(网景)suiteware
5742端口：木马WinCrash1.03开放此端口
5745端口：fcopy-服务器
5746端口：fcopys-服务器
5755端口：OpenMail(邮件服务器)桌面网关服务器
5757端口：OpenMail(邮件服务器)X.500目录服务器
5766端口：OpenMail (邮件服务器)NewMail服务器
5767端口：OpenMail (邮件服务器)请求代理曾(安全)
5768端口：OpenMail(邮件服务器) CMTS服务器
5777端口：DALI端口
5800端口：虚拟网络计算
5801端口：虚拟网络计算
5802端口：虚拟网络计算HTTP访问, d
5803端口：虚拟网络计算HTTP访问, d
5900端口：虚拟网络计算机显示0
5901端口：虚拟网络计算机显示1
5902端口：虚拟网络计算机显示2
5903端口：虚拟网络计算机显示3
6000端口：X Window 系统
6001端口：X Window 服务器
6002端口：X Window 服务器
6003端口：X Window 服务器
6004端口：X Window 服务器
6005端口：X Window 服务器
6006端口：X Window 服务器
6007端口：X Window 服务器
6008端口：X Window 服务器
6009端口：X Window 服务器
6456端口：SKIP证书发送
6471端口：LVision许可管理器
6505端口：BoKS管理私人端口
6506端口：BoKS管理公共端口
6507端口：BoKS Dir服务器,私人端口
6508端口：BoKS Dir服务器,公共端口
6509端口：MGCS-MFP端口
6510端口：MCER端口
6566端口：SANE控制端口
6580端口：Parsec主服务器
6581端口：Parsec对等网络
6582端口：Parsec游戏服务器
6588端口：AnalogX HTTP代理端口
6631端口：Mitchell电信主机
6667端口：Internet多线交谈
6668端口：Internet多线交谈
6670端口：木马Deep Throat开放此端口
6671端口：木马Deep Throat 3.0开放此端口
6699端口：Napster文件(MP3)共享服务
6701端口：KTI/ICAD名称服务器
6788端口：SMC软件-HTTP
6789端口：SMC软件-HTTPS
6841端口：Netmo软件默认开放端口
6842端口：Netmo HTTP服务
6883端口：木马DeltaSource开放此端口
6939端口：木马Indoctrination开放此端口
6969端口：木马Gatecrasher、Priority开放此端口
6970端口：real音频开放此端口
7000端口：木马Remote Grab开放此端口
7002端口：使用者& 组 数据库
7003端口：音量定位数据库
7004端口：AFS/Kerberos认证服务
7005端口：音量管理服务
7006端口：错误解释服务
7007端口：Basic监督进程
7008端口：服务器-服务器更新程序
7009端口：远程缓存管理服务
7011端口：Talon软件发现端口
7012端口：Talon软件引擎
7013端口：Microtalon发现
7014端口：Microtalon通信
7015端口：Talon网络服务器
7020端口：DP服务
7021端口：DP服务管理
7100端口：X字型服务
7121端口：虚拟原型许可证管理
7300端口：木马NetMonitor开放此端口
7301端口：木马NetMonitor开放此端口
7306端口：木马NetMonitor，NetSpy1.0开放此端口
7307端口：木马NetMonitor开放此端口
7308端口：木马NetMonitor开放此端口
7323端口：Sygate服务器端
7511端口：木马聪明基因开放此端口
7588端口：Sun许可证管理
7597端口：木马Quaz开放此端口
7626端口：木马冰河开放此端口
7633端口：PMDF管理
7674端口：iMQ SSL通道
7675端口：iMQ通道
7676端口：木马Giscier开放此端口
7720端口：Med图象入口
7743端口：Sakura脚本传递协议
7789端口：木马ICKiller开放此端口
7797端口：Propel连接器端口
7798端口：Propel编码器端口
8000端口：腾讯QQ服务器端开放此端口
8001端口：VCOM通道
8007端口：Apache(类似iis)jServ协议1.x
8008端口：HTTP Alternate
8009端口：Apache(类似iis)JServ协议1.3
8010端口：Wingate代理开放此端口
8011端口：木马way2.4开放此端口
8022端口：OA-系统
8080端口：WWW代理开放此端口
8081端口：ICECap控制台
8082端口：BlackIce(防止黑客软件)警报发送到此端口
8118端口：Privoxy HTTP代理
8121端口：Apollo数据端口
8122端口：Apollo软件管理端口
8181端口：Imail
8225端口：木马灰鸽子开放此端口
8311端口：木马初恋情人开放此端口
8351端口：服务器寻找
8416端口：eSpeech Session协议
8417端口：eSpeech RTP协议
8473端口：虚拟点对点
8668端口：网络地址转换
8786端口：Message客户端
8787端口：Message服务器
8954端口：Cumulus管理端口
9000端口：CS监听
9001端口：ETL服务管理
9002端口：动态id验证
9021端口：Pangolin验证
9022端口：PrivateArk远程代理
9023端口：安全网络登陆-1
9024端口：安全网络登陆-2
9025端口：安全网络登陆-3
9026端口：安全网络登陆-4
9101端口：Bacula控制器
9102端口：Bacula文件后台
9103端口：Bacula存储邮件后台
9111端口：DragonIDS控制台
9217端口：FSC通讯端口
9281端口：软件传送端口1
9282端口：软件传送端口2
9346端口：C技术监听
9400端口：木马Incommand 1.0开放此端口
9401端口：木马Incommand 1.0开放此端口
9402端口：木马Incommand 1.0开放此端口
9594端口：信息系统
9595端口：Ping Discovery服务
9800端口：WebDav源端口
9801端口：Sakura脚本转移协议-2
9802端口：WebDAV Source TLS/SSL
9872端口：木马Portal of Doom开放此端口
9873端口：木马Portal of Doom开放此端口
9874端口：木马Portal of Doom开放此端口
9875端口：木马Portal of Doom开放此端口
9899端口：木马InIkiller开放此端口
9909端口：域名时间
9911端口：SYPECom传送协议
9989端口：木马iNi-Killer开放此端口
9990端口：OSM Applet程序服务器
9991端口：OSM事件服务器
10000端口：网络数据管理协议
10001端口：SCP构造端口
10005端口：安全远程登陆
10008端口：Octopus多路器
10067端口：木马iNi-Killer开放此端口
10113端口：NetIQ端点
10115端口：NetIQ端点
10116端口：NetIQVoIP鉴定器
10167端口：木马iNi-Killer开放此端口
11000端口：木马SennaSpy开放此端口
11113端口：金山词霸开放此端口
11233端口：木马Progenic trojan开放此端口
12076端口：木马Telecommando开放此端口
12223端口：木马Hack'99 KeyLogger开放此端口
12345端口：木马NetBus1.60/1.70、GabanBus开放此端口
12346端口：木马NetBus1.60/1.70、GabanBus开放此端口
12361端口：木马Whack-a-mole开放此端口
13223端口：PowWow 客户端，是Tribal Voice的聊天程序
13224端口：PowWow 服务器，是Tribal Voice的聊天程序
16959端口：木马Subseven开放此端口
16969端口：木马Priority开放此端口
17027端口：外向连接
19191端口：木马蓝色火焰开放此端口
20000端口：木马Millennium开放此端口
20001端口：木马Millennium开放此端口
20034端口：木马NetBus Pro开放此端口
21554端口：木马GirlFriend开放此端口
22222端口：木马Prosiak开放此端口
23444端口：木马网络公牛开放此端口
23456端口：木马Evil FTP、Ugly FTP开放此端口
25793端口：Vocaltec地址服务器
26262端口：K3软件-服务器
26263端口：K3软件客户端
26274端口：木马Delta开放此端口
27374端口：木马Subseven 2.1开放此端口
30100端口：木马NetSphere开放此端口
30129端口：木马Masters Paradise开放此端口
30303端口：木马Socket23开放此端口
30999端口：木马Kuang开放此端口
31337端口：木马BO(Back Orifice)开放此端口
31338端口：木马BO(Back Orifice)，DeepBO开放此端口
31339端口：木马NetSpy DK开放此端口
31666端口：木马BOWhack开放此端口
31789端口：Hack-a-tack
32770端口：sun solaris RPC服务开放此端口
33333端口：木马Prosiak开放此端口
33434端口：路由跟踪
34324端口：木马Tiny Telnet Server、BigGluck、TN开放此端口
36865端口：KastenX软件端口
38201端口：Galaxy7软件数据通道
39681端口：TurboNote默认端口
40412端口：木马The Spy开放此端口
40421端口：木马Masters Paradise开放此端口
40422端口：木马Masters Paradise开放此端口
40423端口：木马Masters Paradise开放此端口
40426端口：木马Masters Paradise开放此端口
40843端口：CSCC 防火墙
43210端口：木马SchoolBus 1.0/2.0开放此端口
43190端口：IP-PROVISION
44321端口：PCP服务器(pmcd)
44322端口：PCP服务器(pmcd)代理
44334端口：微型个人防火墙端口
44442端口：ColdFusion软件端口
44443端口：ColdFusion软件端口
44445端口：木马Happypig开放此端口
45576端口：E代时光专业代理开放此端口
47262端口：木马Delta开放此端口
47624端口：Direct Play服务器
47806端口：ALC协议
48003端口：Nimbus网关
50505端口：木马Sockets de Troie开放此端口
50766端口：木马Fore开放此端口
53001端口：木马Remote Windows Shutdown开放此端口
54320端口：木马bo2000开放此端口
54321端口：木马SchoolBus 1.0/2.0开放此端口
61466端口：木马Telecommando开放此端口
65000端口：木马Devil 1.03开放此端口
65301端口：PC Anywhere软件开放端口