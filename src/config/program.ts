import { UrlConfigInterface, SiteConfigInterface, CodeConfigInterface } from "@/d.ts/config/program";
import { publicPath } from "@/router/path";

const urlConfig: UrlConfigInterface = {
    siteUrl: process.env.VITE_SITE_URL!,
    resourceUrl: "http://space-cdn.eyescode.top/",
    userResourceUrl: "http://user-cdn.eyescode.top/",
    eyesResourceUrl: "http://eyes-cdn.eyescode.top/",
    warehouseUrl: "https://github.com/YuJiZhao/eyesSpace",
    zwfwUrl: "https://beian.miit.gov.cn",
    picbedUrl: "http://img.codesocean.top",
    avatarUploadUrl: `${process.env.VITE_API_DOMAIN}/user/info/updateUserAvatar`
};

const siteConfig: SiteConfigInterface = {
    tokenHeader: {
        sToken: "sAuth",
        lToken: "lAuth"
    },
    tokenExpireTime: 30,
    mpThreshold: 800,
    aesKey: "",
    aesIV: "",
    enterURL: "enterURL",
    keepAliveRoute: ["Home", "Blog", "Shuoshuo", "Music", "Video", "Message", "About", "Login", "Version"],
    avatarMaxSize: 5,
    avatarImgType: ["image/png", "image/jpg", "image/jpeg"],
    followCardId: "stickyCard",
    sideBarShowStorage: "isSideBarShow"
}

const codeConfig: CodeConfigInterface = {
    success: 200,
    fail: 400,
    authentication_error: 20000,
    authentication_error_illegal_jwt: 20001,
    authentication_expired: 20002,
    account_freezing: 20003,
    account_not_found: 20004,
    no_time: 2001,
}

const pathConfig = publicPath;

export { urlConfig, siteConfig, codeConfig, pathConfig }