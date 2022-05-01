/**
 * 网站相关配置
 */


/**
 * url管理
 */
const urlConfig: urlType = {
    blogUrl: "https://blog.eyescode.top/",
    apiUrl: "/api",
    picUrl: "https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/",
    videoUrl: "",
};

/**
 * 内置提示内容
 */
const alertMsg: AlertMsgType = {
    copyAlert: {
        title: "不能打开控制台喔",
        content: "采用本站js及css请注明来源，请勿商业使用哦"
    }
}

/**
 * 内置公告内容
 */
const announcementMsg: AnnouncementMsgType = {
    defaultAnnouncement: {
        title: "公告",
        content: "我的博客网站历经艰险，终于上线啦! 本站有我的博客，说说等内容，但也可以在这里听歌看视频。",
    },
}

/**
 * 内置报错信息
 */
const errorMsg: ErrorMsgType = {
    apiError: "接口请求出错...",
    apiErrorDetail: "请检查您网络是否正常，正常的话那应该是我网站服务器崩了...",
    contextError: "网络出错...",
}

export { urlConfig, alertMsg, announcementMsg, errorMsg };