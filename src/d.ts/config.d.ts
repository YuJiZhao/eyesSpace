interface ConfigType {
    blogUrl: String,
    picUrl: String,
    videoUrl: String
}

interface AlertMsgType {
    copyAlert: {title: String, content: String}
}

interface AnnouncementMsgType {
    defaultAnnouncement: {title: String, content: String}
}