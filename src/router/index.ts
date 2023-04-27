import { createRouter, RouteRecordRaw, createWebHistory } from 'vue-router';
import { context } from '@/modules/context';
import process from "@/modules/process";
import { errorPath } from '@/config/site';
import { content } from './path';
import { siteConfig } from "@/config/program";

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
    // 清除工具按钮中关闭侧栏的localStorage
    localStorage.removeItem(siteConfig.sideBarShowStorage);
    next();
});

router.afterEach((to, from) => {
});

export default router;