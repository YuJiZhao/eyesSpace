import { PopupType, CVType, UserType, ExtType } from "@/d.ts/modules";
import { reactive, ref, UnwrapNestedRefs } from "vue";
import { errorMsg } from "@/config/websiteConfig";

// 弹出层
const Popup: PopupType = {
    // 页面跳转加载页面
    loadStatus: ref(false),
    loadShow() { Popup.loadStatus.value = true; },
    loadHide() { Popup.loadStatus.value = false; },

    // 提示
    alertStatus: ref(false),
    alertMsg: reactive({
        title: "",
        content: ""
    }),
    alertShow(msg: UnwrapNestedRefs<{title: String, content: String}>) {
        Popup.alertMsg.title = msg.title;
        Popup.alertMsg.content = msg.content;
        Popup.alertStatus.value = true;
    },
    alertHide() { 
        Popup.alertStatus.value = false;
        Popup.alertMsg.title = "";
        Popup.alertMsg.content = "";
    },

    // 公告
    announcementStatus: ref(false),
    announcementMsg: reactive({
        title: "",
        content: ""
    }),
    announcementShow(msg: UnwrapNestedRefs<{title: String, content: String}>) {
        Popup.announcementMsg.title = msg.title || errorMsg.contextError;
        Popup.announcementMsg.content = msg.content || errorMsg.contextError;
        Popup.announcementStatus.value = true;
    },
    announcementHide() {
        Popup.announcementStatus.value = false; 
    },

    // 打赏
    rewardStatus: ref(false),
    rewardShow() { Popup.rewardStatus.value = true; },
    rewardHide() { Popup.rewardStatus.value = false; },
};

// 文案配置
let CV: CVType = {
    data: {},
    init: <T>(context: T) => {
        CV.data = context;
        // TODO：我想直接在CV上加上参数，这样减少调用时的层级
        // CV = {
        //     CV,
        //     ...context
        // }
    },
};

// 用户信息
const User: UserType = {
    info: reactive({ detail: {} }),
    init: <T>(userInfo: T) => {
        User.info.detail = userInfo;
    },
};

// 流程逻辑控制
const Ext: ExtType = {
};

export { User, CV, Popup, Ext };