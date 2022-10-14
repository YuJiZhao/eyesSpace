import { Ref, UnwrapNestedRefs } from "vue";

export declare interface VideoProcessInterface extends 
    CardListInterface,
    VideoExtInterface
{}

export declare interface CardListInterface {
    cardInitLoad: Ref<boolean>;
    cardInitFail: Ref<boolean>;
}

export declare interface VideoExtInterface {
    fullscreenSentry: Ref<boolean>;
    doFullScreen: () => void;
    
    likesNumSentry: Ref<boolean>;

    videoEndSentry: Ref<boolean>;
    doChangeVideo: () => void;

    videoVariable: Ref<boolean>;
}