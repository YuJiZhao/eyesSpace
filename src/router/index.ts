import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

import {  } from '../components'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: () => import("../views/Home.vue"),
        children: []
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router