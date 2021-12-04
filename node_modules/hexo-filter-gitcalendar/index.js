'use strict'
// 全局声明插件代号
const pluginname = 'gitcalendar'
// 全局声明依赖
const pug = require('pug')
const path = require('path')
const urlFor = require('hexo-util').url_for.bind(hexo)
const util = require('hexo-util')

hexo.extend.filter.register('after_generate', function () {
  // 首先获取整体的配置项名称
  const config = hexo.config.gitcalendar ? hexo.config.gitcalendar : hexo.theme.config.gitcalendar
  // 如果配置开启
  if (!(config && config.enable)) return
  // 集体声明配置项
    const data = {
      theme: hexo.config.theme,
      enable_page: config.enable_page ? config.enable_page : "/",
      user: config.user,
      layout_type: config.layout.type,
      layout_name: config.layout.name,
      layout_index: config.layout.index ? config.layout.index : 0,
      pc_minheight: config.minheight.pc ? config.minheight.pc : "280px",
      mobile_minheight: config.minheight.mobile ? config.minheight.mobile : "0px",
      color: config.color ? config.color : "['#e4dfd7', '#f9f4dc', '#f7e8aa', '#f7e8aa', '#f8df72', '#fcd217', '#fcc515', '#f28e16', '#fb8b05', '#d85916', '#f43e06']",
      apiurl: config.apiurl ? config.apiurl + "/api" : 'https://gitcalendar.akilar.top/api',
      container: config.container
    }
  // 渲染页面
  const temple_html_text = pug.renderFile(path.join(__dirname, './lib/html.pug'),data);

  //cdn资源声明
    //样式资源
  const css_text = `<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/hexo-filter-gitcalendar/lib/gitcalendar.css">`
    //脚本资源
  const js_text = `<script data-pjax src="https://cdn.jsdelivr.net/npm/hexo-filter-gitcalendar/lib/gitcalendar.js"></script>`

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
                          parent_div_git.insertAdjacentHTML("afterbegin",item_html)
                          console.log('已挂载${pluginname}')
                          }

                        if( ${get_layout} && (location.pathname ==='${data.enable_page}'|| '${data.enable_page}' ==='all')){
                            ${pluginname}_injector_config()
                            GitCalendarInit("${data.apiurl}?${data.user}",${data.color},'${data.user}')
                        }
                      </script>`
  // 注入样式资源
  hexo.extend.injector.register('head_end', css_text, "default");
  // 注入脚本资源
  hexo.extend.injector.register('body_end', js_text, "default");
  // 注入用户脚本
  // 此处利用挂载容器实现了二级注入
  hexo.extend.injector.register('body_end', user_info_js, "default");

},
hexo.extend.helper.register('priority', function(){
  // 过滤器优先级，priority 值越低，过滤器会越早执行，默认priority是10
  const pre_priority = hexo.config.electric_clock.priority ?  hexo.config.electric_clock.priority : hexo.theme.config.electric_clock.priority
  const priority = pre_priority ? pre_priority : 10
  return priority
})
)
