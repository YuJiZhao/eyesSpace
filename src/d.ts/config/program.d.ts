export declare interface UrlConfigInterface {
    siteUrl: string;
    resourceUrl: string;
    userResourceUrl: string;
    eyesResourceUrl: string;
    warehouseUrl: string;
    zwfwUrl: string;
    picbedUrl: string;
    avatarUploadUrl: string;
}

export declare interface SiteConfigInterface {
    tokenHeader: {
        sToken: string;
        lToken: string;
    };
    tokenExpireTime: number;
    mpThreshold: number;
    aesKey: string;
    aesIV: string;
    enterURL: string;
    keepAliveRoute: Array<string>;
    avatarMaxSize: number;
    avatarImgType: Array<string>;
    sideBarShowStorage: string;
    stickyKey: string;
}

export declare interface CodeConfigInterface {
    success: number;
    fail: number;
    authentication_error: number;
    authentication_error_illegal_jwt: number;
    authentication_expired: number;
    account_freezing: number;
    account_not_found: number;
    no_time: number;
}