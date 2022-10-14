export declare interface ShuoContextInterface {
    data: Array<Partial<ShuoContextDataInterface>>;
    init: (cx: Array<Partial<ShuoContextDataInterface>>) => void
}

interface ShuoContextDataInterface {
    id: string;
    content: string;
    picList: Array<string>;
    views: number;
    comments: number;
    createTime: string;
}