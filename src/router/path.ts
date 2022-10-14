import { RouteRecordRaw } from 'vue-router';

const publicPath = {
    home: "/",
    login: "/login",
    blog: "/blog",
    shuoshuo: "/shuoshuo",
    music: "/music",
    video: "/video",
    about: "/about",
    errorPath: {
        route: "/error/errorRoute",
        context: "/error/errorContext"
    }
}

const common: Array<RouteRecordRaw> = [
    {
        path: publicPath.login,
        name: "login",
        component: () => import("@/views/common/Login.vue"),
    },
    {
        path: "/error/:type",
        name: "errorPage",
        component: () => import("@/views/common/ErrorPage.vue"),
    },
    {
        path: "/:catchAll(.*)",
        redirect: publicPath.errorPath.route
    }
];

const content: Array<RouteRecordRaw> = [
    ...common,
    {
        path: "",
        name: "home",
        component: () => import("@/views/content/Home.vue"),
    },
    {
        path: publicPath.blog,
        children: [
            {
                path: "",
                name: "blog",
                component: () => import("@/views/content/Blog.vue"),
            },
            {
                path: "details/:id",
                name: "blogDetails",
                component: () => import("@/views/content/blog/Details.vue"),
            },
        ]
    },
    {
        path: publicPath.shuoshuo,
        children: [
            {
                path: "",
                name: "shuoshuo",
                component: () => import("@/views/content/Shuoshuo.vue"),
            },
            {
                path: "details",
                name: "shuoshuoDetails",
                component: () => import("@/views/content/shuoshuo/Details.vue"),
            }
        ]
    },
    {
        path: publicPath.music,
        name: "music",
        component: () => import("@/views/content/Music.vue"),
    },
    {
        path: publicPath.video,
        name: "video",
        component: () => import("@/views/content/Video.vue"),
    },
    {
        path: publicPath.about,
        name: "about",
        component: () => import("@/views/content/About.vue"),
    }
];

export { publicPath, content };