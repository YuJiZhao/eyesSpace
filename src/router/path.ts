import { RouteRecordRaw } from 'vue-router';

const publicPath = {
    home: "/",
    blog: "/blog",
    shuoshuo: "/shuoshuo",
    music: "/music",
    video: "/video",
    anime: "/anime",
    message: "/message",
    friend: "/friend",
    version: "version",
    about: "/about",
    errorPath: {
        route: "/error/errorRoute",
        context: "/error/errorContext"
    }
}

const common: Array<RouteRecordRaw> = [
    {
        path: "/auth",
        name: "auth",
        component: () => import("@/views/common/Auth.vue"),
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
        path: publicPath.anime,
        children: [
            {
                path: "",
                name: "anime",
                component: () => import("@/views/content/Anime.vue"),
            },
            {
                path: "details/:id",
                name: "animeDetails",
                component: () => import("@/views/content/anime/Details.vue"),
            }
        ]
    },
    {
        path: publicPath.message,
        name: "message",
        component: () => import("@/views/content/Message.vue"),
    },
    {
        path: publicPath.friend,
        name: "friend",
        component: () => import("@/views/content/Friend.vue"),
    },
    {
        path: publicPath.version,
        name: "version",
        component: () => import("@/views/content/Version.vue"),
    },
    {
        path: publicPath.about,
        name: "about",
        component: () => import("@/views/content/About.vue"),
    }
];

export { publicPath, content };