/**
 * url管理
 */

const config: ConfigType = {
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

export { config, alertMsg, announcementMsg };