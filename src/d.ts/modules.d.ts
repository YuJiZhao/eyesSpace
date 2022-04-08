import { Ref, UnwrapNestedRefs } from "vue";

// 用户类型
interface UserType {
    readonly info: { detail: Partial<userInfoType> };
    init: <T>(info: T) => void;
}
interface userInfoType {
}

// 弹出层
interface PopupType {
    loadStatus: Ref<Boolean>;
    loadShow: () => void;
    loadHide: () => void;

    alertStatus: Ref<Boolean>;
    alertShow: (content: String) => void;
    alertHide: () => void;

    announcementStatus: Ref<Boolean>;
    announcementMsg: UnwrapNestedRefs<{title: String, content: String}>
    announcementShow: (msg: UnwrapNestedRefs<{title: String, content: String}>) => void;
    announcementHide: () => void;
}

// 文案配置
interface CVType {
    context: Partial<context>;
    init: <T>(context: T) => void;
}
interface context {
    blog_name: string;
    cover: string;
    navBar: Partial<iconLink>;
    avatar: string;
    nick: string;
    motto: string;
    footprint: Partial<iconLink>;
    announcement: string;
    date: string;
    wechat_reward: string;
    alipay_reward: string;
    copyright: string;
    theme_name: string;
    project_info: Partial<iconLink>;
}
interface iconLink {
    icon: string;
    title: string;
    link: string;
}

// 流程逻辑控制
interface ExtType {
}