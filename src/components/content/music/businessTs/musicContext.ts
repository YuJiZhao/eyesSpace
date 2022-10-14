import { MusicContextInterface } from "@/components/content/music/d.ts/musicContext";

const musicContext: MusicContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(musicContext.data, cx);
    },
}

export { musicContext };