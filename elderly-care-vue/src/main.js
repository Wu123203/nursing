import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import { Route404 } from './router';
import store from './store'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
//引入echarts组件
import * as echarts from 'echarts'
import request from '@/utils/request'
import {hasPermission} from '@/utils/permission'
import mitt from 'mitt';
// 跨浏览器兼容：CSS样式重置
import 'normalize.css/normalize.css'
import 'font-awesome/css/font-awesome.min.css'

const app = createApp(App);
//白名单：不作权限校验
const whiteList = ['/login']

//使用钩子函数对路由进行权限跳转
router.beforeEach(async(to, from, next) => {
    document.title = `${to.meta.title} | 智能护工平台`;
    var userJson = localStorage.getItem("user");
    if(userJson){
        let user = JSON.parse(userJson);
        let role;
        if(user.role == 0){
            role = "admin"
        }else if(user.role == 1){
            role = "employee"
        }else{
            role = "user"
        }
        const routes = store.state.routes;
        if (routes &&  routes.length > 0) {
            if(!hasPermission([role],to)){
              next('/404')
            }else{
              next()
            }
          } else {
              if(!hasPermission([role],to)){
                next('/404')
              }else{
                const accessRoutes = await store.dispatch('generateRoutes', role)
                accessRoutes.forEach((route) => {
                    router.addRoute(route);
                })                
                //动态引入404路由
                router.addRoute(Route404);
                //确保addRoutes操作完成
                next({ ...to, replace: true })
              }   
          }
    }else{
        if(!to.meta.roles){
            next()
        }else{
            next('/');
        }
    }
});

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 事件总线
app.config.globalProperties.$bus = mitt()
//全局引入echarts
app.config.globalProperties.$echarts = echarts
//全局引入axios
app.config.globalProperties.$http = request
app.use(store).use(router).use(ElementPlus,{locale: zhCn,size:"default"}).mount('#app')