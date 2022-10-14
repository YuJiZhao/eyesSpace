import { VideoProcessInterface, CardListInterface, VideoExtInterface } from "@/components/content/video/d.ts/videoProcess";
import { reactive, ref } from "vue";

const CardList: CardListInterface = {
    cardInitLoad: ref(true),
    cardInitFail: ref(false)
};

const ext: VideoExtInterface = {
    fullscreenSentry: ref(false),
    doFullScreen: () => {
        ext.fullscreenSentry.value = !ext.fullscreenSentry.value;
    },

    likesNumSentry: ref(false),

    videoEndSentry: ref(false),
    doChangeVideo: () => {
        ext.videoEndSentry.value = !ext.videoEndSentry.value;
    },

    videoVariable: ref(false),
}

const videoProcess: VideoProcessInterface = {
    ...CardList,
    ...ext
}

export default videoProcess;