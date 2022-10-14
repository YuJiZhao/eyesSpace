import { WindowInterface, WindowSizeInterface, WindowDistanceInterface } from "@/d.ts/modules/window";
import { ref } from "vue";

const size: WindowSizeInterface = {
    width: ref(0),
    height: ref(0),
    initSize() {
        size.width.value = document.documentElement.clientWidth;
        size.height.value = document.documentElement.clientHeight;
    }
}

const distance: WindowDistanceInterface = {
    scrollTop: ref(0),
    initDistance() {
        distance.scrollTop.value = document.documentElement.scrollTop || document.body.scrollTop;
    }
}

const Window: WindowInterface = {
    ...size,
    ...distance
};

export default Window;