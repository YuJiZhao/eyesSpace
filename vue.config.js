const { defineConfig } = require('@vue/cli-service')

const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  chainWebpack: config => {
    config.resolve.alias.set("@", resolve("src"))
  },
  configureWebpack: {
    name: '耶瞳空间管理系统'
  },
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:9300",
        pathRewrite: {'^/api' : ''}
      }
    }
  }
})
