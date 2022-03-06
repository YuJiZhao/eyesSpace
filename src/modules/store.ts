import { reactive, ref } from "vue";

// 弹出层
const Popup: Popup = {
    // 页面跳转加载控制
    loadStatus: ref(false),
    loadShow() { Popup.loadStatus.value = true; },
    loadHide() { Popup.loadStatus.value = false; },

    // 等待控制
    waitStatus: ref(false),
    waitShow() { Popup.waitStatus.value = true; },
    waitHide() { Popup.waitStatus.value = false; },
};

// 文案配置
const CV: CV = {
    context: {},
    init: <T>(context: T) => {
        CV.context = context;
    },
};

// 用户信息
const User: U = {
    info: reactive({ detail: {} }),
    init: <T>(userInfo: T) => {
        User.info.detail = userInfo;
    },
};

// 流程逻辑控制
const Ext: ext = {
};

export { User, CV, Popup, Ext };