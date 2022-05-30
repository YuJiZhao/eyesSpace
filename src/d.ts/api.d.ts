// 请求参数集中类型声明
// declare namespace bean {
    //基类型
    // interface base {
    // }
    //查询文案配置
    // interface copywriting extends base {
    // }
    //刷新登录接口
    // interface rein extends base {
    // }
    // interface login extends base {
    // }
// }

// 接口返回格式
interface RespType {
    code?: number;
    msg?: string;
    [propName: string]: any;
}

// 统一api管理
interface ApiObject {
    safe: () => Promise<void>;
    getContext: () => Promise<RespType>;
    getMusicList: () => Promise<RespType>;
}