// @ts-nocheck
export default {
    addHandler: function (element, type, handler) {
        if (element.addEventListener)
            element.addEventListener(type, handler);
        else if (element.attachEvent)
            element.attachEvent("on" + type, handler);
        else
            element["on" + type] = handler;
    },
    removeHandler: function (element, type, handler) {
        if(element.removeEventListener)
            element.removeEventListener(type, handler);
        else if(element.detachEvent)
            element.detachEvent("on" + type, handler);
        else
            element["on" + type] = handler;
    },

    // 监听触摸的方向
    listenTouchDirection: function (
        target, 
        isPreventDefault = false,
        upCallback?: () => void, 
        downCallback?: () => void,
        leftCallback?: () => void, 
        rightCallback?: () => void
    ) { 
        this.addHandler(target, "touchstart", handleTouchEvent);
        this.addHandler(target, "touchend", handleTouchEvent);
        this.addHandler(target, "touchmove", handleTouchEvent);
        let startX;
        let startY;

        function handleTouchEvent(event) {
            switch (event.type){
                case "touchstart":
                    startX = event.touches[0].pageX;
                    startY = event.touches[0].pageY;
                    break;
                case "touchend":
                    let spanX = event.changedTouches[0].pageX - startX;
                    let spanY = event.changedTouches[0].pageY - startY;
                    if(spanX > 5){
                        if(rightCallback) rightCallback();
                    } else if(spanX < -5){
                        if(leftCallback) leftCallback();
                    }
                    if(spanY > 5){
                        if(downCallback) downCallback();
                    } else if (spanY < -5) {
                        if(upCallback) upCallback();
                    }
                    break;
                case "touchmove":
                    if(isPreventDefault) event.preventDefault();
                    break;
            }
        }
    }
}
