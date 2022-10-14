import { Ref } from "vue";

export declare interface GoCommentInterface {
    goCommentSentry: Ref<boolean>;
    objectId: Ref<number>;
    replyName: Ref<string>;
    landlord: Ref<undefined | number>;
    clickFunc: (objectId: number, replyName: string, landlord?: number) => void;
}

export declare interface InputControllerInterface {
    focusSentry: Ref<boolean>;
    doInputFocus: () => void;

    fillReplySentry: Ref<boolean>;
    doFillReply: () => void;
}

export declare interface DelCommentProcessInterface {
    delSentry: Ref<boolean>;
    delId: Ref<number>;
    doDelComment: (id: number) => void;
}

export declare interface PublishCommentProcessInterface {
    publishSentry: Ref<boolean>,
    doPublishComment: () => void;
}