import { urlConfig } from "@/config/program";

const sitePrefix = "site/";
const siteResource = urlConfig.resourceUrl + sitePrefix;

const avatarPrefix = "avatar/";
const avatarResource = urlConfig.resourceUrl + avatarPrefix;

const icon = {
    success: siteResource + "success.png",
    warn: siteResource + "warn.png",
    info: siteResource + "info.png",
    error: siteResource + "error.png",
    fail: siteResource + "fail.png",
    home: siteResource + "home.png",
    blog: siteResource + "blog.png",
    shuoshuo: siteResource + "shuoshuo.png",
    music: siteResource + "music.png",
    video: siteResource + "video.png",
    about: siteResource + "about.png",
    menu: siteResource + "menu.png",
    before: siteResource + "before.png",
    announce: siteResource + "announce.png",
    data: siteResource + "data.png",
    comment: siteResource + "comment.png",
    refresh: siteResource + "refresh.png",
    email: siteResource + "email.png",
    copy: siteResource + "copy.png",
    bilibili: siteResource + "bilibili.png",
    copyright: siteResource + "copyright.png",
    like: siteResource + "like.png",
    likeActive: siteResource + "like_active.png",
    hide: siteResource + "hide.png",
    fullscreen: siteResource + "fullscreen.png",
    switch: siteResource + "switch.png",
    touch: siteResource + "touch.png",
    load: siteResource + "load.png",
    category: siteResource + "category.png",
    label: siteResource + "label.png",
    top: siteResource + "top.png",
    collect: siteResource + "collect.png",
    collectActive: siteResource + "collectActive.png",
    reward: siteResource + "reward.png",
    catalogue: siteResource + "catalogue.png",
    function: siteResource + "function.png",
    range: siteResource + "range.png",
}

const img = {
    errorPage: siteResource + "errorPage.png",
    defaultAvatar: avatarResource + "default.jpg",
    alipay: siteResource + "alipay.png",
    wepay: siteResource + "wepay.png",
}

const gif = {
    loading: siteResource + "loading.gif",
}

const data = {
    emojiJson: "http://eyescdn.eyescode.top/data/emoji.json"
}

const resource = {
    ...icon,
    ...img,
    ...gif,
    ...data
}

export default resource;