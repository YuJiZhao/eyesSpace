export declare interface UrlConfigInterface {
    siteUrl: string;
    resourceUrl: string;
    warehouseUrl: string;
    zwfwUrl: string;
    picbedUrl: string;
    avatarUploadUrl: string;
}

export declare interface SiteConfigInterface {
    tokenHeader: string;
    tokenExpireTime: number;
    mpThreshold: number;
    aesKey: string;
    aesIV: string;
    enterURL: string;
    keepAliveRoute: Array<string>;
    avatarMaxSize: number;
    avatarImgType: Array<string>;
    followCardId: string;
}

export declare interface CodeConfigInterface {
    success: number;
    fail: number;
    authentication_error: number;
    authentication_error_illegal_jwt: number;
    authentication_expired: number;
    account_freezing: number;
    gateway_exception: number;
    none_http_servlet_request: number;
    gateway_bypassed: number;

    no_time: number;
}