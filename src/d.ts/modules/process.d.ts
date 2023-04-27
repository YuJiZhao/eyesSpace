import { Ref, UnwrapNestedRefs } from "vue";
import { Cards, CardList, CardType, Dialogs } from "@/constant";
import { RollType } from "@/hooks/useGoBoth";

export declare interface ProcessInterface extends PopupInterface, ComponentInterface {
}

/*
 ***************************************************************************************
 *                                    popup
 ***************************************************************************************
 */

export declare interface PopupInterface {
    maskStatus: Ref<boolean>;
    maskClickFunc: Ref<() => void>;
    maskShow: (clickFunc: (() => void) | null) => void;
    maskHide: () => void;

    loadStatus: Ref<boolean>;
    loadShow: () => void;
    loadHide: () => void;

    tipSentry: Ref<boolean>;
    tipList: UnwrapNestedRefs<{msg: string, type: number}>;
    tipShow: TipShowInterface;

    alertStatus: Ref<boolean>;
    alertMsg: UnwrapNestedRefs<{title: string, content: string}>
    alertShow: (msg: UnwrapNestedRefs<{title: string, content: string}>) => void;
    alertHide: () => void;

    dialogStatus: Ref<boolean>;
    dialogChoice: Ref<Dialogs>;
    dialogShow: (dialog: Dialogs) => void;
    dialogHide: () => void;
}

interface TipShowInterface {
    success: (msg: string) => void;
    info: (msg: string) => void;
    warn: (msg: string) => void;
    error: (msg: string) => void;
}

/*
 ***************************************************************************************
 *                                    component
 ***************************************************************************************
 */
export declare interface ComponentInterface {
    headerStatus: Ref<boolean>;
    headerCheckLock: Ref<boolean>;
    headerCheckSwitch: (clientHeight: number, HTMLHeight: number) => void;

    sideCardStatus: Ref<boolean>;
    sideCardPosition: Ref<string>;
    sideCardType: Ref<CardType>;
    sideCardChoice: Ref<Array<Cards>>;
    sideCardList: Ref<CardList>;
    sideCardFollow: Ref<boolean>;

    footerStatus: Ref<boolean>;
    footerPosition: Ref<boolean>;
    footerPositionSwitch: (clientHeight: number, HTMLHeight: number) => void;

    rollType: Ref<RollType>;
    rollTime: Ref<number>;
}