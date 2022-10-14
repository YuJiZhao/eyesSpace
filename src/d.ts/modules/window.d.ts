import { Ref } from "vue";

export declare interface WindowInterface extends 
    WindowSizeInterface,
    WindowDistanceInterface 
{}

/*
 ***************************************************************************************
 *                                    size
 ***************************************************************************************
 */

export declare interface WindowSizeInterface {
    width: Ref<number>;
    height: Ref<number>;
    initSize: () => void;
}

export declare interface WindowDistanceInterface {
    scrollTop: Ref<number>;
    initDistance: () => void;
}