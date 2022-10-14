import jQuery from "jquery";
import touchUtils from "@/utils/touch";

type useMouseWheelType = (
    wheelUp: () => void, 
    wheelDown: () => void
) => void;

const useMouseWheel: useMouseWheelType = (wheelUp, wheelDown) => {
    jQuery($ => {
        $(document).on('mousewheel DOMMouseScroll', function(e){
            let event = e as any;
            let wheel = event.originalEvent.wheelDelta;
            let detal = event.originalEvent.detail;
            if (event.originalEvent.wheelDelta) { // IE, Chrome兼容               
                if (wheel > 0) wheelUp();
                else wheelDown();
            } else if (event.originalEvent.detail) {  // Firefox兼容  
                if (detal > 0) wheelDown();
                else wheelUp();
            }
        });
        // 兼容移动端滑动事件
        touchUtils.listenTouchDirection(document, false, wheelDown, wheelUp);  
    })
};

export default useMouseWheel;