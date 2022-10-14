import { CardListInterface, BlogDetailProcessInterface, ExtInterface } from "@/components/content/blogDetail/d.ts/blogDetailProcess";
import { reactive, ref } from "vue";

const CardList: CardListInterface = {
    cardInitLoad: ref(true),
    cardInitFail: ref(false)
};

const ext: ExtInterface = {
    likesNumSentry: ref(false),
    likesNumStep: ref(0),
    changeLikesNum: (num) => {
        ext.likesNumSentry.value = !ext.likesNumSentry.value;
        ext.likesNumStep.value = num;
    },

    collectNumSentry: ref(false),
    collectNumStep: ref(0),
    changecollectNum: (num) => {
        ext.collectNumSentry.value = !ext.collectNumSentry.value;
        ext.collectNumStep.value = num;
    }
}

const blogDetailProcess: BlogDetailProcessInterface = {
    ...CardList,
    ...ext
}

export default blogDetailProcess;