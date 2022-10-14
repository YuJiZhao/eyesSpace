import { Ref } from "vue";

export declare interface BlogDetailContextInterface {
    data: Partial<BlogDetailContextDataInterface>;
    init: (cx: Partial<BlogDetailContextDataInterface>) => void
}

export declare interface BlogDetailContextDataInterface {
    title: string;
    summary: string;
    content: string;
    category: string;
    labels: Array<string>;
    views: number;
    likes: number;
    existLike: boolean;
    collections: number;
    existCollect: boolean;
    comments: number;
    words: number;
    createTime: string;
}