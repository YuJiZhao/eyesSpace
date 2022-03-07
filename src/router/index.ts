import { createRouter, createWebHashHistory, RouteRecordRaw, onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "app",
        component: () => import("@/App.vue"),
        children: [
            {
                path: "/Home",
                name: "Home",
                component: () => import("@/view/Home.vue"),
                children: []
            },
            {
                path: "/Blog",
                name: "Blog",
                component: () => import("@/view/Blog.vue"),
                children: []
            },
            // TODO: 完善非法路由跳转
            // {
            //     path: '/*',
            //     component: () => import("@/view/Error.vue"),
            //     redirect: '/Error',
            // },
        ]
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