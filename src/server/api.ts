import { get, post, put, del } from "./ajax";
import { 
    ApiObject, 
    SiteInterface, 
    UserInterface, 
    ShuoshuoInterface, 
    BlogInterface,
    MusicInterface, 
    VideoInterface,
    JokeInterface,
    AnimeInterface,
    FriendInterface,
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

const user: UserInterface = {
    getUserInfo: async () => {
        return await get("/user/getUserInfo");
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

const music: MusicInterface = {
    getMusicInfo: async () => {
        return await get("/music/getMusicInfoByUser");
    },
    doMusicUserLike: async (req) => {
        return await get("/music/doUserLike", req);
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

const joke: JokeInterface = {
    getJokeNotice: async () => {
        return await get("/joke/getJokeNotice");
    },
    getJokeList: async (req) => {
        return await get("/joke/getJokeList", req);
    },
    jokeVisit: async () => {
        return await get("/track/jokeVisit");
    },
}

const anime: AnimeInterface = {
    getAnimeNotice: async () => {
        return await get("/anime/getAnimeNotice");
    },
    getAnimeListInfo: async () => {
        return await get("/anime/getAnimeListInfo");
    },
    getAnimeList: async (req) => {
        return await get("/anime/getAnimeList", req);
    },
    getAnimeInfo: async (req) => {
        return await get("/anime/getAnimeInfo", req, UrlReqType.path);
    },
    doAnimeComment: async (req) => {
        return await post("/anime/doAnimeComment", req);
    },
    getAnimeCommentList: async (req) => {
        return await get("/anime/getAnimeCommentList", req);
    },
    delAnimeComment: async (req) => {
        return await del("/anime/delAnimeComment", req, UrlReqType.path);
    }
}

const friend: FriendInterface = {
    applyFriendChain: async (req) => {
        return await post("/friend/applyFriendChain", req);
    },
    getFriendListData: async () => {
        return await get("/friend/getFriendListData");
    },
    getFriendList: async (req) => {
        return await get("/friend/getFriendList", req);
    },
    getFriendPreamble: async () => {
        return await get("/friend/getFriendPreamble");
    }
}


const version: VersionInterface = {
    getVersionInfo: async () => {
        return await get("/version/getVersionInfo");
    },
    getVersionList: async (req) => {
        return await get("/version/getVersionList", req);
    }
}

const api: ApiObject = {
    ...site,
    ...user,
    ...blog,
    ...shuoshuo,
    ...music,
    ...video,
    ...joke,
    ...anime,
    ...friend,
    ...version
}

export default api;