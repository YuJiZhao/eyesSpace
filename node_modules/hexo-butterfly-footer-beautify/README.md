# hexo-butterfly-footer-beautify

给`hexo-theme-butterfly`添加底栏美化,实际包括两个组件，
页脚计时器：[Native JS Timer](https://akilar.top/posts/b941af/)
页脚徽标：[Add Github Badge](https://akilar.top/posts/e87ad7f8/)

# 安装

1. 安装插件,在博客根目录`[Blogroot]`下打开终端，运行以下指令：
  ```bash
  npm install hexo-butterfly-footer-beautify --save
  ```

2. 添加配置信息，以下为写法示例
  在站点配置文件`_config.yml`或者主题配置文件`_config.butterfly.yml`中添加

  ```yaml
  # footer_beautify
  # 页脚计时器：[Native JS Timer](https://akilar.top/posts/b941af/)
  # 页脚徽标：[Add Github Badge](https://akilar.top/posts/e87ad7f8/)
  footer_beautify:
    enable:
      timer: true # 计时器开关
      bdage: true # 徽标开关
    priority: 5 #过滤器优先权
    enable_page: all # 应用页面
    exclude: #屏蔽页面
      # - /posts/
      # - /about/
    layout: # 挂载容器类型
      type: id
      name: footer-wrap
      index: 0
    # 计时器部分配置项
    runtime_js: https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify@1.0.0/lib/runtime.js
    runtime_css: https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify@1.0.0/lib/runtime.css
    # 徽标部分配置项
    swiperpara: 3 #若非0，则开启轮播功能，每行徽标个数
    bdageitem:
      - link: https://hexo.io/ #徽标指向网站链接
        shields: https://img.shields.io/badge/Frame-Hexo-blue?style=flat&logo=hexo #徽标API
        message: 博客框架为Hexo_v5.4.0 #徽标提示语
      - link: https://butterfly.js.org/
        shields: https://img.shields.io/badge/Theme-Butterfly-6513df?style=flat&logo=bitdefender
        message: 主题版本Butterfly_v3.8.2
      - link: https://www.jsdelivr.com/
        shields: https://img.shields.io/badge/CDN-jsDelivr-orange?style=flat&logo=jsDelivr
        message: 本站使用JsDelivr为静态资源提供CDN加速
      - link: https://vercel.com/
        shields: https://img.shields.io/badge/Hosted-Vercel-brightgreen?style=flat&logo=Vercel
        message: 本站采用双线部署，默认线路托管于Vercel
      - link: https://vercel.com/
        shields: https://img.shields.io/badge/Hosted-Coding-0cedbe?style=flat&logo=Codio
        message: 本站采用双线部署，联通线路托管于Coding
      - link: https://github.com/
        shields: https://img.shields.io/badge/Source-Github-d021d6?style=flat&logo=GitHub
        message: 本站项目由Gtihub托管
      - link: http://creativecommons.org/licenses/by-nc-sa/4.0/
        shields: https://img.shields.io/badge/Copyright-BY--NC--SA%204.0-d42328?style=flat&logo=Claris
        message: 本站采用知识共享署名-非商业性使用-相同方式共享4.0国际许可协议进行许可
    swiper_css: https://cdn.jsdelivr.net/npm/hexo-butterfly-swiper/lib/swiper.min.css
    swiper_js: https://cdn.jsdelivr.net/npm/hexo-butterfly-swiper/lib/swiper.min.js
    swiperbdage_init_js: https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify/lib/swiperbdage_init.min.js
  ```
3. 参数释义

  |参数|备选值/类型|释义|
  |:--|:--|:--|
  |priority|number|【可选】过滤器优先级，数值越小，执行越早，默认为10，选填|
  |enable.timer|true/false|【必选】计时器控制开关|
  |enable.bdage|true/false|【必选】徽标控制开关|
  |enable_page|path|【可选】填写想要应用的页面,如根目录就填'/',分类页面就填'/categories/'。若要应用于所有页面，就填`all`，默认为`all`|
  |exclude|path|【可选】填写想要屏蔽的页面，可以多个。仅当enable_page为'all'时生效。写法见示例。原理是将屏蔽项的内容逐个放到当前路径去匹配，若当前路径包含任一屏蔽项，则不会挂载。|
  |layout.type|id/class|【可选】挂载容器类型，填写id或class，不填则默认为id|
  |layout.name|text|【必选】挂载容器名称|
  |layout.index|0和正整数|【可选】前提是layout.type为class，因为同一页面可能有多个class，此项用来确认究竟排在第几个顺位|
  |runtime_js|url|【必选】页脚计时器脚本，可以下载上文填写示例的链接，参照注释和[教程：Native JS Timer](https://akilar.top/posts/b941af/)自行修改。|
  |runtime_css|url|【可选】自定义样式，预留开发者接口，可自行下载。|
  |swiperpara|number|【可选】若非零，则开启轮播功能，此项表示每行最多容纳徽标个数，用来应对徽标过多显得页脚拥挤的问题|
  |bdageitem.link|url|【可选】页脚徽标指向的网站链接|
  |bdageitem.shields|url|【必选】页脚徽标对应的API，API具体写法示例参照[教程Add Github Badge](https://akilar.top/posts/e87ad7f8/)|
  |bdageitem.message|text|【可选】页脚徽标悬停时显示的信息|
  |swiper_css|url|【可选】swiper的依赖|
  |swiper_js|url|【可选】swiper的依赖|
  |swiperbdage_init_js|url|【可选】swiper初始化方法|
# 截图
![页脚计时器效果](https://cdn.jsdelivr.net/npm/akilar-candyassets/image/Native-JS-Timer-50daecfe.png)
![页脚徽标效果](https://cdn.jsdelivr.net/npm/akilar-candyassets/image/1UWbK9rphJtTHsD.png)


# 目前存在的问题：
1. 和hexo-butterfly-swiper共用依赖，但是无法解决swiper.min.js在pjax重载后的引入顺序问题。目前只能是重复引入。后续可能把这几个整合到一起。
2. 如果之前用hexo-butterfly-wowjs给徽标添加过动画，需要关闭动画。wowjs的动画效果和swiper的轮播效果不兼容。
