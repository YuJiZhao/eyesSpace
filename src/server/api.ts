import { get, post, put, del } from "./ajax";
import { 
    ApiObject, 
    SiteInterface, 
    UserApiInterface, 
    MusicInterface, 
    VideoInterface, 
    ShuoshuoInterface, 
    BlogInterface,
    VersionInterface
} from "@/d.ts/server/api";
import { UrlReqType } from "@/constant";

const site: SiteInterface = {
    getContext: async () => {
        return await get("/context/getContext");
    },
    getHomeList: async (req) => {
        return await get("/home/getHomeList", req);
    },
    getSiteData: async () => {
        return await get("/home/getSiteData");
    },
    addSpaceVisit: async (req) => {
        return await post("/track/visitAdd", req);
    },
    getAboutContent: async () => {
        return await get("/context/getAboutContext");
    },
    doAboutComment: async (req) => {
        return await post("/message/doMessage", req);
    },
    getAboutCommentList: async (req) => {
        return await get("/message/getMessageList", req);
    },
    delAboutComment: async (req) => {
        return await del("/message/delMessage", req, UrlReqType.path);
    }
}

const blog: BlogInterface = {
    getBlogListInfo: async () => {
        return await get("/blog/getBlogListInfo");
    },
    getBlogList: async (req) => {
        return await get("/blog/getBlogList", req);
    },
    getBlogInfo: async (req) => {
        return await get("/blog/getBlogInfo", req, UrlReqType.path);
    },
    getBlogCategory: async () => {
        return await get("/blog/getBlogCategory");
    },
    getBlogLabel: async () => {
        return await get("/blog/getBlogLabel");
    },
    doBlogLike: async (req) => {
        return await get("/blog/doBlogLike", req);
    },
    doBlogCollect: async (req) => {
        return await get("/blog/doBlogCollect", req);
    },
    doBlogComment: async (req) => {
        return await post("/blog/doBlogComment", req);
    },
    getBlogCommentList: async (req) => {
        return await get("/blog/getBlogCommentList", req);
    },
    delBlogComment: async (req) => {
        return await del("/blog/delBlogComment", req, UrlReqType.path);
    }
}

const shuoshuo: ShuoshuoInterface = {
    getShuoshuoList: async (req) => {
        return await get("/shuo/getShuoList", req);
    },
    getShuoshuoListInfo: async () => {
        return await get("/shuo/getShuoListInfo");
    },
    getShuoshuoSingleInfo: async (req) => {
        return await get("/shuo/getSingleShuo", req);
    },
    doShuoshuoComment: async (req) => {
        return await post("/shuo/doShuoComment", req);
    },
    getShuoshuoCommentList: async (req) => {
        return await get("/shuo/getShuoCommentList", req);
    },
    delShuoshuoComment: async (req) => {
        return await del("/shuo/delShuoComment", req, UrlReqType.path);
    }
}

const video: VideoInterface = {
    getVideoInfo: async () => {
        return await get("/video/getVideoInfoByUser");
    },
    doVideoUserLike: async (req) => {
        return await get("/video/doUserLike", req);
    }
}

const music: MusicInterface = {
    getMusicInfo: async () => {
        return await get("/music/getMusicInfoByUser");
    },
    doMusicUserLike: async (req) => {
        return await get("/music/doUserLike", req);
    }
}

const userApi: UserApiInterface = {
    getUserInfo: async () => {
        return await get("/user/getUserInfo");
    },
    updateUserInfo: async (req) => {
        return await put("/user/updateUserInfo", req);
    }
}

const versionApi: VersionInterface = {
    getVersionInfo: async () => {
        return await get("/version/getVersionInfo");
    },
    getVersionList: async (req) => {
        return await get("/version/getVersionList", req);
    }
}

const api: ApiObject = {
    ...site,
    ...blog,
    ...shuoshuo,
    ...video,
    ...music,
    ...userApi,
    ...versionApi
}

export default api;