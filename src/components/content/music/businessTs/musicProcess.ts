import { MusicProcessInterface, MusicExtInterface } from "@/components/content/music/d.ts/musicProcess";
import { reactive, ref } from "vue";

const ext: MusicExtInterface = {
    initSentry: ref(false),
    initNotice: () => {
        ext.initSentry.value = !ext.initSentry.value;
    },

    musicEndSentry: ref(false),
    doChangeMusic: () => {
        ext.musicEndSentry.value = !ext.musicEndSentry.value;
    },

    musicRangeSentry: ref(false),
    rangeNotice: () => {
        ext.musicRangeSentry.value = !ext.musicRangeSentry.value;
    }
}

const musicProcess: MusicProcessInterface = {
    ...ext
}

export default musicProcess;