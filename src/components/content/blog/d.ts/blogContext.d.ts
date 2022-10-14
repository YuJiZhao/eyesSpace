export declare interface BlogContextInterface {
    data: Array<Partial<BlogContextDataInterface>>;
    init: (cx: Array<Partial<BlogContextDataInterface>>) => void;
}

export declare interface BlogContextDataInterface {
    id: number;
    title: string;
    summary: string;
    category: string;
    views: number;
    likes: number;
    collections: number;
    words: number;
    createTime: string;
}