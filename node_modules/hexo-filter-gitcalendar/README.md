# hexo-filter-gitcalendar

给`hexo`添加 [首页git提交日历](https://akilar.top/posts/1f9c68c9/)

# 致谢
感谢[@冰老师](https://zfe.space/)的gitcalendar思路。本插件基于冰老师的hexo-githubcalendar进行二次开发。优化了对主题的适应性。添加了各个冗长配置项的默认项。尽可能简化了配置过程。

# 安装

1. 安装插件,在博客根目录`[Blogroot]`下打开终端，运行以下指令：
  ```bash
  npm install hexo-filter-gitcalendar --save
  ```

2. 添加配置信息，以下为写法示例
  在站点配置文件`_config.yml`或者主题配置文件如`_config.butterfly.yml`中添加

  ```yaml
  # hexo-filter-gitcalendar
  # see https://akilar.top/posts/1f9c68c9/
  gitcalendar:
    enable: true # 开关
    priority: 5 #过滤器优先权
    enable_page: / # 应用页面
    # butterfly挂载容器
    layout: # 挂载容器类型
      type: id
      name: recent-posts
      index: 0
    # volantis挂载容器
    # layout:
    #   type: class
    #   name: l_main
    #   index: 0
    # matery挂载容器
    # layout:
    #   type: id
    #   name: indexCard
    #   index: 0
    # mengd挂载容器
    # layout:
    #   type: class
    #   name: content
    #   index: 0
    user: Akilarlxh #git用户名
    apiurl: 'https://gitcalendar.akilar.top'
    minheight:
      pc: 280px #桌面端最小高度
      mibile: 0px #移动端最小高度
    color: "['#e4dfd7', '#f9f4dc', '#f7e8aa', '#f7e8aa', '#f8df72', '#fcd217', '#fcc515', '#f28e16', '#fb8b05', '#d85916', '#f43e06']" #橘黄色调
    # color: "['#ebedf0', '#fdcdec', '#fc9bd9', '#fa6ac5', '#f838b2', '#f5089f', '#c4067e', '#92055e', '#540336', '#48022f', '#30021f']" #浅紫色调
    # color: "['#ebedf0', '#f0fff4', '#dcffe4', '#bef5cb', '#85e89d', '#34d058', '#28a745', '#22863a', '#176f2c', '#165c26', '#144620']" #翠绿色调
    # color: "['#ebedf0', '#f1f8ff', '#dbedff', '#c8e1ff', '#79b8ff', '#2188ff', '#0366d6', '#005cc5', '#044289', '#032f62', '#05264c']" #天青色调
    container: .recent-post-item(style='width:100%;height:auto;padding:10px;') #父元素容器，需要使用pug语法
  ```
3. 参数释义

  |参数|备选值/类型|释义|
  |:--|:--|:--|
  |priority|number|【可选】过滤器优先级，数值越小，执行越早，默认为10，选填|
  |enable|true/false|【必选】控制开关|
  |enable_page|path/all|【可选】填写想要应用的页面的相对路径（即路由地址）,如根目录就填'/',分类页面就填'/categories/'。若要应用于所有页面，就填'all'，默认为'/'|
  |layout.type|id/class|【可选】挂载容器类型，填写id或class，不填则默认为id|
  |layout.name|text|【必选】挂载容器名称|
  |layout.index|0和正整数|【可选】前提是layout.type为class，因为同一页面可能有多个class，此项用来确认究竟排在第几个顺位|
  |user|text|【必选】git用户名|
  |apiurl|url|【可选】默认使用提供文档提供的api，但还是建议自建api，参考教程：[自建API部署](https://akilar.top/posts/1f9c68c9/#自建API部署)|
  |minheight.pc|280px|【可选】桌面端最小高度，默认为280px|
  |minheight.mobile|0px|【可选】移动端最小高度，默认为0px|
  |color|list|【可选】一个包含11个色值的数组，文档给出了四款预设值|
  |container|pug|【可选】预留的父元素容器，用以适配多主题，需要用pug语法填写，目前已适配butterfly，volantis，matery，mengd主题，这四个主题，插件会自自动识别_config.yml内填写的theme配置项。其余主题需要自己填写父元素容器。|
# 截图
  ![](https://cdn.jsdelivr.net/npm/hexo-filter-gitcalendar/lib/gitcalendar.png)
