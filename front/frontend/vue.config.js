const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
  
  transpileDependencies: true,
  
  //port 설정
  devServer :{
    proxy:{   
      '/api':{
      target:'http://localhost:8088',
      changeOrigin: true
      }

    }                  
  },

  //elements plus 설정
  lintOnSave:false,

  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
  }

  //font 설정
  
  
})
