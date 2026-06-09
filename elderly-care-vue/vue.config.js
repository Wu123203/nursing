module.exports = {

  //关闭语法检查
  lintOnSave: false,
  //开启代理服务器
  devServer: {
    port: 9527,   //配置本地默认端口：支撑脚手架和代理服务器自身端口
    proxy: {
      '/api': {   //请求前缀，axios请求前缀为api的走代理
        target: 'http://localhost:8080',  //代理的目标服务器
        pathRewrite:{'^/api':''}, // 请求路径重写，把以api开头的这部分地址替换为空字符串
        changeOrigin: true //用于控制请求头中的host值
      }
    }
  }
}