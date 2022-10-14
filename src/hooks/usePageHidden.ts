interface UsePageHiddenInterface {
    addEvent: (showCallback: Function, hideCallback?: Function) => Function;
    removeEvent: (func: any) => void;
}

const usePageHidden: UsePageHiddenInterface = {
    addEvent: (showCallback, hideCallback) => {
        let addFunc = () => {
            if (!document.hidden) showCallback();
            else if(hideCallback) hideCallback();
        }
        document.addEventListener("visibilitychange", addFunc);
        return addFunc;
    },
    removeEvent: (func) => {
        document.removeEventListener("visibilitychange", func);
    }
}

export default usePageHidden;