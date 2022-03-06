interface U {
    readonly info: { detail: Partial<userInfoType> };
    init: <T>(info: T) => void;
}

interface Popup {
    loadStatus: any;
    loadShow: () => void;
    loadHide: () => void;

    waitStatus: any;
    waitShow: () => void;
    waitHide: () => void;
}

interface iconLink {
    icon: string;
    title: string;
    link: string;
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

interface CV {
    context: Partial<context>;
    init: <T>(context: T) => void;
}

interface ext {
}

//用户的类型
interface userInfoType {
}

interface resp_type {
    code?: string;
    msg?: string;
    [propName: string]: any;
}

declare namespace bean {
    //基类型
    interface base {
    }
    //查询文案配置
    interface copywriting extends base {
    }
    //刷新登录接口
    // interface rein extends base {
    // }
    // interface login extends base {
    // }
}
