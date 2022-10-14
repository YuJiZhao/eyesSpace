import { Ref } from "vue";

export declare interface VideoContextInterface {
    data: Partial<VideoContextDataInterface>;
    init: (cx: Partial<VideoContextDataInterface>) => void
}

interface VideoContextDataInterface {
    id: string;
    title: Ref<string>;
    originalAuthor: string;
    pictureUrl: string;
    originalUrl: string;
    ownerComment: string;
    videoUrl: string;
    views: number;
    likes: number;
    comments: number;
    createTime: string;
    isLike: boolean;
}