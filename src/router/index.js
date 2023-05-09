import { createRouter, createWebHistory } from 'vue-router';

const systemAdministor = [
    {
        path: '/',
        name: '主控台',
        component: () => import('@/views/dashboard')
    },
    {
        path: '/content',
        name: '内容',
        children: [
            {
                path: '/content/blog',
                name: '博客',
                component: () => import('@/views/content/blog')
            },
            {
                path: '/content/shuoshuo',
                name: '说说',
                component: () => import('@/views/content/shuoshuo')
            },
            {
                path: '/content/exercises',
                name: '习题',
                component: () => import('@/views/content/exercises')
            }
        ]
    },
    {
        path: '/music',
        name: '音乐管理',
        component: () => import('@/views/music')
    },
    {
        path: '/video',
        name: '视频管理',
        component: () => import('@/views/video')
    },
    {
        path: '/user',
        name: '用户管理',
        component: () => import('@/views/user')
    },
    {
        path: '/analysis',
        name: '数据分析',
        component: () => import('@/views/analysis')
    },
    {
        path: '/log',
        name: '系统日志',
        component: () => import('@/views/log')
    }
];

const routes = [
    {
        path: '/',
        name: 'index',
        component: () => import('@/Index.vue'),
        children: [
            ...systemAdministor,
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router;