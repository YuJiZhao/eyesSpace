import { Ref } from "vue";

export declare interface MusicContextInterface {
    data: Partial<MusicContextDataInterface>;
    init: (cx: Partial<MusicContextDataInterface>) => void
}

interface MusicContextDataInterface {
    id: string;
    title: string;
    author: string;
    url: string;
    pic: string;
    views: number;
    likes: number;
    comments: number;
    isLike: boolean;
}