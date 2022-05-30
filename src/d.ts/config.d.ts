import { AplayerSongServer, AplayerSongType, AplayerLoop, AplayerOrder, AplayerPreload, AplayerLrcType } from "@/constant/aplayerConstant";

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

interface AplayerConfigType {
    songServer: AplayerSongServer,
    songType: AplayerSongType,
    songId: number,
    fixed: boolean,
    mini: boolean,
    autoplay: boolean,
    loop: AplayerLoop,
    order: AplayerOrder,
    preload: AplayerPreload,
    volume: number,
    mutex: boolean,
    lrcType: AplayerLrcType
}