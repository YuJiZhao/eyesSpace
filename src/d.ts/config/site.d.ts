export declare interface SiteContextInterface {
    clientId: number;
    siteName: string;
    siteNameEn: string;
    spaceVersion: string;
    ownerEmail: string;
    siteVideoBV: string;
    commentMaxLen: number;
}

export declare interface UserCenterContextInterface {
    info: string;
    auth: string;
    redirectUrl: string;
}

export declare type ContactMeConfigType = Array<ContactMeConfigInterface>;
interface ContactMeConfigInterface {
    title: string;
    content: string;
    btnIcon: string;
    btnWord: string;
    clickFunc: string;
}

export declare type HeaderConfigType = Array<HeaderBarInterface>;
interface HeaderBarInterface {
    path?: string;
    icon: string;
    word: string;
    children?: Array<{
        path: string;
        icon: string;
        word: string;
    }>
}

export declare type SiteDataType = Array<KNInterface>;
interface KNInterface {
    key: number;
    name: string;
}

export declare interface FooterConfigInterface {
    copyright: string;
    theme: string;
    techStack: string;
    zwfwCode: string;
}

export declare interface ErrorPathInterface {
    route: string;
    context: string;
}

export declare interface ErrorConfigInterface {
    errorRoute: ErrorConfigPropertyInterface;
    errorContext: ErrorConfigPropertyInterface;
}
export declare interface ErrorConfigPropertyInterface {
    title: string;
    items?: Array<ErrorConfigItemInterface>;
    process: Array<boolean>;
}
interface ErrorConfigItemInterface {
    icon: string;
    word: string;
    clickFunc: string;
}