import { createRouter, createWebHashHistory, RouteRecordRaw, onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: () => import("../views/Home.vue"),
        children: []
    }
]

// TODO：配置路由跳转动画加载
// onBeforeRouteUpdate((to,from)=>{
//     console.log(to);
// })
// onBeforeRouteLeave((to,from)=>{
//     alert('我离开啦')
// })

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router