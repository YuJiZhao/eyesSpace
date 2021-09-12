---
title: 使用java.io.File操作文件及文件夹
date: 2021-09-11 17:37:55
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/42.jpg
tags: 
  - IO流
  - 文件
categories: java
---

# 一：File类的概述
java有个io包，java用于操作流的对象都在io包中，io流简单来说就是input和output流，io流主要是用来处理设备之间的数据传输，Java IO对于数据的操作都是通过流实现的。

在整个java.io包中最重要的就是5个类和一个接口。5个类指的是File、OutputStream、InputStream、Writer、Reader；一个接口指的是Serializable。而这篇博客只会涉及到5个类中的File类。

java.io.File类是文件和目录路径名的抽象表示，主要用于文件和目录的创建查找和删除等操作。在Java中，File 类是 java.io 包中唯一代表磁盘文件本身的对象，但是File 类不能访问文件内容本身，如果需要访问文件内容本身，则需要使用输入/输出流。

java.io.File类的方法：
+ 创建一个文件/文件夹
+ 删除一个文件/文件夹
+ 获取文件/文件夹
+ 判断文件/文件夹是否存在
+ 对文件夹进行遍历
+ 获取文件大小

File类的学习主要是围绕三个单词：
+ file: 文件
+ directory: 文件夹/目录
+ path: 路径

# 二：File类的静态成员变量
+ `static String pathSeparator` ：与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
+ `static char pathSeparatorChar` ：与系统有关的路径分隔符。
+ `static String separator` ：与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
+ `static char separatorChar` ：与系统有关的默认名称分隔符

路径分隔符和文件分隔符在不同操作系统下是不同的，路径分隔符在Windows下是";"，Linux下是":"，文件名称分隔符在Windows下是"\"，而Linux下是"/"。

比如Windows操作系统下有这样一个路径：
```
C:\Windows\Help
```
那么在Linux下就是这样的：

```
C:/Windows/Help
```
因此经行路径相关操作时路径不能绝对化，以上面的路径为例，应该写成这种形式：

```
"C" + File
```

```java
package base.file;
import java.io.File;

public class staticVar {
    /*
        static String pathSeparator 与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
        static char pathSeparatorChar 与系统有关的路径分隔符。
        static String separator 与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
        static char separatorChar 与系统有关的默认名称分隔符
    */
    public static void main(String[] args) {
        String pathSeparator = File.pathSeparator;  // 路径分隔符，Windows下是";"，Linux下是":"
        char pathSeparatorChar = File.pathSeparatorChar;
        String separator = File.separator;  // 文件名称分隔符，Windows下是"\"，而Linux下是"/"
        char separatorChar = File.separatorChar;

        System.out.println(pathSeparator);
        System.out.println(pathSeparatorChar);
        System.out.println(separator);
        System.out.println(separatorChar);

        System.out.println("C:" + File.separator + "Windows" + File.separator + "Help");
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f9e741cea6994c349ce199b631ab983b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 三：File类的构造方法
File 类提供了如下三种形式构造方法。
+ File(String path)：如果 path 是实际存在的路径，则该 File 对象表示的是目录；如果 path 是文件名，则该 File 对象表示的是文件。
+ File(String parent, String child)：parent 是路径名，name 是文件名。
+ File(File dir, String name)：dir 是路径对象，name 是文件名。

三种方法的使用以及一些说明都在代码里：
```java
package base.file;

import java.io.File;

public class fileStruct {
    // 方法一
    private static void show01() {
        /*
            File(String pathname) 通过将给定路径名字符串转换为抽象路径来创建一个新的File实例
            参数：
                String pathname: 字符串的路径名称
                路径可以是文件结尾，也可以是文件夹结尾
                路径可以是相对路径，也可以是绝对路径
                创建File对象，只是把字符串路径封装为File对象，不考虑路径的真假情况
         */

        // 测试一个绝对路径
        File f1 = new File("E:\\fileTest");  // 此处需要写两个反斜杠，因为在java中一个反斜杠代表转义，两个反斜杠才是一个反斜杠的意思
        System.out.println(f1);  // 输出 "E:\fileTest"，说明重写了Object类的toString方法

        // 测试一个相对路径
        File f2 = new File("fileTest");
        System.out.println(f2);  // 输出 fileTest
    }

    // 方法二
    private static void show02(String parent, String child) {
        /*
            File(String parent, String child) 根据parent路径名字符串和child路径名字符串创建一个新的File实例
            参数：把路径分成了两部分
                String parent: 父路径
                String child: 子路径
            好处：父路径和子路径可以单独书写，使用起来非常灵活，父路径和子路径都可以变化
         */

        File file = new File(parent, child);
        System.out.println(file);
    }

    // 方法三
    private static void show03() {
        /*
            File(File parent, String child) 根据parent抽象路径名和child路径名字符串创建一个新的File实例
            参数：把路径分成了两部分
                File parent: 父路径
                String child: 子路径
            好处：
                父路径和子路径可以单独书写，使用起来非常灵活，父路径和子路径都可以变化
                父路径是File类型，可以使用File的方法对路径经行一些操作，再使用路径创建对象
         */

        File parent = new File("E:\\fileTest");
        File file = new File(parent, "test.txt");
        System.out.println(file);
    }

    // 主函数
    public static void main(String[] args) {
        /*
            File类的构造方法
         */
        System.out.println("----- 方法一输出 -----");
        show01();

        System.out.println("----- 方法二输出 -----");
        show02("E:\\fileTest", "test.txt");  // 输出"E:\fileTest\test.txt"
        show02("D:\\fileTest", "test.java");  // 输出"D:\fileTest\test.java"

        System.out.println("----- 方法三输出 -----");
        show03();  // 输出"E:\fileTest\test.txt"
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/b2f82472082a4fffbe4fb3ea4b545fc8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 四：File类的常用方法：
这里只列一个表，后续内容会分类型对一些方法进行说明。
<table border="1">
<caption>
<br>
表 1&nbsp; File类的常用方法</caption>
<tbody>
<tr>
<th>
方法名称</th>
<th>
说明</th>
</tr>
<tr>
<td>
boolean canRead()</td>
<td>
测试应用程序是否能从指定的文件中进行读取</td>
</tr>
<tr>
<td>
boolean canWrite()</td>
<td>
测试应用程序是否能写当前文件</td>
</tr>
<tr>
<td>
boolean delete()</td>
<td>
删除当前对象指定的文件</td>
</tr>
<tr>
<td>
boolean exists()</td>
<td>
测试当前 File 是否存在</td>
</tr>
<tr>
<td>
String getAbsolutePath()</td>
<td>
返回由该对象表示的文件的绝对路径名</td>
</tr>
<tr>
<td>
String getName()</td>
<td>
返回表示当前对象的文件名或路径名（如果是路径，则返回最后一级子路径名）</td>
</tr>
<tr>
<td>
String getParent()</td>
<td>
返回当前 File 对象所对应目录（最后一级子目录）的父目录名</td>
</tr>
<tr>
<td>
boolean isAbsolute()</td>
<td>
测试当前 File 对象表示的文件是否为一个绝对路径名。该方法消除了不同平台的差异，可以直接判断 file 对象是否为绝对路径。在 UNIX/Linux/BSD 等系统上，如果路径名开头是一条斜线<code>/</code>，则表明该 File 对象对应一个绝对路径；在 Windows 等系统上，如果路径开头是盘符，则说明它是一个绝对路径。</td>
</tr>
<tr>
<td>
boolean isDirectory()</td>
<td>
测试当前 File 对象表示的文件是否为一个路径</td>
</tr>
<tr>
<td>
boolean isFile()</td>
<td>
测试当前 File 对象表示的文件是否为一个“普通”文件</td>
</tr>
<tr>
<td>
long lastModified()</td>
<td>
返回当前 File 对象表示的文件最后修改的时间</td>
</tr>
<tr>
<td>
long length()</td>
<td>
返回当前 File 对象表示的文件长度</td>
</tr>
<tr>
<td>
String[] list()</td>
<td>
返回当前 File 对象指定的路径文件列表</td>
</tr>
<tr>
<td>
String[]&nbsp;list(FilenameFilter)</td>
<td>
返回当前 File 对象指定的目录中满足指定过滤器的文件列表</td>
</tr>
<tr>
<td>
boolean mkdir()</td>
<td>
创建一个目录，它的路径名由当前 File 对象指定</td>
</tr>
<tr>
<td>
boolean mkdirs()</td>
<td>
创建一个目录，它的路径名由当前 File 对象指定</td>
</tr>
<tr>
<td>
boolean renameTo(File)</td>
<td>
将当前 File 对象指定的文件更名为给定参数 File 指定的路径名</td>
</tr>
</tbody>
</table>

# 五：File类获取功能的方法
+ `public String getAbsolutePath()` : 返回此File的绝对路径名字符串
+ `public String getPath()` : 将此File转换为路径名字符串
+ `public String getName()` : 返回由此File表示的文件或者目录的名称
+ `public long length()` : 返回由此File表示的文件的大小，如果路径不存在，则返回0

演示代码如下：

```java
package base.file;
import java.io.File;

public class getMethod {
    /*
         值得注意的是，如果使用相对路径，那么根路径是项目的根路径，
         在这个项目中，根路径是code，因此test.txt的相对路径为src\base\file\fileTest\test.txt
     */
    public static void main(String[] args) {
        File f = new File("src\\base\\file\\fileTest\\test.txt");
        System.out.println("文件绝对路径：" + f.getAbsolutePath());
        System.out.println("文件相对路径：" + f.getPath());
        System.out.println("文件名称：" + f.getName());
        System.out.println("文件大小：" + f.length() + "字节");

        File f2 = new File("src\\base\\file\\fileTest");
        System.out.println("目录绝对路径：" + f2.getAbsolutePath());
        System.out.println("目录相对路径：" + f2.getPath());
        System.out.println("目录名称：" + f2.getName());
        System.out.println("目录大小：" + f2.length());  // 文件夹没有大小概念，因此返回0
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/cc80acefae4c417bb9991e3584b744e8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 六：File类判断功能的方法
+ `public boolean exists()` ：此File表示的文件或目录是否实际存在
+ `public boolean isDirectory()` ：此File表示的是否为目录
+ `public boolean isFile()` ：此File表示的是否为文件

演示代码如下：
```java
package base.file;
import java.io.File;

public class fileJudge {
    public static void main(String[] args) {
        File f1 = new File("src\\base\\file\\fileTest");  // 文件夹
        File f2 = new File("src\\base\\file\\fileTest\\test.txt");  // 文件
        File f3 = new File("src\\base\\file\\test");  // 不存在的文件夹
        File f4 = new File("src\\base\\file\\fileTest\\demo.txt");  // 不存在的文件

        System.out.println(f1.exists());  // true
        System.out.println(f2.exists());  // true
        System.out.println(f3.exists());  // false
        System.out.println(f4.exists());  // false

        System.out.println(f1.isDirectory());  // true
        System.out.println(f2.isDirectory());  // false
        System.out.println(f3.isDirectory());  // false
        System.out.println(f4.isDirectory());  // false

        System.out.println(f1.isFile());  // false
        System.out.println(f2.isFile());  // true
        System.out.println(f3.isFile());  // false
        System.out.println(f4.isFile());  // false
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/19d365d3fc504a24bb30bbf086a7b800.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 七：FIle类创建删除功能的方法
+ `public boolean createNewFile()` ：当且仅当具有该名称的文件尚不存在时，创建一个新的空文件夹
+ `public boolean delete()` ：删除由此File表示的文件或目录
+ `public boolean mkdir()` ：创建由此File表示的目录
+ `public boolean mkdirs` ：创建由此File表示的目录，包括任何必需但不存在的父目录

演示代码如下：
```java
package base.file;
import java.io.File;
import java.io.IOException;

public class fileCreateOrDel {
    // createNewFile()，如果抛出异常，我们调用throws或trycatch捕获处理
    private static void show01() throws IOException {
        /*
            当且仅当具有该名称的文件尚不存在时，创建一个新的空文件夹
            返回值是布尔值
            此方法只能创建文件，不能创建文件夹
            创建文件的路径必须是存在的，否则抛出异常
         */
        File f = new File("src\\base\\file\\fileTest\\new.txt");
        boolean b = f.createNewFile();
        System.out.println("b:" + b);  // true
    }

    // delete()
    private static void show02() throws IOException {
        /*
            该方法可以删除文件，也可以返回文件夹
            删除文件夹时如果文件夹有内容则不删除返回false
            路径不存在则返回false
            delete是直接在硬盘上删除文件/文件夹，不会经过回收站，因此需要谨慎
         */
        File f = new File("src\\base\\file\\fileTest\\test.txt");
        boolean b = f.delete();
        System.out.println(b);  // true
    }

    // mkdir()
    private static void show03() throws IOException {
        /*
            创建单级空文件夹
            文件夹存在会返回false，并且不会创建
            如果用这个方法创建多级文件夹，会返回false而且不创建
         */
        File f1 = new File("src\\base\\file\\fileTest\\new");
        boolean b1 = f1.mkdir();
        System.out.println("b1:" + b1);  // true

        File f2 = new File("src\\base\\file\\fileTest\\hhh\\aaa");
        boolean b2 = f1.mkdir();
        System.out.println("b2:" + b2);  // false
    }
    // mkdirs()
    private static void show04() throws IOException {
        /*
            可以创建单级空文件夹，也可以创建多级空文件夹
            文件夹存在会返回false，并且不会创建
         */
        File f1 = new File("src\\base\\file\\fileTest\\new2");
        boolean b1 = f1.mkdirs();
        System.out.println("b1:" + b1);  // true

        File f2 = new File("src\\base\\file\\fileTest\\bbb\\ccc");
        boolean b2 = f2.mkdirs();
        System.out.println("b2:" + b2);  // true
    }

    // 主函数
    public static void main(String[] args) throws IOException {
        show01();
        show02();
        show03();
        show04();
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/834ed5c2d0514b89a8314cefbad7443c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

# 八：File类遍历目录的功能
+ `public String[] list()` ：返回一个String数组，表示该File目录中的所有子文件或目录
+ `public File[] listFiles()` ：返回一个File数组，表示该File目录中的所有子文件或目录

注意：
+ list方法和listFiles方法遍历的是构造方法中给出的目录
+ 如果构造方法中给出的目录的路径不存在，会抛出空指针异常
+ 如果构造方法中给出的路径不是一个目录，也会抛出空指针异常

演示代码如下：

```java
package base.file;

import java.io.File;

public class fileErgodic {
    // public String[] list()
    private static void show01() {
        /*
            返回一个String数组，表示该File目录中的所有子文件或目录
            遍历构造方法中给出的目录，会获取当前目录中所有文件/文件夹的名称，把获取到的多个名称存储到一个String类型的数组中
            只会遍历当前目录，如果当前目录含有一个文件夹，不会遍历该文件夹内部
         */
        File f = new File("src\\base\\file");
        String[] arr = f.list();
        for (String fileName : arr) {
            System.out.println(fileName);
        }
    }

    // public File[] listFiles()
    private static void show02() {
        /*
            返回一个File数组，表示该File目录中的所有子文件或目录
            遍历构造方法中给出的目录，会获取当前目录中所有文件/文件夹，把文件/文件夹封装为File对象，多个File对象存储到File数组中
         */
        File f = new File("src\\base\\file");
        File[] files = f.listFiles();
        for (File i : files) {
            System.out.println(i);
        }
    }

    // 主函数
    public static void main(String[] args) {
        show01();
        System.out.println("---------------");
        show02();
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/79abdda7cf5b4d7ebd3cbeabfd3f461c.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
# 九：递归打印多级目录
代码如下：

```java
package base.file;
import java.io.File;

public class fileRecursion {
    public static void getAllFile(File dir) {
        System.out.println(dir);
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getAllFile(f);
            } else {
                System.out.println(f);
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("src\\base\\file");
        getAllFile(file);
    }
}
```
输出结果为：

```
src\base\file
src\base\file\fileCreateOrDel.java
src\base\file\fileErgodic.java
src\base\file\fileJudge.java
src\base\file\fileRecursion.java
src\base\file\fileStruct.java
src\base\file\fileTest
src\base\file\fileTest\bbb
src\base\file\fileTest\bbb\ccc
src\base\file\fileTest\new
src\base\file\fileTest\new.txt
src\base\file\fileTest\new2
src\base\file\getMethod.java
src\base\file\staticVar.java
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/441f834c51314a578d50bcb6f8c23567.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)