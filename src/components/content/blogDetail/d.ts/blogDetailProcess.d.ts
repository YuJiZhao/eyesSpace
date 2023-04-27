import { Ref, UnwrapNestedRefs } from "vue";

export declare interface BlogDetailProcessInterface extends 
    CardListInterface,
    ExtInterface
{}

export declare interface CardListInterface {
    cardInitLoad: Ref<boolean>,
    cardInitFail: Ref<boolean>
}