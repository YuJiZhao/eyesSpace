import { PopupType, CVType, UserType, ExtType } from "@/d.ts/modules";
import { reactive, ref, UnwrapNestedRefs } from "vue";
import { announcementMsg } from "@/config/index"

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
        ...announcementMsg.defaultAnnouncement
    }),
    announcementShow(msg: UnwrapNestedRefs<{title: String, content: String}>) {
        Popup.announcementMsg.title = msg.title;
        Popup.announcementMsg.content = msg.content;
        Popup.announcementStatus.value = true;
    },
    announcementHide() {
        Popup.announcementStatus.value = false; 
        Popup.announcementMsg.title = announcementMsg.defaultAnnouncement.title;
        Popup.announcementMsg.content = announcementMsg.defaultAnnouncement.content;
    },
};

// 文案配置
const CV: CVType = {
    context: {},
    init: <T>(context: T) => {
        CV.context = context;
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