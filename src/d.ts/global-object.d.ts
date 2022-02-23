interface U {
    readonly info: { detail: Partial<userInfoType> };
    init: <T>(info: T) => void;
}

interface Wait {
    status: any;
    show: () => void;
    hide: () => void;
}

interface context {
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
//   p_no: string; //手机号
//   op_n: string; //运营商
//   rg_s: string; //彩铃状态
//   vp_s: string; //个彩状态
//   cg_id: string; //计费ID
}

interface resp_type {
    // code?: string;
    // msg?: string;
    // [propName: string]: any;
}

declare namespace bean {
    //基类型
    interface base {
        // cn: string;
        // pn: string;
    }
    //查询文案配置
    interface qcog extends base {
        pa_t?: string;
        on_s?: string;
    }
    //刷新登录接口
    interface rein extends base {
        b_type: string;
        r_time?: string;
    }
    interface login extends base {
        s_cd: string;
        b_type: string;
        r_time: string;
        p_no: string;
    }
}
