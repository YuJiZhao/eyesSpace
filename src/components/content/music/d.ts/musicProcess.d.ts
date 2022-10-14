import { Ref, UnwrapNestedRefs } from "vue";

export declare interface MusicProcessInterface extends 
    MusicExtInterface
{}

export declare interface MusicExtInterface {
    initSentry: Ref<boolean>;
    initNotice: () => void;

    musicEndSentry: Ref<boolean>;
    doChangeMusic: () => void;

    musicRangeSentry: Ref<boolean>;
    rangeNotice: () => void;
}