'use strict'
// 全局声明插件代号
const pluginname = 'butterfly_footer_beautify'
// 全局声明依赖
const pug = require('pug')
const path = require('path')
const urlFor = require('hexo-util').url_for.bind(hexo)
const util = require('hexo-util')

hexo.extend.filter.register('after_generate', function (locals) {
  // 首先获取整体的配置项名称
  const config = hexo.config.footer_beautify ? hexo.config.footer_beautify : hexo.theme.config.footer_beautify
  // 如果计时器或徽标任意一个配置开启
  if (!(config && (config.enable.bdage || config.enable.timer))) return
  // 集体声明配置项
    // 首先获取所有的徽标参数列表，默认是博客框架+主题框架的徽标
    const bdageitem = config.bdageitem ? config.bdageitem : [{"link": "https://hexo.io/","shields": "https://img.shields.io/badge/Frame-Hexo-blue?style=flat&logo=hexo","message": "博客框架为Hexo"},{"link": "https://butterfly.js.org/","shields": "https://img.shields.io/badge/Theme-Butterfly-6513df?style=flat&logo=bitdefender","message": "主题使用Butterfly"}]
    //然后获取预备分割的每行个数。
    const swiperpara = config.swiperpara
    // 定义一个swiperitem数组用来存放分割后的徽标参数
    var swiperitem = [];
    // 当swiperitem存在的时候，默认为开启轮播功能，对bdageitem进行分割
    if (swiperpara){
      for(var i=0;i<bdageitem.length;i+=swiperpara){
        /*按照每行固定个数进行分割，
        slice方法不会改变原数组，并且返回一个新的数组
        而且当slice(start,end)第二个end参数值大于数组length的时候
        会按照数组length算，取的数组结束的所有元素*/
          swiperitem.push(bdageitem.slice(i,i+swiperpara));
      }
    }
    // console.log(swiperitem)
    const data = {
      timer_enable: config.enable.timer,
      bdage_enable: config.enable.bdage,
      enable_page: config.enable_page ? config.enable_page : "all",
      exclude: config.exclude,
      layout_type: config.layout.type,
      layout_name: config.layout.name,
      layout_index: config.layout.index ? config.layout.index : 0,
      runtime_js: config.runtime_js ? urlFor(config.runtime_js) : "https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify@1.0.0/lib/runtime.min.js",
      runtime_css: config.runtime_css ? urlFor(config.runtime_css) : "https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify@1.0.0/lib/runtime.min.css",
      swiper_css: config.swiper_css ? urlFor(config.swiper_css) : "https://cdn.jsdelivr.net/npm/hexo-butterfly-swiper/lib/swiper.min.css",
      swiper_js: config.swiper_js ? urlFor(config.swiper_js) : "https://cdn.jsdelivr.net/npm/hexo-butterfly-swiper/lib/swiper.min.js",
      swiperbdage_init_js: config.swiperbdage_init_js ? urlFor(config.swiperbdage_init_js) : "https://cdn.jsdelivr.net/npm/hexo-butterfly-footer-beautify/lib/swiperbdage_init.min.js",
      swiperpara: swiperpara,
      bdageitem: bdageitem,
      swiperitem: swiperitem
    }
  // 渲染页面
  const temple_html_text = config.temple_html ? config.temple_html : pug.renderFile(path.join(__dirname, './lib/html.pug'),data)
  //cdn资源声明
    //样式资源
  if (swiperpara){
    var css_text = `<link rel="stylesheet" href="${data.runtime_css}" media="defer" onload="this.media='all'"><link rel="stylesheet" href="${data.swiper_css}" media="defer" onload="this.media='all'">`
  }else{
    var css_text = `<link rel="stylesheet" href="${data.runtime_css}" media="defer" onload="this.media='all'">`
  }
    //脚本资源

  const runtime_js_text = `<script async src="${data.runtime_js}"></script>`
  const swiperbdage_js_text = `<script defer src="${data.swiper_js}"></script><script defer data-pjax src="${data.swiperbdage_init_js}"></script>`

  //注入容器声明
  var get_layout
  //若指定为class类型的容器
  if (data.layout_type === 'class') {
    //则根据class类名及序列获取容器
    get_layout = `document.getElementsByClassName('${data.layout_name}')[${data.layout_index}]`
  }
  // 若指定为id类型的容器
  else if (data.layout_type === 'id') {
    // 直接根据id获取容器
    get_layout = `document.getElementById('${data.layout_name}')`
  }
  // 若未指定容器类型，默认使用id查询
  else {
    get_layout = `document.getElementById('${data.layout_name}')`
  }

  //挂载容器脚本
  var user_info_js = `<script data-pjax>
  function ${pluginname}_injector_config(){
    var parent_div_git = ${get_layout};
    var item_html = '${temple_html_text}';
    console.log('已挂载${pluginname}')
    parent_div_git.insertAdjacentHTML("beforeend",item_html)
    }
  var elist = '${data.exclude}'.split(',');
  var cpage = location.pathname;
  var epage = '${data.enable_page}';
  var flag = 0;

  for (var i=0;i<elist.length;i++){
    if (cpage.includes(elist[i])){
      flag++;
    }
  }

  if ((epage ==='all')&&(flag == 0)){
    ${pluginname}_injector_config();
  }
  else if (epage === cpage){
    ${pluginname}_injector_config();
  }
  </script>`
  // 注入用户脚本
  // 此处利用挂载容器实现了二级注入
  hexo.extend.injector.register('body_end', user_info_js, "default");
  // 注入脚本资源
  if (data.timer_enable){
    hexo.extend.injector.register('body_end', runtime_js_text, "default");
  }
  if (data.swiperpara){
    hexo.extend.injector.register('body_end', swiperbdage_js_text, "default");
  }
  // 注入样式资源
  hexo.extend.injector.register('head_end', css_text, "default");
},
hexo.extend.helper.register('priority', function(){
  // 过滤器优先级，priority 值越低，过滤器会越早执行，默认priority是10
  const pre_priority = hexo.config.footer_beautify.priority ?  hexo.config.footer_beautify.priority : hexo.theme.config.footer_beautify.priority
  const priority = pre_priority ? pre_priority : 10
  return priority
})
)
