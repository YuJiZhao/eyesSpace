import { Ref, UnwrapNestedRefs } from "vue";

export declare interface BlogDetailProcessInterface extends 
    CardListInterface,
    ExtInterface
{}

export declare interface CardListInterface {
    cardInitLoad: Ref<boolean>,
    cardInitFail: Ref<boolean>
}

export declare interface ExtInterface {
    likesNumSentry: Ref<boolean>,
    likesNumStep: Ref<number>,
    changeLikesNum: (num: number) => void,

    collectNumSentry: Ref<boolean>,
    collectNumStep: Ref<number>,
    changecollectNum: (num: number) => void,
}