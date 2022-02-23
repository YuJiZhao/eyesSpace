interface U {
    readonly info: { detail: Partial<userInfoType> };
    init: <T>(info: T) => void;
}

interface Wait {
    status: any;
    show: () => void;
    hide: () => void;
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
//   vr_s: string; //视频彩铃状态 vrbtRingStatus
//   vr_vs: string;
}

interface resp_type {
    code?: string;
    msg?: string;
    [propName: string]: any;
}

declare namespace bean {
    //基类型
    interface base {
        // cn: string;
        // pn: string;
    }
    //查询文案配置
    interface copywriting extends base {
        pa_t?: string;
        on_s?: string;
    }
    //刷新登录接口
    // interface rein extends base {
    //     b_type: string;
    //     r_time?: string;
    // }
    // interface login extends base {
    //     s_cd: string;
    //     b_type: string;
    //     r_time: string;
    //     p_no: string;
    // }
}
