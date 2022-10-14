import { ref } from "vue";
import { 
    GoCommentInterface, 
    InputControllerInterface, 
    DelCommentProcessInterface, 
    PublishCommentProcessInterface 
} from "@/components/general/comment/d.ts/commentProcess";

export const goComment: GoCommentInterface = {
    goCommentSentry: ref(false),
    objectId: ref(0),
    replyName: ref(""),
    landlord: ref<undefined | number>(),
    clickFunc: (objectId, replyName, landlord) => {
        goComment.objectId.value = objectId;
        goComment.replyName.value = replyName;
        goComment.landlord.value = landlord;

        inputController.doInputFocus();
        setTimeout(() => {
            window.scroll(0, Math.max(window.pageYOffset - 200, 0));
        });
        goComment.goCommentSentry.value = !goComment.goCommentSentry.value;
    }
}

export const inputController: InputControllerInterface = {
    focusSentry: ref(false),
    doInputFocus: () => {
        inputController.focusSentry.value = !inputController.focusSentry.value;
    },

    fillReplySentry: ref(false),
    doFillReply: () => {
        inputController.fillReplySentry.value = !inputController.fillReplySentry.value;
    }
}

export const delCommentProcess: DelCommentProcessInterface = {
    delSentry: ref(false),
    delId: ref(0),
    doDelComment: (id) => {
        delCommentProcess.delId.value = id;
        delCommentProcess.delSentry.value = !delCommentProcess.delSentry.value;
    }
}

export const publishCommentProcess: PublishCommentProcessInterface = {
    publishSentry: ref(false),
    doPublishComment: () => {
        publishCommentProcess.publishSentry.value = !publishCommentProcess.publishSentry.value;
    }
}