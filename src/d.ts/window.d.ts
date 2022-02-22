interface ConfigType {
    apiUrl: string;
    appid: string;
    [propName: string]: any; //支持拓展属性
}

declare const config: ConfigType;