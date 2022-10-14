import { ProcessInterface, PopupInterface, ComponentInterface } from "@/d.ts/modules/process";
import { reactive, ref, nextTick } from "vue";

// 弹出层逻辑控制
const Popup: PopupInterface = {
    // 黑色幕布
    maskStatus: ref(false),
    maskClickFunc: ref(() => {}),
    maskShow(clickFunc) {
        Popup.maskClickFunc.value = clickFunc || Popup.maskClickFunc.value;
        Popup.maskStatus.value = true; 
    },
    maskHide() { 
        Popup.maskClickFunc.value = () => {};
        Popup.maskStatus.value = false; 
    },

    // 页面跳转加载页面
    loadStatus: ref(true),
    loadShow() { Popup.loadStatus.value = true; },
    loadHide() { Popup.loadStatus.value = false; },

    // 轻提示
    tipSentry: ref(false),
    tipList: reactive({
        msg: "",
        type: 0
    }),
    tipShow: {
        success: (msg) => {
            Popup.tipList.msg = msg;
            Popup.tipList.type = 0;
            Popup.tipSentry.value = !Popup.tipSentry.value;
        },
        info: (msg) => {
            Popup.tipList.msg = msg;
            Popup.tipList.type = 1;
            Popup.tipSentry.value = !Popup.tipSentry.value;
        },
        warn: (msg) => {
            Popup.tipList.msg = msg;
            Popup.tipList.type = 2;
            Popup.tipSentry.value = !Popup.tipSentry.value;
        },
        error: (msg) => {
            Popup.tipList.msg = msg;
            Popup.tipList.type = 3;
            Popup.tipSentry.value = !Popup.tipSentry.value;
        }
    },

    // 提示
    alertStatus: ref(false),
    alertMsg: reactive({
        title: "",
        content: ""
    }),
    alertShow(msg) {
        if(Popup.alertStatus.value = true) {
            Popup.alertStatus.value = false;
        }
        setTimeout(() => {
            Popup.alertMsg.title = msg.title;
            Popup.alertMsg.content = msg.content;
            Popup.alertStatus.value = true;
        }, 500);
    },
    alertHide() { 
        Popup.alertStatus.value = false;
    },

    // 弹窗
    dialogStatus: ref(false),
    dialogChoice: ref(0),
    dialogShow: (dialog) => {
        Popup.dialogChoice.value = dialog;
        Popup.dialogStatus.value = true;
    },
    dialogHide: () => { Popup.dialogStatus.value = false; }
};

const Component: ComponentInterface = {
    // 顶部导航栏
    headerStatus: ref(true),
    headerCheckLock: ref(true),
    headerCheckSwitch(clientHeight) {
        nextTick(() => {
            Component.headerCheckLock.value = document.querySelector("html")!.offsetHeight < clientHeight;
        })
    },

    // 侧栏卡片
    sideCardStatus: ref(true),
    sideCardPosition: ref("row"),
    sideCardType: ref(0),
    sideCardChoice: ref([]),
    sideCardList: ref(0),
    sideCardFollow: ref(false),

    // 底部组件
    footerStatus: ref(true),
    footerPosition: ref(true),
    footerPositionSwitch(clientHeight) {
        nextTick(() => {
            if(!document.querySelector(".footer")) return;
            Component.footerPosition.value = (
                clientHeight < document.querySelector("html")!.offsetHeight
                            + (document.querySelector(".footer") as HTMLElement).offsetHeight
            );
        })
    }
}

const Process: ProcessInterface = {
    ...Popup,
    ...Component
}

export default Process;