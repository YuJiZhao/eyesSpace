import { VideoContextInterface } from "@/components/content/video/d.ts/videoContext";

const videoContext: VideoContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(videoContext.data, cx);
    },
}

export { videoContext };