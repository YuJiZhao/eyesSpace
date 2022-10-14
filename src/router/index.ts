import { createRouter, RouteRecordRaw, createWebHistory } from 'vue-router';
import { context } from '@/modules/context';
import process from "@/modules/process";
import Window from '@/modules/window';
import { errorPath } from '@/config/site';
import { content } from './path';

const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "index",
        component: () => import("@/views/Index.vue"),
        children: content
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    // 禁止在没获取配置信息时浏览网站
    if(from.fullPath == errorPath.context && !Object.getOwnPropertyNames(context.data).length) {
        process.tipShow.warn("暂时无法跳转");
        return;
    };
    next();
});

router.afterEach((to, from) => {
    // 调整首部导航栏伸缩
    if(process.headerStatus.value) {
        process.headerCheckSwitch(Window.height.value);
    }
    // 调整底栏模式
    if(process.footerStatus.value) {
        process.footerPositionSwitch(Window.height.value);
    }
});

export default router;