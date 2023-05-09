import { get, post, put, del } from "./ajax"

const video = {
    addVideo: async (req) => {
        return await post("/video/addVideo", req);
    },
    getVideoList: async (req) => {
        return await get("/video/getVideoList", req);
    },
    getVideoListInfo: async () => {
        return await get("/video/getVideoListInfo");
    },
    getVideoInfo: async (req) => {
        return await get("/video/getVideoInfo", req);
    }
}

const music = {
    addMusic: async (req) => {
        return await post("/music/addMusic", req);
    },
    getMusicList: async (req) => {
        return await get("/music/getMusicList", req);
    },
    getMusicListInfo: async () => {
        return await get("/music/getMusicListInfo");
    },
    getMusicInfo: async (req) => {
        return await get("/music/getMusicInfo", req);
    }
}

const shuoshuo = {
    addShuoshuo: async (req) => {
        return await post("/shuo/addShuo", req);
    },
    getShuoshuoListInfo: async () => {
        return await get("/shuo/getShuoListInfo");
    },
    getShuoshuoList: async (req) => {
        return await get("/shuo/getShuoList", req);
    }
}

const blog = {
    addBlog: async (req) => {
        return await post("/blog/addBlog", req);
    },
    addBlogPic: async (req) => {
        return await post("/blog/addBlogPic", req);
    },
}

const api = {
    ...video,
    ...music,
    ...shuoshuo,
    ...blog
}

export default api;