interface ConfigType {
    blogUrl: string,
    apiUrl: string,
    picUrl: string,
    videoUrl: string
}

interface AlertMsgType {
    copyAlert: {title: String, content: String}
}

interface AnnouncementMsgType {
    defaultAnnouncement: {title: String, content: String}
}