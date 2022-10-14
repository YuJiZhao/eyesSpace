import { get, post, put, del } from "./ajax";
import { 
    ApiObject, 
    CommonInterface, 
    SiteInterface, 
    UserApiInterface, 
    MusicInterface, 
    VideoInterface, 
    ShuoshuoInterface, 
    BlogInterface 
} from "@/d.ts/server/api";
import { UrlReqType } from "@/constant";

const common: CommonInterface = {
    getImgCode: async () => {
        return await get("/auth/login/getImgVeriCode");
    },
    getEmailCode: async (req) => {
        return await post("/auth/login/sendEmail", req);
    },
    login: async (req) => {
        return await post("/auth/login/doLogin", req);
    },
}

const site: SiteInterface = {
    getContext: async () => {
        return await get("/site/context/getContext");
    },
    getHomeList: async (req) => {
        return await get("/site/home/getHomeList", req);
    },
    addSpaceVisit: async (req) => {
        return await post("/backstage/trace/addSpaceVisit", req);
    },
    doAboutComment: async (req) => {
        return await post("/site/about/doAboutComment", req);
    },
    getAboutCommentList: async (req) => {
        return await get("/site/about/getAboutCommentList", req);
    },
    delAboutComment: async (req) => {
        return await del("/site/about/delAboutComment", req, UrlReqType.path);
    }
}

const blog: BlogInterface = {
    getBlogListInfo: async () => {
        return await get("/article/blog/getBlogListInfo");
    },
    getBlogList: async (req) => {
        return await get("/article/blog/getBlogList", req);
    },
    getBlogInfo: async (req) => {
        return await get("/article/blog/getBlogInfo", req, UrlReqType.path);
    },
    getBlogCategory: async () => {
        return await get("/article/blog/getBlogCategory");
    },
    getBlogLabel: async () => {
        return await get("/article/blog/getBlogLabel");
    },
    doBlogLike: async (req) => {
        return await get("/article/blog/doBlogLike", req);
    },
    doBlogCollect: async (req) => {
        return await get("/article/blog/doBlogCollect", req);
    },
    doBlogComment: async (req) => {
        return await post("/article/blog/doBlogComment", req);
    },
    getBlogCommentList: async (req) => {
        return await get("/article/blog/getBlogCommentList", req);
    },
    delBlogComment: async (req) => {
        return await del("/article/blog/delBlogComment", req, UrlReqType.path);
    }
}

const shuoshuo: ShuoshuoInterface = {
    getShuoshuoList: async (req) => {
        return await get("/article/shuoshuo/getShuoshuoList", req);
    },
    getShuoshuoListInfo: async () => {
        return await get("/article/shuoshuo/getShuoshuoListInfo");
    },
    getShuoshuoSingleInfo: async (req) => {
        return await get("/article/shuoshuo/getSingleShuoshuoByString", req);
    },
    doShuoshuoComment: async (req) => {
        return await post("/article/shuoshuo/doShuoComment", req);
    },
    getShuoshuoCommentList: async (req) => {
        return await get("/article/shuoshuo/getShuoCommentList", req);
    },
    delShuoshuoComment: async (req) => {
        return await del("/article/shuoshuo/delShuoComment", req, UrlReqType.path);
    }
}

const video: VideoInterface = {
    getVideoInfo: async () => {
        return await get("/entertain/video/user/getVideoInfo");
    },
    doVideoUserLike: async (req) => {
        return await get("/entertain/video/user/doUserLike", req);
    }
}

const music: MusicInterface = {
    getMusicInfo: async () => {
        return await get("/entertain/music/user/getMusicInfo");
    },
    doMusicUserLike: async (req) => {
        return await get("/entertain/music/user/doUserLike", req);
    }
}

const userApi: UserApiInterface = {
    getUserInfo: async () => {
        return await get("/site/user/info/getUserInfo");
    },
    updateUserInfo: async (req) => {
        return await put("/site/user/info/updateUserInfo", req);
    }
}

const api: ApiObject = {
    ...common,
    ...site,
    ...blog,
    ...shuoshuo,
    ...video,
    ...music,
    ...userApi
}

export default api;