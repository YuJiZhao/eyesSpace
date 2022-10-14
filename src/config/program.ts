import { UrlConfigInterface, SiteConfigInterface, CodeConfigInterface } from "@/d.ts/config/program";
import { publicPath } from "@/router/path";

const urlConfig: UrlConfigInterface = {
    siteUrl: process.env.VITE_SITE_URL!,
    resourceUrl: "http://spaceresource.eyescode.top/",
    warehouseUrl: "https://github.com/YuJiZhao/eyesSpace",
    zwfwUrl: "https://beian.miit.gov.cn",
    picbedUrl: "http://img.codesocean.top",
    avatarUploadUrl: `${process.env.VITE_API_DOMAIN}/site/user/info/updateUserAvatar`
};

const siteConfig: SiteConfigInterface = {
    tokenHeader: "Authorization",
    tokenExpireTime: 30,
    mpThreshold: 800,
    aesKey: "",
    aesIV: "",
    enterURL: "enterURL",
    keepAliveRoute: ["Home", "Blog", "Shuoshuo", "Music", "Video", "About", "Login"],
    avatarMaxSize: 5,
    avatarImgType: ["image/png", "image/jpg", "image/jpeg"],
    followCardId: "stickyCard",
}

const codeConfig: CodeConfigInterface = {
    success: 200,
    fail: 400,
    authentication_error: 1000,
    authentication_error_illegal_jwt: 1001,
    authentication_expired: 1002,
    account_freezing: 1003,
    gateway_exception: 1004,
    none_http_servlet_request: 1005,
    gateway_bypassed: 1006,

    no_time: 2000,
}

const pathConfig = publicPath;

export { urlConfig, siteConfig, codeConfig, pathConfig }