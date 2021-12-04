'use strict'

const pug = require('pug')
const path = require('path')
const urlFor = require('hexo-util').url_for.bind(hexo)
const util = require('hexo-util')
hexo.extend.generator.register('comments', function (locals) {
  const config = hexo.config.envelope_comment || hexo.theme.config.envelope_comment

  if (!(config && config.enable)) return

  const data = {
    author: hexo.config.author,
    cover: config.cover ? urlFor(config.cover) : "https://ae01.alicdn.com/kf/U5bb04af32be544c4b41206d9a42fcacfd.jpg",
    message: config.message ?  config.message : ["有什么想问的？","有什么想说的？","有什么想吐槽的？","哪怕是有什么想吃的，都可以告诉我哦~"],
    bottom: config.bottom ? config.bottom : "自动书记人偶竭诚为您服务",
    height: config.height ? config.height : "1050px"
  }
  const content = pug.renderFile(path.join(__dirname, './lib/html.pug'), data)

  const pathPre = config.path || 'comments'

  let pageDate = {
    content: content
  }

  if (config.front_matter) {
    pageDate = Object.assign(pageDate, config.front_matter)
  }

  return {
    path: pathPre + '/index.html',
    data: pageDate,
    layout: ['page', 'post']
  }
})
