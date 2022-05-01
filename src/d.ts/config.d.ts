/**
 * 网站配置类型
 */

interface urlType {
    blogUrl: string,
    apiUrl: string,
    picUrl: string,
    videoUrl: string,
}

interface AlertMsgType {
    copyAlert: {title: string, content: string}
}

interface AnnouncementMsgType {
    defaultAnnouncement: {title: string, content: string}
}

interface ErrorMsgType {
    apiError: string,
    apiErrorDetail: string,
    contextError: string
}

/**
 * 外部应用配置类型
 */

declare enum AplayerSongServerType { "netease", "tencent", "kugou", "xiami", "baidu" }
declare enum AplayerSongTypeType { "song", "playlist", "album", "search", "artist" }
declare enum AplayerLoopType { "all", "one", "none" }
declare enum AplayerOrderType { "list", "random" }
declare enum AplayerPreloadType { "auto", "metadata", "none" }
declare enum AplayerLrcTypeType { 
    "zero" = 0,
    "one" = 1,
    "two" = 2,
    "three" = 3
}
interface AplayerConfigType {
    songServer: AplayerSongServerType,
    songType: AplayerSongTypeType,
    songId: number,
    fixed: boolean,
    mini: boolean,
    autoplay: boolean,
    loop: AplayerLoopType,
    order: AplayerOrderType,
    preload: AplayerPreloadType,
    volume: number,
    mutex: boolean,
    lrcType: AplayerLrcTypeType
}